package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HumpheadParrotfishEntity extends Animal {
    private BlockPos target;

    public HumpheadParrotfishEntity(EntityType<? extends Animal> entity, Level world) {
        super(entity, world);
        this.moveControl = new MoveHelperController(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 100));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 2.0F, true));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void travel(Vec3 p_213352_1_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_213352_1_);
        }
    }

    protected PathNavigation createNavigation(Level world) {
        return new WaterBoundPathNavigation(this, world);
    }

    public static boolean checkFishSpawnRules(EntityType<? extends HumpheadParrotfishEntity> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos p_223363_3_, Random randomIn) {
        return worldIn.getBlockState(p_223363_3_).is(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.above()).is(Blocks.WATER) && worldIn.getLevelData().getDayTime() < 12000 && worldIn.getLevelData().getDayTime() > 24000 && randomIn.nextFloat() > 0.7F;
    }

    protected void updateAir(int air) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(air - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    public void baseTick() {
        int lvt_1_1_ = this.getAirSupply();
        super.baseTick();
        this.updateAir(lvt_1_1_);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {
            if (target == null && tickCount % 12000 == 0) {
                selectTarget();
            }

            if (target != null) {
                if (navigation.isDone()) navigation.moveTo(target.getX(), target.getY(), target.getZ(), 0.3);
                else if (distanceToSqr(target.getX(), target.getY(), target.getZ()) <= 4) breakBlock();
            }
        }
    }

    private void breakBlock() {
        if (wasEyeInWater) {
            BlockState state = level.getBlockState(target);
            if (state.canOcclude()) {
                level.levelEvent(2001, target, Block.getId(state));
                for (ItemStack drop : state.getDrops(new LootContext.Builder((ServerLevel) level).withRandom(random).withParameter(LootContextParams.TOOL, ItemStack.EMPTY))) {
                    spawnAtLocation(drop, (float) (target.getY() - getY()));
                }
                level.removeBlock(target, false);
            }
        }
    }

    private void selectTarget() {
        List<BlockPos> possible = new ArrayList<>();
        BlockPos start = blockPosition();
        BlockPos.betweenClosedStream(start.offset(-16, -16, -16), start.offset(16, 16, 16)).forEach(pos -> {
            if (level.getBlockState(pos).is(BlockTags.CORAL_BLOCKS)) {
                possible.add(pos.immutable());
            }
        });
        if (!possible.isEmpty())
            target = possible.get(random.nextInt(possible.size()));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 2.5D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        HumpheadParrotfishEntity entity = new HumpheadParrotfishEntity(UAMEntities.HUMPHEAD_PARROTFISH.get(), this.level);
        entity.finalizeSpawn((ServerLevel) this.level, this.level.getCurrentDifficultyAt(new BlockPos(entity.blockPosition())), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
        return entity;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag != null) {
            setAge(dataTag.getInt("Age"));
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public void aiStep() {
        if (!this.isInWater() && this.onGround && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645D, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.onGround = false;
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        animationSpeed = 2.0f;

        super.aiStep();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (Block.byItem(heldItem.getItem()) instanceof CoralBlock) {
            spawnAtLocation(new ItemStack(Items.SAND, getRandom().nextInt(10)));
            heldItem.shrink(1);
            return InteractionResult.SUCCESS;
        } else if (isBaby() && heldItem.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack bucket = new ItemStack(UAMItems.BABY_HUMPHEAD_PARROTFISH_BUCKET.get());
            if (this.hasCustomName()) {
                bucket.setHoverName(this.getCustomName());
            }
            if (!this.level.isClientSide) {
                bucket.getOrCreateTag().putInt("Age", getAge());
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucket);
            }

            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, bucket);
            } else if (!player.getInventory().add(bucket)) {
                player.drop(bucket, false);
            }

            this.discard();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return Block.byItem(stack.getItem()).defaultBlockState().is(BlockTags.CORAL_PLANTS);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    static class MoveHelperController extends MoveControl {
        private final HumpheadParrotfishEntity fish;

        MoveHelperController(HumpheadParrotfishEntity p_i48857_1_) {
            super(p_i48857_1_);
            this.fish = p_i48857_1_;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }
            if (this.operation == Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
                double lvt_1_1_ = this.wantedX - this.fish.getX();
                double lvt_3_1_ = this.wantedY - this.fish.getY();
                double lvt_5_1_ = this.wantedZ - this.fish.getZ();
                double lvt_7_1_ = Math.sqrt(lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_);
                lvt_3_1_ /= lvt_7_1_;
                float lvt_9_1_ = (float)(Mth.atan2(lvt_5_1_, lvt_1_1_) * 57.2957763671875D) - 90.0F;
                this.fish.setYRot(this.rotlerp(this.fish.getYRot(), lvt_9_1_, 90.0F));
                this.fish.yBodyRot = this.fish.getYRot();
                float lvt_10_1_ = (float)(this.speedModifier * this.fish.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), lvt_10_1_));
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double)this.fish.getSpeed() * lvt_3_1_ * 0.1D, 0.0D));
            } else {
                this.fish.setSpeed(0.0F);
            }
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(UAMItems.HUMPHEAD_PARROTFISH_SPAWN_EGG.get());
    }
}

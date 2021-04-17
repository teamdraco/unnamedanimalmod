package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HumpheadParrotfishEntity extends AnimalEntity {
    private BlockPos target;

    public HumpheadParrotfishEntity(EntityType<? extends AnimalEntity> entity, World world) {
        super(entity, world);
        this.moveController = new MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreatheAirGoal(this));
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 100));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 2.0F, true));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    public void travel(Vector3d p_213352_1_) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_213352_1_);
        }
    }

    protected PathNavigator createNavigator(World p_175447_1_) {
        return new SwimmerPathNavigator(this, p_175447_1_);
    }

    public static boolean func_223363_b(EntityType<? extends HumpheadParrotfishEntity> type, IWorld worldIn, SpawnReason reason, BlockPos p_223363_3_, Random randomIn) {
        return worldIn.getBlockState(p_223363_3_).isIn(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.up()).isIn(Blocks.WATER) && worldIn.getWorldInfo().getDayTime() < 12000 && worldIn.getWorldInfo().getDayTime() > 24000;
    }

    protected void updateAir(int air) {
        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAir(300);
        }
    }

    public void baseTick() {
        int lvt_1_1_ = this.getAir();
        super.baseTick();
        this.updateAir(lvt_1_1_);
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
            if (target == null && ticksExisted % 12000 == 0) {
                selectTarget();
            }

            if (target != null) {
                if (navigator.noPath()) navigator.tryMoveToXYZ(target.getX(), target.getY(), target.getZ(), 0.3);
                else if (getDistanceSq(target.getX(), target.getY(), target.getZ()) <= 4) breakBlock();
            }
        }
    }

    private void breakBlock() {
        if (eyesInWater) {
            BlockState state = world.getBlockState(target);
            if (state.isSolid()) {
                world.playEvent(2001, target, Block.getStateId(state));
                for (ItemStack drop : state.getDrops(new LootContext.Builder((ServerWorld) world).withRandom(rand).withParameter(LootParameters.TOOL, ItemStack.EMPTY))) {
                    entityDropItem(drop, (float) (target.getY() - getPosY()));
                }
                world.removeBlock(target, false);
            }
        }
    }

    private void selectTarget() {
        List<BlockPos> possible = new ArrayList<>();
        BlockPos start = getPosition();
        BlockPos.getAllInBox(start.add(-16, -16, -16), start.add(16, 16, 16)).forEach(pos -> {
            if (world.getBlockState(pos).isIn(BlockTags.CORAL_BLOCKS)) {
                possible.add(pos.toImmutable());
            }
        });
        if (!possible.isEmpty())
            target = possible.get(rand.nextInt(possible.size()));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 14.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 2.5D);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        HumpheadParrotfishEntity entity = new HumpheadParrotfishEntity(UAMEntities.HUMPHEAD_PARROTFISH.get(), this.world);
        entity.onInitialSpawn((ServerWorld) this.world, this.world.getDifficultyForLocation(new BlockPos(entity.getPosition())), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
        return entity;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (dataTag != null) {
            setGrowingAge(dataTag.getInt("Age"));
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public void livingTick() {
        if (!this.isInWater() && this.onGround && this.collidedVertically) {
            this.setMotion(this.getMotion().add((double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645D, (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.onGround = false;
            this.isAirBorne = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }

        limbSwingAmount = 2.0f;

        super.livingTick();
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (Block.getBlockFromItem(heldItem.getItem()) instanceof CoralBlock) {
            entityDropItem(new ItemStack(Items.SAND, getRNG().nextInt(10)));
            heldItem.shrink(1);
            return ActionResultType.SUCCESS;
        } else if (isChild() && heldItem.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack bucket = new ItemStack(UAMItems.BABY_HUMPHEAD_PARROTFISH_BUCKET.get());
            if (this.hasCustomName()) {
                bucket.setDisplayName(this.getCustomName());
            }
            if (!this.world.isRemote) {
                bucket.getOrCreateTag().putInt("Age", getGrowingAge());
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, bucket);
            }

            if (heldItem.isEmpty()) {
                player.setHeldItem(hand, bucket);
            } else if (!player.inventory.addItemStackToInventory(bucket)) {
                player.dropItem(bucket, false);
            }

            this.remove();
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(player, hand);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return Block.getBlockFromItem(stack.getItem()).isIn(BlockTags.CORAL_PLANTS);
    }

    public boolean isPushedByWater() {
        return false;
    }

    static class MoveHelperController extends MovementController {
        private final HumpheadParrotfishEntity fish;

        MoveHelperController(HumpheadParrotfishEntity p_i48857_1_) {
            super(p_i48857_1_);
            this.fish = p_i48857_1_;
        }

        public void tick() {
            if (this.fish.areEyesInFluid(FluidTags.WATER)) {
                this.fish.setMotion(this.fish.getMotion().add(0.0D, 0.005D, 0.0D));
            }
            if (this.action == Action.MOVE_TO && !this.fish.getNavigator().noPath()) {
                double lvt_1_1_ = this.posX - this.fish.getPosX();
                double lvt_3_1_ = this.posY - this.fish.getPosY();
                double lvt_5_1_ = this.posZ - this.fish.getPosZ();
                double lvt_7_1_ = (double) MathHelper.sqrt(lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_);
                lvt_3_1_ /= lvt_7_1_;
                float lvt_9_1_ = (float)(MathHelper.atan2(lvt_5_1_, lvt_1_1_) * 57.2957763671875D) - 90.0F;
                this.fish.rotationYaw = this.limitAngle(this.fish.rotationYaw, lvt_9_1_, 90.0F);
                this.fish.renderYawOffset = this.fish.rotationYaw;
                float lvt_10_1_ = (float)(this.speed * this.fish.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                this.fish.setAIMoveSpeed(MathHelper.lerp(0.125F, this.fish.getAIMoveSpeed(), lvt_10_1_));
                this.fish.setMotion(this.fish.getMotion().add(0.0D, (double)this.fish.getAIMoveSpeed() * lvt_3_1_ * 0.1D, 0.0D));
            } else {
                this.fish.setAIMoveSpeed(0.0F);
            }
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.HUMPHEAD_PARROTFISH_SPAWN_EGG.get());
    }
}
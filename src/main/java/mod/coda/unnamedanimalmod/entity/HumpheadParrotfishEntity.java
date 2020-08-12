package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;

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

    @Override
    public void travel(Vec3d p_213352_1_) {
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

    public static boolean canFishSpawn(EntityType<? extends HumpheadParrotfishEntity> entity, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() < world.getSeaLevel() && world.getFluidState(pos) == Fluids.WATER;
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
            if (target == null && ticksExisted % 500 == 0) {
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
                for (ItemStack drop : state.getDrops(new LootContext.Builder((ServerWorld) world).withRandom(rand).withParameter(LootParameters.POSITION, target).withParameter(LootParameters.TOOL, ItemStack.EMPTY))) {
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

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.5);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        HumpheadParrotfishEntity entity = new HumpheadParrotfishEntity(ModEntityTypes.HUMPHEAD_PARROTFISH.get(), this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
        return entity;
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
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            HumpheadParrotfishEntity child = ModEntityTypes.HUMPHEAD_PARROTFISH.get().create(world);
            if (child != null) {
                child.setGrowingAge(-24000);
                child.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
                this.world.addEntity(child);
                if (heldItem.hasDisplayName()) {
                    child.setCustomName(heldItem.getDisplayName());
                }
                this.onChildSpawnFromEgg(player, child);
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
            }
            return true;
        }
        if (this.isBreedingItem(heldItem)) {
            if (this.getGrowingAge() == 0 && this.canBreed()) {
                this.consumeItemFromStack(player, heldItem);
                this.setInLove(player);
                player.swing(hand, true);
            }
            return true;
        }
        if (this.isChild()) {
            this.consumeItemFromStack(player, heldItem);
            this.ageUp((int) (this.getGrowingAge() / -20.0 * 0.1), true);
            return true;
        }
        return false;
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return Block.getBlockFromItem(stack.getItem()).isIn(BlockTags.CORAL_BLOCKS);
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
                float lvt_10_1_ = (float)(this.speed * this.fish.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                this.fish.setAIMoveSpeed(MathHelper.lerp(0.125F, this.fish.getAIMoveSpeed(), lvt_10_1_));
                this.fish.setMotion(this.fish.getMotion().add(0.0D, (double)this.fish.getAIMoveSpeed() * lvt_3_1_ * 0.1D, 0.0D));
            } else {
                this.fish.setAIMoveSpeed(0.0F);
            }
        }
    }

}

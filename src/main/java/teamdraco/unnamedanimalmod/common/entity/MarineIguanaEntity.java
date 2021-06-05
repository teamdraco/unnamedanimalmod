package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.init.UAMItems;
import teamdraco.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class MarineIguanaEntity extends AnimalEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(MarineIguanaEntity.class, DataSerializers.INT);
    public int timeUntilNextSneeze = this.random.nextInt(8000) + 8000;
    private UUID lightningUUID;
    private boolean didSneeze;

    public MarineIguanaEntity(EntityType<? extends MarineIguanaEntity> type, World world) {
        super(type, world);
        this.moveControl = new MarineIguanaEntity.MoveHelperController(this);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.maxUpStep = 1;
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new MarineIguanaEntity.Navigator(this, level);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 360;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 2.0D, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new MarineIguanaEntity.WanderGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new MarineIguanaEntity.GoToWaterGoal(this, 1.0D));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.SEAGRASS;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 10).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15);
    }

    public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).is(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return 1.5F;
        }
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.timeUntilNextSneeze <= 0) {
            if (!this.isSilent()) {
                this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.LLAMA_SPIT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }
            this.spawnAtLocation(new ItemStack(UAMItems.SALT.get(), random.nextInt(4)));
            MarineIguanaEntity iguanaEntity = (MarineIguanaEntity)this;
            this.timeUntilNextSneeze = this.random.nextInt(8000) + 6000;
            this.didSneeze = true;

            iguanaEntity.setDidSneeze(false);
        }
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(Blocks.STONE) && worldIn.getRawBrightness(pos, 0) > 8;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (dataTag == null) {
            setVariant(random.nextInt(4));
        } else {
            if (dataTag.contains("BucketVariantTag", 3)){
                this.setVariant(dataTag.getInt("BucketVariantTag"));
            }
        }
        return spawnDataIn;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("BucketVariantTag", getVariant());
        compound.putInt("SneezeTime", this.timeUntilNextSneeze);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("BucketVariantTag"));
        if (compound.contains("SneezeTime")) {
            this.timeUntilNextSneeze = compound.getInt("SneezeTime");
        }
   }

    public void thunderHit(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
        UUID uuid = p_241841_2_.getUUID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setVariant(4);
            this.lightningUUID = uuid;
            this.playSound(UAMSounds.MARINE_IGUANA_TRANSFORMS.get(), 2.0F, 1.0F);
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        this.spawnAtLocation(new ItemStack(UAMItems.MARINE_IGUANA_EGG.get(), getRandom().nextInt(1) + 1));
        this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        ((AnimalEntity) p_241840_2_).resetLove();
        return null;
    }

    private void setDidSneeze(boolean didSneezeIn) {
        this.didSneeze = didSneezeIn;
    }

    protected SoundEvent getAmbientSound() {
        return UAMSounds.MARINE_IGUANA_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return UAMSounds.MARINE_IGUANA_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UAMSounds.MARINE_IGUANA_HURT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.MARINE_IGUANA_SPAWN_EGG.get());
    }

    static class MoveHelperController extends MovementController {
        private final MarineIguanaEntity iguana;

        MoveHelperController(MarineIguanaEntity iguana) {
            super(iguana);
            this.iguana = iguana;
        }

        private void updateSpeed() {
            if (this.iguana.isInWater()) {
                this.iguana.setDeltaMovement(this.iguana.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

                if (this.iguana.isBaby()) {
                    this.iguana.setSpeed(Math.max(this.iguana.getSpeed() / 3.0F, 0.06F));
                }
            } else if (this.iguana.onGround) {
                this.iguana.setSpeed(Math.max(this.iguana.getSpeed() / 1.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.operation == MovementController.Action.MOVE_TO && !this.iguana.getNavigation().isDone()) {
                double d0 = this.wantedX - this.iguana.getX();
                double d1 = this.wantedY - this.iguana.getY();
                double d2 = this.wantedZ - this.iguana.getZ();
                double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.iguana.yRot = this.rotlerp(this.iguana.yRot, f, 90.0F);
                this.iguana.yBodyRot = this.iguana.yRot;
                float f1 = (float)(this.speedModifier * this.iguana.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.iguana.setSpeed(MathHelper.lerp(0.125F, this.iguana.getSpeed(), f1));
                this.iguana.setDeltaMovement(this.iguana.getDeltaMovement().add(0.0D, (double)this.iguana.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.iguana.setSpeed(0.0F);
            }
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(MarineIguanaEntity iguana, World worldIn) {
            super(iguana, worldIn);
        }

        protected boolean canUpdatePath() {
            return true;
        }

        protected PathFinder createPathFinder(int p_179679_1_) {
            this.nodeEvaluator = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeEvaluator, p_179679_1_);
        }

        public boolean isStableDestination(BlockPos pos) {
            if (this.mob instanceof MarineIguanaEntity) {
                return !this.level.getBlockState(pos.below()).isAir();
                }
            else return !this.level.getBlockState(pos.below()).isAir();
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final MarineIguanaEntity iguana;

        private WanderGoal(MarineIguanaEntity iguana, double speedIn, int chance) {
            super(iguana, speedIn, chance);
            this.iguana = iguana;
        }

        public boolean canUse() {
            return !this.mob.isInWater() && super.canUse();
        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final MarineIguanaEntity iguana;

        private GoToWaterGoal(MarineIguanaEntity iguana, double speedIn) {
            super(iguana, iguana.isBaby() ? 2.0D : speedIn, 24);
            this.iguana = iguana;
            this.verticalSearchStart = -1;
        }

        public boolean canContinueToUse() {
            return !this.iguana.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.iguana.level, this.blockPos);
        }

        public boolean canUse() {
            if (this.iguana.isBaby() && !this.iguana.isInWater()) {
                return super.canUse();
            } else {
                return !this.iguana.isInWater() && super.canUse();
            }
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 160 == 0;
        }

        protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos).is(Blocks.WATER);
        }
    }
}
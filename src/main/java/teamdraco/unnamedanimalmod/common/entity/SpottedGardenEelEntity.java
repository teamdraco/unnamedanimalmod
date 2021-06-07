package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import teamdraco.unnamedanimalmod.init.UAMItems;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SpottedGardenEelEntity extends AbstractFishEntity {
    private static final DataParameter<Boolean> HIDDEN = EntityDataManager.defineId(SpottedGardenEelEntity.class, DataSerializers.BOOLEAN);
    private static final String HIDDEN_DATA = "Hidden";

    public SpottedGardenEelEntity(EntityType<? extends SpottedGardenEelEntity> type, World world) {
        super(type, world);
        this.moveControl = new SpottedGardenEelEntity.MoveHelperController(this);
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return  new SwimmerPathNavigator(this, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new HideGoal());
        if (!this.isHidden()) {
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 10, 2.0D, 2.5D));
            this.goalSelector.addGoal(6, new SpottedGardenEelEntity.SwimGoal(this));
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HIDDEN, false);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult result) {
        return new ItemStack(UAMItems.SPOTTED_GARDEN_EEL_SPAWN_EGG.get());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setBurrowed(compound.getBoolean(HIDDEN_DATA));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean(HIDDEN_DATA, false);
    }

    public boolean isHidden() {
        return entityData.get(HIDDEN);
    }

    public void setBurrowed(boolean hide) {
        entityData.set(HIDDEN, hide);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isHidden()) {
            if (level.getBlockState(blockPosition().below()).getMaterial() != Material.SAND) setBurrowed(false);
        }
    }

    @Override
    protected ItemStack getBucketItemStack() {
        return new ItemStack(UAMItems.SPOTTED_GARDEN_EEL_BUCKET.get());
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || isHidden();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Override
    public boolean isPushable() {
        return !isHidden();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.KNOCKBACK_RESISTANCE, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.7D);
    }

    @Override
    public float getEyeHeight(Pose p_213307_1_) {
        return isHidden() ? 0.65F : 0.1F;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        setPos(getRandomX(1.5), getY(), getRandomZ(1.5));
        return spawnDataIn;
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        }
        else {
            super.travel(travelVector);
        }
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final SpottedGardenEelEntity eel;

        public SwimGoal(SpottedGardenEelEntity eel) {
            super(eel, 1.0D, 40);
            this.eel = eel;
        }

        public boolean canUse() {
            return super.canUse();
        }
    }

    static class MoveHelperController extends MovementController {
        private final SpottedGardenEelEntity eel;
        MoveHelperController(SpottedGardenEelEntity eel) {
            super(eel);
            this.eel = eel;
        }

        public void tick() {
            if (this.eel.isEyeInFluid(FluidTags.WATER)) {
                this.eel.setDeltaMovement(this.eel.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.eel.getNavigation().isDone()) {
                float f = (float)(this.speedModifier * this.eel.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.eel.setSpeed(MathHelper.lerp(0.125F, this.eel.getSpeed(), f));
                double d0 = this.wantedY - this.eel.getY();
                double d1 = this.wantedX - this.eel.getY();
                double d2 = this.wantedZ - this.eel.getZ();
                if (d1 != 0.0D) {
                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.eel.setDeltaMovement(this.eel.getDeltaMovement().add(0.0D, (double)this.eel.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if (d0 != 0.0D || d2 != 0.0D) {
                    float f1 = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.eel.yRot = this.rotlerp(this.eel.yRot, f1, 90.0F);
                    this.eel.yRotO = this.eel.yRot;
                }
            } else {
                this.eel.setSpeed(0.0F);
            }
        }
    }

    class HideGoal extends Goal {
        private int hideTicks = 30;

        public HideGoal() {
            setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return !isHidden() && belowIsSand();
        }

        @Override
        public void stop() {
            hideTicks = 30;
        }

        @Override
        public void tick() {
            if (isInWater())
                if (--hideTicks <= 0)
                {
                    setBurrowed(true);
                    hideTicks = 30;
                }
        }

        private boolean belowIsSand() {
            return level.getBlockState(blockPosition().below(1)).getMaterial() == Material.SAND;
        }
    }
}
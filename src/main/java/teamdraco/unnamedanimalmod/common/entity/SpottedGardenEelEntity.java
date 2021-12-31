package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import teamdraco.unnamedanimalmod.init.UAMItems;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SpottedGardenEelEntity extends AbstractFish {
    private static final EntityDataAccessor<Boolean> HIDDEN = SynchedEntityData.defineId(SpottedGardenEelEntity.class, EntityDataSerializers.BOOLEAN);
    private static final String HIDDEN_DATA = "Hidden";

    public SpottedGardenEelEntity(EntityType<? extends SpottedGardenEelEntity> type, Level world) {
        super(type, world);
        this.moveControl = new SpottedGardenEelEntity.MoveHelperController(this);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        return  new WaterBoundPathNavigation(this, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new HideGoal());
        if (!this.isHidden()) {
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 10, 2.0D, 2.5D));
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
    public ItemStack getPickedResult(HitResult result) {
        return new ItemStack(UAMItems.SPOTTED_GARDEN_EEL_SPAWN_EGG.get());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBurrowed(compound.getBoolean(HIDDEN_DATA));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
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
    public ItemStack getBucketItemStack() {
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

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.7D);
    }

    @Override
    public float getEyeHeight(Pose p_213307_1_) {
        return isHidden() ? 0.65F : 0.1F;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        setPos(getRandomX(1.5), getY(), getRandomZ(1.5));
        return spawnDataIn;
    }

    @Override
    public void travel(Vec3 travelVector) {
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

    static class MoveHelperController extends MoveControl {
        private final AbstractFish fish;

        MoveHelperController(AbstractFish p_i48857_1_) {
            super(p_i48857_1_);
            this.fish = p_i48857_1_;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
                float f = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), f));
                double d0 = this.wantedX - this.fish.getX();
                double d1 = this.wantedY - this.fish.getY();
                double d2 = this.wantedZ - this.fish.getZ();
                if (d1 != 0.0D) {
                    double d3 = (double)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double)this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if (d0 != 0.0D || d2 != 0.0D) {
                    float f1 = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.fish.setYRot(this.rotlerp(this.fish.getYRot(), f1, 90.0F));
                    this.fish.yBodyRot = this.fish.getYRot();
                }

            } else {
                this.fish.setSpeed(0.0F);
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

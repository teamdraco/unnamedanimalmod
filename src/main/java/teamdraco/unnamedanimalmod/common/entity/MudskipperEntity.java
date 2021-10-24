package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import teamdraco.unnamedanimalmod.common.entity.util.GroundAndSwimmerNavigator;
import teamdraco.unnamedanimalmod.init.UAMItems;

import javax.annotation.Nullable;
import java.util.Random;

public class MudskipperEntity extends WaterMobEntity {
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.defineId(MudskipperEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(MudskipperEntity.class, DataSerializers.INT);

    public MudskipperEntity(EntityType<? extends MudskipperEntity> type, World world) {
        super(type, world);
        this.moveControl = new MudskipperEntity.MoveHelperController(this);
        this.maxUpStep = 1.0f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(1, new PanicGoal(this,1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 4) {
            @Override
            public boolean canUse() {
                return super.canUse() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new MudskipperEntity.WanderGoal(this, 1.0D, 15));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new FindWaterGoal(this));
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        return new GroundAndSwimmerNavigator(this, world);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9f;
    }

    protected ItemStack getFishBucket() {
        return new ItemStack(UAMItems.MUDSKIPPER_BUCKET.get());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 6).add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (dataTag == null) {
            setVariant(random.nextInt(5));
        } else {
            if (dataTag.contains("Variant", 3)){
                this.setVariant(dataTag.getInt("Variant"));
            }
        }
        return spawnDataIn;
    }

    public static boolean checkMudskipperSpawnRules(EntityType<? extends MudskipperEntity> p_223363_0_, IWorld p_223363_1_, SpawnReason p_223363_2_, BlockPos p_223363_3_, Random p_223363_4_) {
        return p_223363_1_.getBlockState(p_223363_3_).is(Blocks.WATER) && p_223363_1_.getBlockState(p_223363_3_.above()).is(Blocks.WATER) && p_223363_4_.nextFloat() > 0.6F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.MUDSKIPPER_SPAWN_EGG.get());
    }

    @Override
    protected void handleAirSupply(int p_209207_1_) {
    }

    @Override
    public boolean requiresCustomPersistence() {
        if (this.isFromBucket()) {
            return false;
        }
        else {
            return true;
        }
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(VARIANT, 0);
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
        compound.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
        setVariant(compound.getInt("Variant"));
    }

    @Override
    public void travel(Vector3d p_213352_1_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.01D, 0.0D));
            }
        } else {
            super.travel(p_213352_1_);
        }

    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            this.setBucketData(itemstack1);
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)p_230254_1_, itemstack1);
            }

            if (itemstack.isEmpty()) {
                p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
            } else if (!p_230254_1_.inventory.add(itemstack1)) {
                p_230254_1_.drop(itemstack1, false);
            }

            this.remove();
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    protected void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
        CompoundNBT compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
    }

    static class MoveHelperController extends MovementController {
        private final MudskipperEntity fish;

        MoveHelperController(MudskipperEntity fish) {
            super(fish);
            this.fish = fish;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.0D, 0.0D));
            }

/*
            BlockPos pos = this.fish.blockPosition();
            BlockPos posBelow = pos.below();
            BlockState state = this.fish.level.getBlockState(pos);
            BlockState stateBelow = this.fish.level.getBlockState(posBelow);
            if (stateBelow.isRedstoneConductor(this.fish.level, pos) && state.is(Blocks.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().multiply(2.0D, 1.0D, 2.0D));
            }
*/

            if (this.operation == Action.MOVE_TO && !this.fish.getNavigation().isDone()) {
                double d0 = this.wantedX - this.fish.getX();
                double d1 = this.wantedY - this.fish.getY();
                double d2 = this.wantedZ - this.fish.getZ();
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.fish.yRot = this.rotlerp(this.fish.yRot, f, 90.0F);
                this.fish.yBodyRot = this.fish.yRot;
                float f1 = (float) (this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(MathHelper.lerp(0.125F, this.fish.getSpeed(), f1));
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double) this.fish.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.fish.setSpeed(0.0F);
            }
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final MudskipperEntity mudskipper;

        private WanderGoal(MudskipperEntity mudskipper, double speedIn, int chance) {
            super(mudskipper, speedIn, chance);
            this.mudskipper = mudskipper;
        }

        public boolean canUse() {
            return !this.mob.isInWater() && super.canUse();
        }
    }
}
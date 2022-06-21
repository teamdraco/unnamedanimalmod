package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlubberJellyEntity extends AbstractFish {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(MangroveSnakeEntity.class, EntityDataSerializers.INT);
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    public float squidRotation;
    public float prevSquidRotation;
    public float tentacleAngle;
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    private float rotationVelocity;
    private float rotateSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public BlubberJellyEntity(EntityType<? extends AbstractFish> type, Level p_i48565_2_) {
        super(type, p_i48565_2_);
        this.random.setSeed((long)this.getId());
        this.rotationVelocity = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag == null) {
            setVariant(random.nextInt(12));
        } else {
            if (dataTag.contains("Variant", 3)){
                this.setVariant(dataTag.getInt("Variant"));
            }
        }
        return spawnDataIn;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BlubberJellyEntity.MoveRandomGoal(this));
        this.goalSelector.addGoal(1, new BlubberJellyEntity.FleeGoal());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    /*    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(UAMItems.BLUBBER_JELLY_SPAWN_EGG.get());
    }*/

    public void saveToBucketTag(ItemStack bucket) {
        super.saveToBucketTag(bucket);
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
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

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.5F;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    public void aiStep() {
        super.aiStep();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;
        if ((double)this.squidRotation > (Math.PI * 2D)) {
            if (this.level.isClientSide) {
                this.squidRotation = ((float)Math.PI * 2F);
            } else {
                this.squidRotation = (float)((double)this.squidRotation - (Math.PI * 2D));
                if (this.random.nextInt(10) == 0) {
                    this.rotationVelocity = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.level.broadcastEntityEvent(this, (byte)30);
            }
        }

        if (this.isInWaterOrBubble()) {
            if (this.squidRotation < (float)Math.PI) {
                float f = this.squidRotation / (float)Math.PI;
                this.tentacleAngle = Mth.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
                if ((double)f > 0.75D) {
                    this.randomMotionSpeed = 1.0F;
                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.level.isClientSide) {
                this.setDeltaMovement((double)(this.randomMotionVecX * this.randomMotionSpeed), (double)(this.randomMotionVecY * this.randomMotionSpeed), (double)(this.randomMotionVecZ * this.randomMotionSpeed));
            }

            Vec3 vector3d = this.getDeltaMovement();
            float f1 = (float) Math.sqrt(distanceToSqr(vector3d));
            this.yBodyRot += (-((float)Mth.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI) - this.yBodyRot) * 0.1F;
            this.setYRot(this.yBodyRot);
            this.squidYaw = (float)((double)this.squidYaw + Math.PI * (double)this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float)Mth.atan2((double)f1, vector3d.y)) * (180F / (float)Math.PI) - this.squidPitch) * 0.1F;
        } else {
            this.tentacleAngle = Mth.abs(Mth.sin(this.squidRotation)) * (float)Math.PI * 0.25F;
            if (!this.level.isClientSide) {
                double d0 = this.getDeltaMovement().y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    d0 = 0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.isNoGravity()) {
                    d0 -= 0.08D;
                }

                this.setDeltaMovement(0.0D, d0 * (double)0.98F, 0.0D);
            }

            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
        }

    }

    public void travel(Vec3 travelVector) {
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 30) {
            this.squidRotation = 0.0F;
        } else {
            super.handleEntityEvent(id);
        }

    }

    @Override
    public ItemStack getBucketItemStack() {
        return /*new ItemStack(UAMItems.BLUBBER_JELLY_BUCKET.get())*/ null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector() {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }

    class FleeGoal extends Goal {
        private int tickCounter;

        private FleeGoal() {
        }

        public boolean canUse() {
            LivingEntity livingentity = BlubberJellyEntity.this.getLastHurtByMob();
            if (BlubberJellyEntity.this.isInWater() && livingentity != null) {
                return BlubberJellyEntity.this.distanceToSqr(livingentity) < 100.0D;
            } else {
                return false;
            }
        }
        
        public void start() {
            this.tickCounter = 0;
        }
        
        public void tick() {
            ++this.tickCounter;
            LivingEntity livingentity = BlubberJellyEntity.this.getLastHurtByMob();
            if (livingentity != null) {
                Vec3 vector3d = new Vec3(BlubberJellyEntity.this.getX() - livingentity.getX(), BlubberJellyEntity.this.getY() - livingentity.getY(), BlubberJellyEntity.this.getZ() - livingentity.getZ());
                BlockState blockstate = BlubberJellyEntity.this.level.getBlockState(new BlockPos(BlubberJellyEntity.this.getX() + vector3d.x, BlubberJellyEntity.this.getY() + vector3d.y, BlubberJellyEntity.this.getZ() + vector3d.z));
                FluidState fluidstate = BlubberJellyEntity.this.level.getFluidState(new BlockPos(BlubberJellyEntity.this.getX() + vector3d.x, BlubberJellyEntity.this.getY() + vector3d.y, BlubberJellyEntity.this.getZ() + vector3d.z));
                if (fluidstate.is(FluidTags.WATER) || blockstate.isAir()) {
                    double d0 = vector3d.length();
                    if (d0 > 0.0D) {
                        vector3d.normalize();
                        float f = 3.0F;
                        if (d0 > 5.0D) {
                            f = (float)((double)f - (d0 - 5.0D) / 5.0D);
                        }

                        if (f > 0.0F) {
                            vector3d = vector3d.scale((double)f);
                        }
                    }

                    if (blockstate.isAir()) {
                        vector3d = vector3d.subtract(0.0D, vector3d.y, 0.0D);
                    }

                    BlubberJellyEntity.this.setMovementVector((float)vector3d.x / 20.0F, (float)vector3d.y / 20.0F, (float)vector3d.z / 20.0F);
                }

                if (this.tickCounter % 10 == 5) {
                    BlubberJellyEntity.this.level.addParticle(ParticleTypes.BUBBLE, BlubberJellyEntity.this.getX(), BlubberJellyEntity.this.getY(), BlubberJellyEntity.this.getZ(), 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        private final BlubberJellyEntity jelly;

        public MoveRandomGoal(BlubberJellyEntity p_i48823_2_) {
            this.jelly = p_i48823_2_;
        }
        
        public boolean canUse() {
            return true;
        }

        public void tick() {
            int i = this.jelly.getNoActionTime();
            if (i > 100) {
                this.jelly.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.jelly.getRandom().nextInt(50) == 0 || !this.jelly.wasTouchingWater || !this.jelly.hasMovementVector()) {
                float f = this.jelly.getRandom().nextFloat() * ((float)Math.PI * 2F);
                float f1 = Mth.cos(f) * 0.2F;
                float f2 = -0.1F + this.jelly.getRandom().nextFloat() * 0.2F;
                float f3 = Mth.sin(f) * 0.2F;
                this.jelly.setMovementVector(f1, f2, f3);
            }

        }
    }
}

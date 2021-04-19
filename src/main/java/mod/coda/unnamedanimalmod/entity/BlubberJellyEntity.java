package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlubberJellyEntity extends AbstractFishEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(MangroveSnakeEntity.class, DataSerializers.VARINT);
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

    public BlubberJellyEntity(EntityType<? extends AbstractFishEntity> type, World p_i48565_2_) {
        super(type, p_i48565_2_);
        this.rand.setSeed((long)this.getEntityId());
        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (dataTag == null) {
            setVariant(rand.nextInt(12));
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

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D);
    }
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.BLUBBER_JELLY_SPAWN_EGG.get());
    }

    protected void setBucketData(ItemStack bucket) {
        super.setBucketData(bucket);
        CompoundNBT compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        if (this.hasCustomName()) {
            bucket.setDisplayName(this.getCustomName());
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setVariant(compound.getInt("Variant"));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.5F;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void livingTick() {
        super.livingTick();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;
        if ((double)this.squidRotation > (Math.PI * 2D)) {
            if (this.world.isRemote) {
                this.squidRotation = ((float)Math.PI * 2F);
            } else {
                this.squidRotation = (float)((double)this.squidRotation - (Math.PI * 2D));
                if (this.rand.nextInt(10) == 0) {
                    this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.setEntityState(this, (byte)30);
            }
        }

        if (this.isInWaterOrBubbleColumn()) {
            if (this.squidRotation < (float)Math.PI) {
                float f = this.squidRotation / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
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

            if (!this.world.isRemote) {
                this.setMotion((double)(this.randomMotionVecX * this.randomMotionSpeed), (double)(this.randomMotionVecY * this.randomMotionSpeed), (double)(this.randomMotionVecZ * this.randomMotionSpeed));
            }

            Vector3d vector3d = this.getMotion();
            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
            this.renderYawOffset += (-((float)MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI) - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw = (float)((double)this.squidYaw + Math.PI * (double)this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float)MathHelper.atan2((double)f1, vector3d.y)) * (180F / (float)Math.PI) - this.squidPitch) * 0.1F;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;
            if (!this.world.isRemote) {
                double d0 = this.getMotion().y;
                if (this.isPotionActive(Effects.LEVITATION)) {
                    d0 = 0.05D * (double)(this.getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1);
                } else if (!this.hasNoGravity()) {
                    d0 -= 0.08D;
                }

                this.setMotion(0.0D, d0 * (double)0.98F, 0.0D);
            }

            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
        }

    }

    public void travel(Vector3d travelVector) {
        this.move(MoverType.SELF, this.getMotion());
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 30) {
            this.squidRotation = 0.0F;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(UAMItems.BLUBBER_JELLY_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
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

        public boolean shouldExecute() {
            LivingEntity livingentity = BlubberJellyEntity.this.getRevengeTarget();
            if (BlubberJellyEntity.this.isInWater() && livingentity != null) {
                return BlubberJellyEntity.this.getDistanceSq(livingentity) < 100.0D;
            } else {
                return false;
            }
        }
        
        public void startExecuting() {
            this.tickCounter = 0;
        }
        
        public void tick() {
            ++this.tickCounter;
            LivingEntity livingentity = BlubberJellyEntity.this.getRevengeTarget();
            if (livingentity != null) {
                Vector3d vector3d = new Vector3d(BlubberJellyEntity.this.getPosX() - livingentity.getPosX(), BlubberJellyEntity.this.getPosY() - livingentity.getPosY(), BlubberJellyEntity.this.getPosZ() - livingentity.getPosZ());
                BlockState blockstate = BlubberJellyEntity.this.world.getBlockState(new BlockPos(BlubberJellyEntity.this.getPosX() + vector3d.x, BlubberJellyEntity.this.getPosY() + vector3d.y, BlubberJellyEntity.this.getPosZ() + vector3d.z));
                FluidState fluidstate = BlubberJellyEntity.this.world.getFluidState(new BlockPos(BlubberJellyEntity.this.getPosX() + vector3d.x, BlubberJellyEntity.this.getPosY() + vector3d.y, BlubberJellyEntity.this.getPosZ() + vector3d.z));
                if (fluidstate.isTagged(FluidTags.WATER) || blockstate.isAir()) {
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
                    BlubberJellyEntity.this.world.addParticle(ParticleTypes.BUBBLE, BlubberJellyEntity.this.getPosX(), BlubberJellyEntity.this.getPosY(), BlubberJellyEntity.this.getPosZ(), 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        private final BlubberJellyEntity jelly;

        public MoveRandomGoal(BlubberJellyEntity p_i48823_2_) {
            this.jelly = p_i48823_2_;
        }
        
        public boolean shouldExecute() {
            return true;
        }

        public void tick() {
            int i = this.jelly.getIdleTime();
            if (i > 100) {
                this.jelly.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.jelly.getRNG().nextInt(50) == 0 || !this.jelly.inWater || !this.jelly.hasMovementVector()) {
                float f = this.jelly.getRNG().nextFloat() * ((float)Math.PI * 2F);
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.jelly.getRNG().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;
                this.jelly.setMovementVector(f1, f2, f3);
            }

        }
    }
}

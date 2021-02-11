package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class HonduranWhiteBatEntity extends AnimalEntity {
    private static final DataParameter<Byte> HANGING = EntityDataManager.createKey(HonduranWhiteBatEntity.class, DataSerializers.BYTE);
    private static final EntityPredicate field_213813_c = (new EntityPredicate()).setDistance(4.0D).allowFriendlyFire();
    private BlockPos spawnPosition;

    public HonduranWhiteBatEntity(EntityType<? extends HonduranWhiteBatEntity> type, World worldIn) {
        super(type, worldIn);
        this.setIsBatHanging(true);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.HONDURAN_WHITE_BAT.get().create(this.world);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(HANGING, (byte)0);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D);
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    protected float getSoundPitch() {
        return super.getSoundPitch() * 1.3F;
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entityIn) {
    }

    protected void collideWithNearbyEntities() {
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.MELON_SLICE;
    }

    public boolean getIsBatHanging() {
        return (this.dataManager.get(HANGING) & 1) != 0;
    }

    public void setIsBatHanging(boolean isHanging) {
        byte b0 = this.dataManager.get(HANGING);
        if (isHanging) {
            this.dataManager.set(HANGING, (byte)(b0 | 1));
        } else {
            this.dataManager.set(HANGING, (byte)(b0 & -2));
        }

    }

    public void tick() {
        super.tick();
        if (this.getIsBatHanging()) {
            this.setMotion(Vector3d.ZERO);
            this.setRawPosition(this.getPosX(), (double) MathHelper.floor(this.getPosY()) + 1.0D - (double)this.getHeight(), this.getPosZ());
        } else {
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        }

    }

    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = this.getPosition();
        BlockPos blockpos1 = blockpos.up();
        if (this.getIsBatHanging()) {
            boolean flag = this.isSilent();
            if (this.world.getBlockState(blockpos1).isNormalCube(this.world, blockpos)) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }

                if (this.world.getClosestPlayer(field_213813_c, this) != null) {
                    this.setIsBatHanging(false);
                    if (!flag) {
                        this.world.playEvent((PlayerEntity)null, 1025, blockpos, 0);
                    }
                }
            } else {
                this.setIsBatHanging(false);
                if (!flag) {
                    this.world.playEvent((PlayerEntity)null, 1025, blockpos, 0);
                }
            }
        } else {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.withinDistance(this.getPositionVec(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.getPosX() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.getPosY() + (double)this.rand.nextInt(6) - 2.0D, this.getPosZ() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
            }

            double d2 = (double)this.spawnPosition.getX() + 0.5D - this.getPosX();
            double d0 = (double)this.spawnPosition.getY() + 0.1D - this.getPosY();
            double d1 = (double)this.spawnPosition.getZ() + 0.5D - this.getPosZ();
            Vector3d vector3d = this.getMotion();
            Vector3d vector3d1 = vector3d.add((Math.signum(d2) * 0.5D - vector3d.x) * (double)0.1F, (Math.signum(d0) * (double)0.7F - vector3d.y) * (double)0.1F, (Math.signum(d1) * 0.5D - vector3d.z) * (double)0.1F);
            this.setMotion(vector3d1);
            float f = (float)(MathHelper.atan2(vector3d1.z, vector3d1.x) * (double)(180F / (float)Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;
            if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos1).isNormalCube(this.world, blockpos1)) {
                this.setIsBatHanging(true);
            }
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.world.isRemote && this.getIsBatHanging()) {
                this.setIsBatHanging(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(HANGING, compound.getByte("BatFlags"));
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putByte("BatFlags", this.dataManager.get(HANGING));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height / 2.0F;
    }

/*    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.HONDURAN_WHITE_BAT_SPAWN_EGG.get());
    }*/
}

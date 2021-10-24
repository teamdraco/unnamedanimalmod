package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import teamdraco.unnamedanimalmod.init.UAMItems;

import javax.annotation.Nullable;

public class LeafySeadragonEntity extends AbstractFishEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(LeafySeadragonEntity.class, DataSerializers.INT);

    public LeafySeadragonEntity(EntityType<? extends LeafySeadragonEntity> type, World world) {
        super(type, world);
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
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
    }

    @Override
    protected void saveToBucketTag(ItemStack bucket) {
        super.saveToBucketTag(bucket);
        CompoundNBT compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult result) {
        return new ItemStack(UAMItems.LEAFY_SEADRAGON_SPAWN_EGG.get());
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            if (random.nextInt(4) == 0) {
                setVariant(random.nextInt(3));
            }
            else if (random.nextInt(2) == 1) {
                setVariant(0);
            }
            else {
                setVariant(3);
            }
        }
        else {
            if (nbt.contains("Variant", 3)) {
                this.setVariant(nbt.getInt("Variant"));
            }
        }
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, nbt);
    }

    @Override
    protected ItemStack getBucketItemStack() {
        return new ItemStack(UAMItems.LEAFY_SEADRAGON_BUCKET.get());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D);
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
}
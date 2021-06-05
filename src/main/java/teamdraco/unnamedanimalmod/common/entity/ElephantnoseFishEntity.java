package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ElephantnoseFishEntity extends AbstractGroupFishEntity {
    public ElephantnoseFishEntity(EntityType<? extends ElephantnoseFishEntity> fish, World world) {
        super(fish, world);
    }

    @Override
    public int getMaxSchoolSize() {
        return 8;
    }

    protected ItemStack getBucketItemStack() {
        return new ItemStack(UAMItems.ELEPHANTNOSE_FISH_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.ELEPHANTNOSE_FISH_SPAWN_EGG.get());
    }
}
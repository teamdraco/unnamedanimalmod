package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

public class ElephantnoseFishEntity extends AbstractSchoolingFish {
    public ElephantnoseFishEntity(EntityType<? extends ElephantnoseFishEntity> fish, Level world) {
        super(fish, world);
    }

    @Override
    public int getMaxSchoolSize() {
        return 8;
    }

    public ItemStack getBucketItemStack() {
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
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(UAMItems.ELEPHANTNOSE_FISH_SPAWN_EGG.get());
    }
}

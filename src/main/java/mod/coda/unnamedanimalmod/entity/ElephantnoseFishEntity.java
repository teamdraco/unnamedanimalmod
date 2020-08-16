package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ElephantnoseFishEntity extends AbstractGroupFishEntity {
    public ElephantnoseFishEntity(EntityType<? extends ElephantnoseFishEntity> fish, World world) {
        super(fish, world);
    }

    protected ItemStack getFishBucket() {
        return new ItemStack(ItemInit.ELEPHANTNOSE_FISH_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }
}
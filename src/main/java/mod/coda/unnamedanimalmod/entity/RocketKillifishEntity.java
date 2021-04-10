package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RocketKillifishEntity extends AbstractGroupFishEntity {
    public RocketKillifishEntity(EntityType<? extends AbstractGroupFishEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(UAMItems.ROCKET_KILLIFISH_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    @Override
    public int getMaxGroupSize() {
        return 15;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.ROCKET_KILLIFISH_SPAWN_EGG.get());
    }
}

package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class RocketKillifishEntity extends AbstractFishEntity {
    public RocketKillifishEntity(EntityType<? extends AbstractFishEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(UAMItems.ROCKET_KILLIFISH_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }
}

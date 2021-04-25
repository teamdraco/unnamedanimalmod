package mod.coda.unnamedanimalmod.item;

import mod.coda.unnamedanimalmod.init.UAMEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class CookedMangroveFruitItem extends Item
{
    public CookedMangroveFruitItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        if (target instanceof MobEntity)
        {
            target.addPotionEffect(new EffectInstance(UAMEffects.FRUIT_RAGE.get(), 3000, 0));
            stack.shrink(1);
            playerIn.swing(hand, true);
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}

package teamdraco.unnamedanimalmod.common.item;

import teamdraco.unnamedanimalmod.common.entity.PacmanFrogEntity;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class PacmanFrogEggItem extends Item {
    public PacmanFrogEggItem(Properties builder) {
        super(builder);
    }


    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        PacmanFrogEntity child = UAMEntities.PACMAN_FROG.get().create(worldIn);
        if (!worldIn.isClientSide && child != null) {
            child.setAge(-24000);
            child.setPos(playerIn.getX(), playerIn.getY(), playerIn.getZ());
            worldIn.addFreshEntity(child);
            worldIn.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.success(itemstack);
    }
}
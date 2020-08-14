package mod.coda.unnamedanimalmod.items;

import mod.coda.unnamedanimalmod.entity.PacmanFrogEntity;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class ModEggItem extends Item {

    public ModEggItem(Item.Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        PacmanFrogEntity child = ModEntityTypes.PACMAN_FROG.create(worldIn);
        if (!worldIn.isRemote && child != null) {
            child.setGrowingAge(-24000);
            child.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
            worldIn.addEntity(child);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        return ActionResult.resultSuccess(itemstack);
    }
}
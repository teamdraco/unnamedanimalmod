package mod.coda.unnamedanimalmod.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ModPungiItem extends Item {
    public ModPungiItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
            playerIn.playSound(SoundEvents.ENTITY_GHAST_SCREAM, 10.0f, 1.0f);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}


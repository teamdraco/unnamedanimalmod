package mod.coda.unnamedanimalmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlubberItem extends Item {
    public BlubberItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 300;
    }
}

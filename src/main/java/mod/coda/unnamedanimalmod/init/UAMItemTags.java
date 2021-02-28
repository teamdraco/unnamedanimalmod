package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.Helper;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class UAMItemTags
{
    
    public static ITag.INamedTag<Item> MANGROVE_LOGS;
    
    public static ITag.INamedTag<Item> makeWrapperTag(String id)
    {
        return ItemTags.createOptional(Helper.prefix(id));
    }
    
    public static void init()
    {
        MANGROVE_LOGS = makeWrapperTag("mangrove_logs");
    }
}

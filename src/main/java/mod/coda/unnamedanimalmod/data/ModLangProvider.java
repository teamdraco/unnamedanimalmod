package mod.coda.unnamedanimalmod.data;

import mod.coda.unnamedanimalmod.Helper;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.init.UAMBlocks;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.Block;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ModLangProvider extends LanguageProvider
{
    public ModLangProvider(DataGenerator gen)
    {
        super(gen, UnnamedAnimalMod.MOD_ID, "en_us");
    }
    
    @Override
    protected void addTranslations()
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(UAMBlocks.REGISTRY.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(UAMItems.REGISTRY.getEntries());
//        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(ENCHANTMENTS.getEntries());
//        Set<RegistryObject<Effect>> effects = new HashSet<>(EFFECTS.getEntries());
        Helper.takeAll(items, i -> i.get() instanceof BlockItem);
        Helper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        blocks.forEach(b -> {
            String name = b.get().getTranslationKey().replaceFirst("block." + UnnamedAnimalMod.MOD_ID + ".", "");
            name = Helper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getTranslationKey(), name);
        });
        
        items.forEach(i -> {
            if (!(i.get() instanceof BlockItem))
            {
                String name = i.get().getTranslationKey().replaceFirst("item." + UnnamedAnimalMod.MOD_ID + ".", "");
                name = Helper.toTitleCase(specialBlockNameChanges(name), "_");
                add(i.get().getTranslationKey(), name);
            }
        });
        
//        enchantments.forEach(e -> {
//            String name = YourModNameHelper.toTitleCase(e.getId().getPath(), "_");
//            add(e.get().getName(), name);
//        });
//
//        effects.forEach(e -> {
//            String name = YourModNameHelper.toTitleCase(e.getId().getPath(), "_");
//            add("effect.modname." + e.get().getRegistryName().getPath(), name);
//        });
        
        add("itemGroup." + UnnamedAnimalMod.MOD_ID, Helper.toTitleCase(UnnamedAnimalMod.MOD_ID, "_"));
    }
    
    @Override
    public String getName()
    {
        return "Lang Entries";
    }
    
    public void addTooltip(String identifier, String tooltip)
    {
        add("modname.tooltip." + identifier, tooltip);
    }
    public String specialBlockNameChanges(String name)
    {
        if ((!name.endsWith("_bricks")))
        {
            if (name.contains("bricks"))
            {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button"))
        {
            if (name.contains("planks"))
            {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}
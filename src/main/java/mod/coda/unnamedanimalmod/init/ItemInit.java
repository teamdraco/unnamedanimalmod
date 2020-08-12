package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.items.ModSpawnEggItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Main.MOD_ID);

//    public static final RegistryObject<FishBucketItem> HUMPHEAD_PARROTFISH_BUCKET = ITEMS.register("humphead_parrotfish_bucket", () -> new FishBucketItem(ModEntityTypes.HUMPHEAD_PARROTFISH.get(), Fluids.WATER, new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)));
    public static final RegistryObject<ModSpawnEggItem> FIRE_SALAMANDER_SPAWN_EGG = ITEMS.register("fire_salamander_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.FIRE_SALAMANDER_ENTITY, 0x14151b, 0xffab00, new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<ModSpawnEggItem> HORNED_VIPER_SPAWN_EGG = ITEMS.register("horned_viper_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.HORNED_VIPER_ENTITY, 0xd7c390, 0x644630, new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<ModSpawnEggItem> HUMPHEAD_PARROTFISH_SPAWN_EGG = ITEMS.register("humphead_parrotfish_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.HUMPHEAD_PARROTFISH, 0x40705c, 0xd29cac, new Item.Properties().group(ItemGroup.MISC)));

}

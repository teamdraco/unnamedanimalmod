package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.item.UAMSpawnEggItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<Item> HONDURAN_WHITE_BAT_SPAWN_EGG = REGISTRY.register("honduran_white_bat_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.HONDURAN_WHITE_BAT, 0xf6f4e8, 0xf7ce62, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> VINE_SNAKE_SPAWN_EGG = REGISTRY.register("vine_snake_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.VINE_SNAKE, 0x2e5129, 0x71bc53, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> BLACK_DIAMOND_STINGRAY_SPAWN_EGG = REGISTRY.register("black_diamond_stingray_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.BLACK_DIAMOND_STINGRAY, 0x35374e, 0xf2f3fe, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> TOMATO_FROG_SPAWN_EGG = REGISTRY.register("tomato_frog_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.TOMATO_FROG, 0x961900, 0xd05a10, new Item.Properties().group(UnnamedAnimalMod.GROUP)));

    public static final RegistryObject<Item> BLACK_DIAMOND_STINGRAY_BUCKET = REGISTRY.register("black_diamond_stingray_bucket", () -> new FishBucketItem(() -> UAMEntities.BLACK_DIAMOND_STINGRAY.get(), () -> Fluids.WATER, new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(1)));
}
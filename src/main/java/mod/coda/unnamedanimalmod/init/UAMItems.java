package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.entity.item.PlatypusEggEntity;
import mod.coda.unnamedanimalmod.item.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<Item> BLACK_DIAMOND_STINGRAY_BUCKET = REGISTRY.register("black_diamond_stingray_bucket", () -> new FishBucketItem(UAMEntities.BLACK_DIAMOND_STINGRAY, () -> Fluids.WATER, new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> TOMATO_FROG_EGG = REGISTRY.register("tomato_frog_egg", () -> new TomatoFrogEggItem(new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(16)));
    public static final RegistryObject<Item> BLUBBER = REGISTRY.register("blubber", () -> new BlubberItem(new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> GREATER_PRAIRIE_CHICKEN_EGG = REGISTRY.register("greater_prairie_chicken_egg", () -> new GreaterPrairieChickenEggItem(new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(16)));
    public static final RegistryObject<Item> FRIED_PRAIRIE_CHICKEN_EGG = REGISTRY.register("fried_prairie_chicken_egg", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(64).food(new Food.Builder().saturation(0.6f).hunger(6).build())));
    public static final RegistryObject<Item> PLATYPUS_EGG = REGISTRY.register("platypus_egg", () -> new PlatypusEggItem(new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(16)));
    public static final RegistryObject<Item> MARINE_IGUANA_EGG = REGISTRY.register("marine_iguana_egg", () -> new MarineIguanaEggItem(new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(16)));
    public static final RegistryObject<Item> SALT = REGISTRY.register("salt", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> ELEPHANTNOSE_FISH_BUCKET = REGISTRY.register("elephantnose_fish_bucket", () -> new FishBucketItem(UAMEntities.ELEPHANTNOSE_FISH, () -> Fluids.WATER, new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> ELEPHANTNOSE_FISH = REGISTRY.register("elephantnose_fish", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP).food(new Food.Builder().saturation(0.2f).hunger(1).build())));
    public static final RegistryObject<Item> MUSK_OX_SHANK = REGISTRY.register("musk_ox_shank", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP).food(new Food.Builder().hunger(4).saturation(0.2f).effect(new EffectInstance(Effects.SLOWNESS, 100, 0), 0.75f).meat().build())));
    public static final RegistryObject<Item> COOKED_MUSK_OX_SHANK = REGISTRY.register("cooked_musk_ox_shank", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP).food(new Food.Builder().hunger(10).saturation(0.6f).effect(new EffectInstance(Effects.SLOWNESS, 100, 0), 0.25f).meat().build())));
    public static final RegistryObject<Item> PLATYPUS_BUCKET = REGISTRY.register("platypus_bucket", () -> new UAMBucketItem(() -> UAMEntities.PLATYPUS.get(), new Item.Properties().maxStackSize(1).group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> BANANA_SLUG_POT = REGISTRY.register("banana_slug_pot", () -> new UAMPotItem(() -> UAMEntities.BANANA_SLUG.get(), new Item.Properties().maxStackSize(1).group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> TOMATO_FROG_BOWL = REGISTRY.register("tomato_frog_bowl", () -> new UAMBowltem(() -> UAMEntities.TOMATO_FROG.get(), new Item.Properties().maxStackSize(1).group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> FROG_LEGS = REGISTRY.register("frog_legs", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP).food(new Food.Builder().hunger(2).saturation(0.2f).meat().build())));
    public static final RegistryObject<Item> COOKED_FROG_LEGS = REGISTRY.register("cooked_frog_legs", () -> new Item(new Item.Properties().group(UnnamedAnimalMod.GROUP).food(new Food.Builder().hunger(5).saturation(0.3f).meat().build())));
    public static final RegistryObject<Item> PACMAN_FROG_EGG = REGISTRY.register("pacman_frog_egg", () -> new PacmanFrogEggItem(new Item.Properties().group(UnnamedAnimalMod.GROUP).maxStackSize(16)));
    public static final RegistryObject<Item> PACMAN_FROG_BOWL = REGISTRY.register("pacman_frog_bowl", () -> new UAMBowltem(() -> UAMEntities.PACMAN_FROG.get(), new Item.Properties().maxStackSize(1).group(UnnamedAnimalMod.GROUP)));

    //public static final RegistryObject<Item> HONDURAN_WHITE_BAT_SPAWN_EGG = REGISTRY.register("honduran_white_bat_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.HONDURAN_WHITE_BAT, 0xf6f4e8, 0xf7ce62, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    //public static final RegistryObject<Item> VINE_SNAKE_SPAWN_EGG = REGISTRY.register("vine_snake_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.VINE_SNAKE, 0x2e5129, 0x71bc53, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> BLACK_DIAMOND_STINGRAY_SPAWN_EGG = REGISTRY.register("black_diamond_stingray_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.BLACK_DIAMOND_STINGRAY, 0x35374e, 0xf2f3fe, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> TOMATO_FROG_SPAWN_EGG = REGISTRY.register("tomato_frog_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.TOMATO_FROG, 0x961900, 0xf1800a, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> SOUTHERN_RIGHT_WHALE_SPAWN_EGG = REGISTRY.register("southern_right_whale_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.SOUTHERN_RIGHT_WHALE, 0x263334, 0xcdcabc, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> GREATER_PRAIRIE_CHICKEN_SPAWN_EGG = REGISTRY.register("greater_prairie_chicken_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.GREATER_PRAIRIE_CHICKEN, 0x4e4340, 0xeda825, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> FLASHLIGHT_FISH_SPAWN_EGG = REGISTRY.register("flashlight_fish_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.FLASHLIGHT_FISH, 0x1a0d11, 0xf7ffff, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> HUMPHEAD_PARROTFISH_SPAWN_EGG = REGISTRY.register("humphead_parrotfish_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.HUMPHEAD_PARROTFISH, 0x447e62, 0xd29cac, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> MUSK_OX_SPAWN_EGG = REGISTRY.register("musk_ox_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.MUSK_OX, 0x402b27, 0x1e1413, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> BANANA_SLUG_SPAWN_EGG = REGISTRY.register("banana_slug_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.BANANA_SLUG, 0xe2ba4c, 0xa78330, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> MARINE_IGUANA_SPAWN_EGG = REGISTRY.register("marine_iguana_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.MARINE_IGUANA, 0x27272d, 0x78f7d4, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> PLATYPUS_SPAWN_EGG = REGISTRY.register("platypus_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.PLATYPUS, 0x71492a, 0x544b38, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> PACMAN_FROG_SPAWN_EGG = REGISTRY.register("pacman_frog_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.PACMAN_FROG, 0x9a9c26, 0x3d1a0c, new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    
    public static final RegistryObject<Item> SALT_BLOCK = REGISTRY.register("salt_block", () -> new BlockItem(UAMBlocks.SALT_BLOCK.get(), new Item.Properties().group(UnnamedAnimalMod.GROUP)));
    public static final RegistryObject<Item> MANGROVE_SAPLING = REGISTRY.register("mangrove_sapling", () -> new BlockItem(UAMBlocks.MANGROVE_SAPLING.get(), new Item.Properties().group(UnnamedAnimalMod.GROUP)));
}
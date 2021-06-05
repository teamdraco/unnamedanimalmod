package teamdraco.unnamedanimalmod.init;

import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
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
import teamdraco.unnamedanimalmod.common.item.*;

public class UAMItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UnnamedAnimalMod.MOD_ID);

    // Drops & Materials
    public static final RegistryObject<Item> BLUBBER = REGISTRY.register("blubber", () -> new BlubberItem(defaultProperties()));
    public static final RegistryObject<Item> SALT = REGISTRY.register("salt", () -> new BlockItem(UAMBlocks.SALT.get(), defaultProperties()));
    public static final RegistryObject<Item> MUD_BALL = REGISTRY.register("mud_ball", () -> new MudBallItem(defaultProperties()));
    public static final RegistryObject<Item> MUD_BRICK = REGISTRY.register("mud_brick", () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> DRIED_MUD_FRAGMENT = REGISTRY.register("dried_mud_fragment", () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> DRIED_MUD_BRICK = REGISTRY.register("dried_mud_brick", () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_BOAT = REGISTRY.register("mangrove_boat", () -> new MangroveBoatItem(defaultProperties().stacksTo(1)));

    // Food
    public static final RegistryObject<Item> FRIED_PRAIRIE_CHICKEN_EGG = REGISTRY.register("fried_prairie_chicken_egg", () -> new Item(defaultProperties().stacksTo(64).food(new Food.Builder().saturationMod(0.6f).nutrition(6).build())));
    public static final RegistryObject<Item> ELEPHANTNOSE_FISH = REGISTRY.register("elephantnose_fish", () -> new Item(defaultProperties().food(new Food.Builder().saturationMod(0.2f).nutrition(1).build())));
    public static final RegistryObject<Item> FLASHLIGHT_FISH = REGISTRY.register("flashlight_fish", () -> new Item(defaultProperties().food(new Food.Builder().saturationMod(0.2f).nutrition(1).effect(() -> new EffectInstance(Effects.GLOWING, 100, 1), 1F).build())));
    public static final RegistryObject<Item> ROCKET_KILLIFISH = REGISTRY.register("rocket_killifish", () -> new Item(defaultProperties().food(new Food.Builder().saturationMod(0.2f).nutrition(1).build())));
    public static final RegistryObject<Item> MUSK_OX_SHANK = REGISTRY.register("musk_ox_shank", () -> new Item(defaultProperties().food(new Food.Builder().nutrition(4).saturationMod(0.2f).effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0), 0.75f).meat().build())));
    public static final RegistryObject<Item> COOKED_MUSK_OX_SHANK = REGISTRY.register("cooked_musk_ox_shank", () -> new Item(defaultProperties().food(new Food.Builder().nutrition(10).saturationMod(0.6f).effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0), 0.25f).meat().build())));
    public static final RegistryObject<Item> FROG_LEGS = REGISTRY.register("frog_legs", () -> new Item(defaultProperties().food(new Food.Builder().nutrition(2).saturationMod(0.2f).meat().build())));
    public static final RegistryObject<Item> COOKED_FROG_LEGS = REGISTRY.register("cooked_frog_legs", () -> new Item(defaultProperties().food(new Food.Builder().nutrition(5).saturationMod(0.3f).meat().build())));
    public static final RegistryObject<Item> MANGROVE_FRUIT = REGISTRY.register("mangrove_fruit", () -> new BlockItem(UAMBlocks.MANGROVE_FRUIT.get(), defaultProperties().food(new Food.Builder().nutrition(2).saturationMod(0.1f).effect(() -> new EffectInstance(Effects.HUNGER, 100, 0), 1f).build())));
    public static final RegistryObject<Item> COOKED_MANGROVE_FRUIT = REGISTRY.register("cooked_mangrove_fruit", () -> new Item(defaultProperties().food(new Food.Builder().nutrition(6).saturationMod(0.5f).build())));

    // Eggs & Buckets
    public static final RegistryObject<Item> TOMATO_FROG_EGG = REGISTRY.register("tomato_frog_egg", () -> new FrogEggItem(UAMEntities.TOMATO_FROG::get, defaultProperties().stacksTo(16)));
    public static final RegistryObject<Item> PACMAN_FROG_EGG = REGISTRY.register("pacman_frog_egg", () -> new FrogEggItem(UAMEntities.PACMAN_FROG::get, defaultProperties().stacksTo(16)));
    public static final RegistryObject<Item> GREATER_PRAIRIE_CHICKEN_EGG = REGISTRY.register("greater_prairie_chicken_egg", () -> new GreaterPrairieChickenEggItem(defaultProperties().stacksTo(16)));
    public static final RegistryObject<Item> PLATYPUS_EGG = REGISTRY.register("platypus_egg", () -> new PlatypusEggItem(defaultProperties().stacksTo(16)));
    public static final RegistryObject<Item> MARINE_IGUANA_EGG = REGISTRY.register("marine_iguana_egg", () -> new MarineIguanaEggItem(defaultProperties().stacksTo(16)));
    public static final RegistryObject<Item> FLASHLIGHT_FISH_BUCKET = REGISTRY.register("flashlight_fish_bucket", () -> new FishBucketItem(UAMEntities.FLASHLIGHT_FISH, () -> Fluids.WATER, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> PLATYPUS_BUCKET = REGISTRY.register("platypus_bucket", () -> new UAMBucketItem(UAMEntities.PLATYPUS::get, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> ELEPHANTNOSE_FISH_BUCKET = REGISTRY.register("elephantnose_fish_bucket", () -> new FishBucketItem(UAMEntities.ELEPHANTNOSE_FISH, () -> Fluids.WATER, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> BLACK_DIAMOND_STINGRAY_BUCKET = REGISTRY.register("black_diamond_stingray_bucket", () -> new FishBucketItem(UAMEntities.BLACK_DIAMOND_STINGRAY, () -> Fluids.WATER, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> BANANA_SLUG_POT = REGISTRY.register("banana_slug_pot", () -> new UAMPotItem(UAMEntities.BANANA_SLUG::get, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> TOMATO_FROG_BOWL = REGISTRY.register("tomato_frog_bowl", () -> new UAMBowltem(UAMEntities.TOMATO_FROG::get, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> PACMAN_FROG_BOWL = REGISTRY.register("pacman_frog_bowl", () -> new UAMBowltem(UAMEntities.PACMAN_FROG::get, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> ROCKET_KILLIFISH_BUCKET = REGISTRY.register("rocket_killifish_bucket", () -> new FishBucketItem(UAMEntities.ROCKET_KILLIFISH, () -> Fluids.WATER, defaultProperties().stacksTo(1)));
    public static final RegistryObject<Item> BABY_HUMPHEAD_PARROTFISH_BUCKET = REGISTRY.register("baby_humphead_parrotfish_bucket", () -> new BabyHumpheadParrotfishBucketItem(() -> UAMEntities.HUMPHEAD_PARROTFISH.get(), () -> Fluids.WATER, defaultProperties().stacksTo(1)));
    // public static final RegistryObject<Item> BLUBBER_JELLY_BUCKET = REGISTRY.register("blubber_jelly_bucket", () -> new UAMWaterBucketItem(() -> UAMEntities.BLUBBER_JELLY.get(), defaultProperties().maxStackSize(1)));
     public static final RegistryObject<Item> FIDDLER_CRAB_BUCKET = REGISTRY.register("fiddler_crab_bucket", () -> new UAMWaterBucketItem(() -> UAMEntities.FIDDLER_CRAB.get(), defaultProperties().stacksTo(1)));

    // Spawn Eggs
    public static final RegistryObject<Item> BLACK_DIAMOND_STINGRAY_SPAWN_EGG = REGISTRY.register("black_diamond_stingray_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.BLACK_DIAMOND_STINGRAY, 0x35374e, 0xf2f3fe, defaultProperties()));
    public static final RegistryObject<Item> TOMATO_FROG_SPAWN_EGG = REGISTRY.register("tomato_frog_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.TOMATO_FROG, 0x961900, 0xf1800a, defaultProperties()));
    public static final RegistryObject<Item> SOUTHERN_RIGHT_WHALE_SPAWN_EGG = REGISTRY.register("southern_right_whale_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.SOUTHERN_RIGHT_WHALE, 0x263334, 0xcdcabc, defaultProperties()));
    public static final RegistryObject<Item> GREATER_PRAIRIE_CHICKEN_SPAWN_EGG = REGISTRY.register("greater_prairie_chicken_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.GREATER_PRAIRIE_CHICKEN, 0x4e4340, 0xeda825, defaultProperties()));
    public static final RegistryObject<Item> FLASHLIGHT_FISH_SPAWN_EGG = REGISTRY.register("flashlight_fish_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.FLASHLIGHT_FISH, 0x1a0d11, 0xf7ffff, defaultProperties()));
    public static final RegistryObject<Item> HUMPHEAD_PARROTFISH_SPAWN_EGG = REGISTRY.register("humphead_parrotfish_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.HUMPHEAD_PARROTFISH, 0x3c667f, 0x6cb8a2, defaultProperties()));
    public static final RegistryObject<Item> MUSK_OX_SPAWN_EGG = REGISTRY.register("musk_ox_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.MUSK_OX, 0x402b27, 0x1e1413, defaultProperties()));
    public static final RegistryObject<Item> BANANA_SLUG_SPAWN_EGG = REGISTRY.register("banana_slug_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.BANANA_SLUG, 0xe2ba4c, 0xa78330, defaultProperties()));
    public static final RegistryObject<Item> MARINE_IGUANA_SPAWN_EGG = REGISTRY.register("marine_iguana_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.MARINE_IGUANA, 0x27272d, 0x78f7d4, defaultProperties()));
    public static final RegistryObject<Item> PLATYPUS_SPAWN_EGG = REGISTRY.register("platypus_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.PLATYPUS, 0x71492a, 0x544b38, defaultProperties()));
    public static final RegistryObject<Item> PACMAN_FROG_SPAWN_EGG = REGISTRY.register("pacman_frog_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.PACMAN_FROG, 0x9a9c26, 0x3d1a0c, defaultProperties()));
    public static final RegistryObject<Item> ELEPHANTNOSE_FISH_SPAWN_EGG = REGISTRY.register("elephantnose_fish_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.ELEPHANTNOSE_FISH, 0x4c3b36, 0xc9c1b9, defaultProperties()));
    public static final RegistryObject<Item> CAPYBARA_SPAWN_EGG = REGISTRY.register("capybara_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.CAPYBARA, 0x9e5d39, 0x412f24, defaultProperties()));
    public static final RegistryObject<Item> ROCKET_KILLIFISH_SPAWN_EGG = REGISTRY.register("rocket_killifish_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.ROCKET_KILLIFISH, 0x172639, 0x49fff2, defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_SNAKE_SPAWN_EGG = REGISTRY.register("mangrove_snake_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.MANGROVE_SNAKE, 0x202023, 0xda9409, defaultProperties()));
    // public static final RegistryObject<Item> BLUBBER_JELLY_SPAWN_EGG = REGISTRY.register("blubber_jelly_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.BLUBBER_JELLY, 0x50ccb7, 0x50ccb7, defaultProperties()));
    public static final RegistryObject<Item> FIDDLER_CRAB_SPAWN_EGG = REGISTRY.register("fiddler_crab_spawn_egg", () -> new UAMSpawnEggItem(UAMEntities.FIDDLER_CRAB, 0xb7510e, 0xf7bb4b, defaultProperties()));

    // Blocks
    public static final RegistryObject<Item> SALT_BLOCK = REGISTRY.register("salt_block", () -> new BlockItem(UAMBlocks.SALT_BLOCK.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_SAPLING = REGISTRY.register("mangrove_sapling", () -> new BlockItem(UAMBlocks.MANGROVE_SAPLING.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_LOG = REGISTRY.register("mangrove_log", () -> new BlockItem(UAMBlocks.MANGROVE_LOG.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_WOOD = REGISTRY.register("mangrove_wood", () -> new BlockItem(UAMBlocks.MANGROVE_WOOD.get(), defaultProperties()));
    public static final RegistryObject<Item> STRIPPED_MANGROVE_LOG = REGISTRY.register("stripped_mangrove_log", () -> new BlockItem(UAMBlocks.STRIPPED_MANGROVE_LOG.get(), defaultProperties()));
    public static final RegistryObject<Item> STRIPPED_MANGROVE_WOOD = REGISTRY.register("stripped_mangrove_wood", () -> new BlockItem(UAMBlocks.STRIPPED_MANGROVE_WOOD.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_PLANKS = REGISTRY.register("mangrove_planks", () -> new BlockItem(UAMBlocks.MANGROVE_PLANKS.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_SLAB = REGISTRY.register("mangrove_slab", () -> new BlockItem(UAMBlocks.MANGROVE_SLAB.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_STAIRS = REGISTRY.register("mangrove_stairs", () -> new BlockItem(UAMBlocks.MANGROVE_STAIRS.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_DOOR = REGISTRY.register("mangrove_door", () -> new BlockItem(UAMBlocks.MANGROVE_DOOR.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_TRAPDOOR = REGISTRY.register("mangrove_trapdoor", () -> new BlockItem(UAMBlocks.MANGROVE_TRAPDOOR.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_BUTTON = REGISTRY.register("mangrove_button", () -> new BlockItem(UAMBlocks.MANGROVE_BUTTON.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_PRESSURE_PLATE = REGISTRY.register("mangrove_pressure_plate", () -> new BlockItem(UAMBlocks.MANGROVE_PRESSURE_PLATE.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_FENCE = REGISTRY.register("mangrove_fence", () -> new BlockItem(UAMBlocks.MANGROVE_FENCE.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_FENCE_GATE = REGISTRY.register("mangrove_fence_gate", () -> new BlockItem(UAMBlocks.MANGROVE_FENCE_GATE.get(), defaultProperties()));
    public static final RegistryObject<Item> MANGROVE_LEAVES = REGISTRY.register("mangrove_leaves", () -> new BlockItem(UAMBlocks.MANGROVE_LEAVES.get(), defaultProperties()));
    public static final RegistryObject<Item> FLOWERING_MANGROVE_LEAVES = REGISTRY.register("flowering_mangrove_leaves", () -> new BlockItem(UAMBlocks.FLOWERING_MANGROVE_LEAVES.get(), defaultProperties()));
    public static final RegistryObject<Item> MUD_BLOCK = REGISTRY.register("mud_block", () -> new BlockItem(UAMBlocks.MUD_BLOCK.get(), defaultProperties()));
    public static final RegistryObject<Item> MUD_BRICKS = REGISTRY.register("mud_bricks", () -> new BlockItem(UAMBlocks.MUD_BRICKS.get(), defaultProperties()));
    public static final RegistryObject<Item> CHISELED_MUD_BRICKS = REGISTRY.register("chiseled_mud_bricks", () -> new BlockItem(UAMBlocks.CHISELED_MUD_BRICKS.get(), defaultProperties()));
    public static final RegistryObject<Item> MUD_BRICKS_SLAB = REGISTRY.register("mud_brick_slab", () -> new BlockItem(UAMBlocks.MUD_BRICK_SLAB.get(), defaultProperties()));
    public static final RegistryObject<Item> MUD_BRICKS_STAIRS = REGISTRY.register("mud_brick_stairs", () -> new BlockItem(UAMBlocks.MUD_BRICK_STAIRS.get(), defaultProperties()));
    public static final RegistryObject<Item> DRIED_MUD_BLOCK = REGISTRY.register("dried_mud_block", () -> new BlockItem(UAMBlocks.DRIED_MUD_BLOCK.get(), defaultProperties()));
    public static final RegistryObject<Item> DRIED_MUD_BRICKS = REGISTRY.register("dried_mud_bricks", () -> new BlockItem(UAMBlocks.DRIED_MUD_BRICKS.get(), defaultProperties()));
    public static final RegistryObject<Item> CHISELED_DRIED_MUD_BRICKS = REGISTRY.register("chiseled_dried_mud_bricks", () -> new BlockItem(UAMBlocks.CHISELED_DRIED_MUD_BRICKS.get(), defaultProperties()));
    public static final RegistryObject<Item> DRIED_MUD_BRICKS_SLAB = REGISTRY.register("dried_mud_brick_slab", () -> new BlockItem(UAMBlocks.DRIED_MUD_BRICK_SLAB.get(), defaultProperties()));
    public static final RegistryObject<Item> DRIED_MUD_BRICKS_STAIRS = REGISTRY.register("dried_mud_brick_stairs", () -> new BlockItem(UAMBlocks.DRIED_MUD_BRICK_STAIRS.get(), defaultProperties()));
    public static final RegistryObject<Item> RICH_FARMLAND = REGISTRY.register("rich_farmland", () -> new BlockItem(UAMBlocks.RICH_FARMLAND.get(), defaultProperties()));

    public static Item.Properties defaultProperties() {
        return new Item.Properties().tab(UnnamedAnimalMod.GROUP);
    }
}

package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.entity.*;
import mod.coda.unnamedanimalmod.entity.item.GreaterPrairieChickenEggEntity;
import mod.coda.unnamedanimalmod.entity.item.MarineIguanaEggEntity;
import mod.coda.unnamedanimalmod.entity.item.PlatypusEggEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<EntityType<BlackDiamondStingrayEntity>> BLACK_DIAMOND_STINGRAY = create("black_diamond_stingray", EntityType.Builder.create(BlackDiamondStingrayEntity::new, EntityClassification.WATER_CREATURE).size(0.8f, 0.2f));
    public static final RegistryObject<EntityType<TomatoFrogEntity>> TOMATO_FROG = create("tomato_frog", EntityType.Builder.create(TomatoFrogEntity::new, EntityClassification.CREATURE).size(0.4f, 0.35f));
    public static final RegistryObject<EntityType<SouthernRightWhaleEntity>> SOUTHERN_RIGHT_WHALE = create("southern_right_whale", EntityType.Builder.create(SouthernRightWhaleEntity::new, EntityClassification.WATER_CREATURE).size(5.5f, 4.25f));
    public static final RegistryObject<EntityType<GreaterPrairieChickenEntity>> GREATER_PRAIRIE_CHICKEN = create("greater_prairie_chicken", EntityType.Builder.create(GreaterPrairieChickenEntity::new, EntityClassification.CREATURE).size(0.8f, 0.8f));
    public static final RegistryObject<EntityType<FlashlightFishEntity>> FLASHLIGHT_FISH = create("flashlight_fish", EntityType.Builder.create(FlashlightFishEntity::new, EntityClassification.WATER_AMBIENT).size(0.25f, 0.25f));
    public static final RegistryObject<EntityType<HumpheadParrotfishEntity>> HUMPHEAD_PARROTFISH = create("humphead_parrotfish", EntityType.Builder.create(HumpheadParrotfishEntity::new, EntityClassification.WATER_CREATURE).size(0.9f, 0.7f));
    public static final RegistryObject<EntityType<MuskOxEntity>> MUSK_OX = create("musk_ox", EntityType.Builder.create(MuskOxEntity::new, EntityClassification.CREATURE).size(1.2f, 1.3f));
    public static final RegistryObject<EntityType<BananaSlugEntity>> BANANA_SLUG = create("banana_slug", EntityType.Builder.create(BananaSlugEntity::new, EntityClassification.CREATURE).size(0.4f, 0.3f));
    public static final RegistryObject<EntityType<MarineIguanaEntity>> MARINE_IGUANA = create("marine_iguana", EntityType.Builder.create(MarineIguanaEntity::new, EntityClassification.CREATURE).size(0.6f, 0.4f));
    public static final RegistryObject<EntityType<PlatypusEntity>> PLATYPUS = create("platypus", EntityType.Builder.create(PlatypusEntity::new, EntityClassification.CREATURE).size(0.6f, 0.4f));
    public static final RegistryObject<EntityType<ElephantnoseFishEntity>> ELEPHANTNOSE_FISH = create("elephantnose_fish", EntityType.Builder.create(ElephantnoseFishEntity::new, EntityClassification.WATER_AMBIENT).size(0.3f, 0.3f));
    public static final RegistryObject<EntityType<PacmanFrogEntity>> PACMAN_FROG = create("pacman_frog", EntityType.Builder.create(PacmanFrogEntity::new, EntityClassification.CREATURE).size(0.5f, 0.4f));
    public static final RegistryObject<EntityType<CapybaraEntity>> CAPYBARA = create("capybara", EntityType.Builder.create(CapybaraEntity::new, EntityClassification.CREATURE).size(0.8f, 1.1f));
    public static final RegistryObject<EntityType<RocketKillifishEntity>> ROCKET_KILLIFISH = create("rocket_killifish", EntityType.Builder.create(RocketKillifishEntity::new, EntityClassification.WATER_AMBIENT).size(0.3f, 0.15f));
    public static final RegistryObject<EntityType<MangroveSnakeEntity>> MANGROVE_SNAKE = create("mangrove_snake", EntityType.Builder.create(MangroveSnakeEntity::new, EntityClassification.WATER_AMBIENT).size(1.0f, 0.3f));

    public static final RegistryObject<EntityType<GreaterPrairieChickenEggEntity>> GREATER_PRAIRIE_CHICKEN_EGG = create("greater_prairie_chicken_egg", EntityType.Builder.<GreaterPrairieChickenEggEntity>create(GreaterPrairieChickenEggEntity::new, EntityClassification.MISC).size(0.25f, 0.25f));
    public static final RegistryObject<EntityType<PlatypusEggEntity>> PLATYPUS_EGG = create("platypus_egg",EntityType.Builder.<PlatypusEggEntity>create(PlatypusEggEntity::new, EntityClassification.MISC).size(0.25f, 0.25f));
    public static final RegistryObject<EntityType<MarineIguanaEggEntity>> MARINE_IGUANA_EGG = create("marine_iguana_egg",EntityType.Builder.<MarineIguanaEggEntity>create(MarineIguanaEggEntity::new, EntityClassification.MISC).size(0.25f, 0.25f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(UnnamedAnimalMod.MOD_ID + "." + name));
    }
}
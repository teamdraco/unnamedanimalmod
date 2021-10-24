package teamdraco.unnamedanimalmod;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import teamdraco.unnamedanimalmod.common.entity.*;
import teamdraco.unnamedanimalmod.init.*;
import teamdraco.unnamedanimalmod.client.ClientEvents;

import java.util.ArrayList;
import java.util.List;

@Mod(UnnamedAnimalMod.MOD_ID)
public class UnnamedAnimalMod {
    public static final String MOD_ID = "unnamedanimalmod";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final List<Runnable> CALLBACKS = new ArrayList<>();

    public UnnamedAnimalMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerClient);
        bus.addListener(this::registerCommon);
        bus.addListener(this::registerEntityAttributes);

        UAMSounds.SOUNDS.register(bus);
        UAMBlocks.BLOCKS.register(bus);
        UAMItems.ITEMS.register(bus);
        UAMEntities.ENTITIES.register(bus);
        UAMFeatures.FEATURES.register(bus);
        UAMBiomes.BIOMES.register(bus);
        UAMBiomes.BUILDERS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UAMConfig.Common.SPEC);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.TOMATO_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.FLASHLIGHT_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlashlightFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.MUSK_OX.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MuskOxEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.MARINE_IGUANA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MarineIguanaEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.PLATYPUS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.BANANA_SLUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BananaSlugEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.ELEPHANTNOSE_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.PACMAN_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.ROCKET_KILLIFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.MANGROVE_SNAKE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SouthernRightWhaleEntity::checkWhaleSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.FIDDLER_CRAB.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FiddlerCrabEntity::canCrabSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.LEAFY_SEADRAGON.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LeafySeadragonEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.HUMPHEAD_PARROTFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HumpheadParrotfishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.SPOTTED_GARDEN_EEL.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.MUDSKIPPER.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MudskipperEntity::checkMudskipperSpawnRules);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
       event.put(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), BlackDiamondStingrayEntity.createAttributes().build());
       event.put(UAMEntities.TOMATO_FROG.get(), TomatoFrogEntity.createAttributes().build());
       event.put(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), SouthernRightWhaleEntity.createAttributes().build());
       event.put(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), GreaterPrairieChickenEntity.createAttributes().build());
       event.put(UAMEntities.FLASHLIGHT_FISH.get(), AbstractFishEntity.createAttributes().build());
       event.put(UAMEntities.HUMPHEAD_PARROTFISH.get(), HumpheadParrotfishEntity.createAttributes().build());
       event.put(UAMEntities.MUSK_OX.get(), MuskOxEntity.createAttributes().build());
       event.put(UAMEntities.BANANA_SLUG.get(), BananaSlugEntity.createAttributes().build());
       event.put(UAMEntities.MARINE_IGUANA.get(), MarineIguanaEntity.createAttributes().build());
       event.put(UAMEntities.PLATYPUS.get(), PlatypusEntity.createAttributes().build());
       event.put(UAMEntities.ELEPHANTNOSE_FISH.get(), AbstractFishEntity.createAttributes().build());
       event.put(UAMEntities.PACMAN_FROG.get(), PacmanFrogEntity.createAttributes().build());
       event.put(UAMEntities.CAPYBARA.get(), CapybaraEntity.createAttributes().build());
       event.put(UAMEntities.ROCKET_KILLIFISH.get(), AbstractFishEntity.createAttributes().build());
       event.put(UAMEntities.MANGROVE_SNAKE.get(), MangroveSnakeEntity.createAttributes().build());
       event.put(UAMEntities.FIDDLER_CRAB.get(), FiddlerCrabEntity.createAttributes().build());
       event.put(UAMEntities.LEAFY_SEADRAGON.get(), LeafySeadragonEntity.createAttributes().build());
       event.put(UAMEntities.SPOTTED_GARDEN_EEL.get(), SpottedGardenEelEntity.createAttributes().build());
       event.put(UAMEntities.MUDSKIPPER.get(), MudskipperEntity.createAttributes().build());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEvents.init();
        CALLBACKS.forEach(Runnable::run);
        CALLBACKS.clear();
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(UAMItems.MARINE_IGUANA_EGG.get());
        }
    };
}

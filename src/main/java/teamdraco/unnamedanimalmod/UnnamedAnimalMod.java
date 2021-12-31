package teamdraco.unnamedanimalmod;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teamdraco.unnamedanimalmod.common.entity.*;
import teamdraco.unnamedanimalmod.init.*;

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
        SpawnPlacements.register(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.TOMATO_FROG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.FLASHLIGHT_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlashlightFishEntity::checkFishSpawnRules);
        SpawnPlacements.register(UAMEntities.MUSK_OX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MuskOxEntity::canAnimalSpawn);
        SpawnPlacements.register(UAMEntities.MARINE_IGUANA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MarineIguanaEntity::canAnimalSpawn);
        SpawnPlacements.register(UAMEntities.PLATYPUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.BANANA_SLUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BananaSlugEntity::canAnimalSpawn);
        SpawnPlacements.register(UAMEntities.ELEPHANTNOSE_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.PACMAN_FROG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.ROCKET_KILLIFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.MANGROVE_SNAKE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SouthernRightWhaleEntity::checkWhaleSpawnRules);
        SpawnPlacements.register(UAMEntities.FIDDLER_CRAB.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FiddlerCrabEntity::canCrabSpawn);
        SpawnPlacements.register(UAMEntities.LEAFY_SEADRAGON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.HUMPHEAD_PARROTFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HumpheadParrotfishEntity::checkFishSpawnRules);
        SpawnPlacements.register(UAMEntities.SPOTTED_GARDEN_EEL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(UAMEntities.MUDSKIPPER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MudskipperEntity::checkMudskipperSpawnRules);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
       event.put(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), BlackDiamondStingrayEntity.createAttributes().build());
       event.put(UAMEntities.TOMATO_FROG.get(), TomatoFrogEntity.createAttributes().build());
       event.put(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), SouthernRightWhaleEntity.createAttributes().build());
       event.put(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), GreaterPrairieChickenEntity.createAttributes().build());
       event.put(UAMEntities.FLASHLIGHT_FISH.get(), AbstractFish.createAttributes().build());
       event.put(UAMEntities.HUMPHEAD_PARROTFISH.get(), HumpheadParrotfishEntity.createAttributes().build());
       event.put(UAMEntities.MUSK_OX.get(), MuskOxEntity.createAttributes().build());
       event.put(UAMEntities.BANANA_SLUG.get(), BananaSlugEntity.createAttributes().build());
       event.put(UAMEntities.MARINE_IGUANA.get(), MarineIguanaEntity.createAttributes().build());
       event.put(UAMEntities.PLATYPUS.get(), PlatypusEntity.createAttributes().build());
       event.put(UAMEntities.ELEPHANTNOSE_FISH.get(), AbstractFish.createAttributes().build());
       event.put(UAMEntities.PACMAN_FROG.get(), PacmanFrogEntity.createAttributes().build());
       event.put(UAMEntities.CAPYBARA.get(), CapybaraEntity.createAttributes().build());
       event.put(UAMEntities.ROCKET_KILLIFISH.get(), AbstractFish.createAttributes().build());
       event.put(UAMEntities.MANGROVE_SNAKE.get(), MangroveSnakeEntity.createAttributes().build());
       event.put(UAMEntities.FIDDLER_CRAB.get(), FiddlerCrabEntity.createAttributes().build());
       event.put(UAMEntities.LEAFY_SEADRAGON.get(), LeafySeadragonEntity.createAttributes().build());
       event.put(UAMEntities.SPOTTED_GARDEN_EEL.get(), SpottedGardenEelEntity.createAttributes().build());
       event.put(UAMEntities.MUDSKIPPER.get(), MudskipperEntity.createAttributes().build());
    }

    private void registerClient(FMLClientSetupEvent event) {
//        ClientEvents.init();
        CALLBACKS.forEach(Runnable::run);
        CALLBACKS.clear();
    }

    public final static CreativeModeTab GROUP = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(UAMItems.MARINE_IGUANA_EGG.get());
        }
    };
}

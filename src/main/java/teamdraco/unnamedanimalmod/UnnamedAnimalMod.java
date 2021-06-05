package teamdraco.unnamedanimalmod;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.Heightmap;
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
import teamdraco.unnamedanimalmod.client.ClientEventHandler;

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

        UAMSounds.REGISTRY.register(bus);
        UAMBlocks.REGISTRY.register(bus);
        UAMItems.REGISTRY.register(bus);
        UAMEntities.REGISTRY.register(bus);
        UAMFeatures.REGISTRY.register(bus);
        UAMBiomes.BIOMES.register(bus);
        UAMBiomes.BUILDERS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UAMConfig.Common.SPEC);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        EntitySpawnPlacementRegistry.register(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.TOMATO_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.FLASHLIGHT_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlashlightFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.MUSK_OX.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.MARINE_IGUANA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MarineIguanaEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.PLATYPUS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.BANANA_SLUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BananaSlugEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.ELEPHANTNOSE_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.PACMAN_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        EntitySpawnPlacementRegistry.register(UAMEntities.ROCKET_KILLIFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), BlackDiamondStingrayEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.TOMATO_FROG.get(), TomatoFrogEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), SouthernRightWhaleEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), GreaterPrairieChickenEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.FLASHLIGHT_FISH.get(), AbstractFishEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.HUMPHEAD_PARROTFISH.get(), HumpheadParrotfishEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.MUSK_OX.get(), MuskOxEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.BANANA_SLUG.get(), BananaSlugEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.MARINE_IGUANA.get(), MarineIguanaEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.PLATYPUS.get(), PlatypusEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.ELEPHANTNOSE_FISH.get(), AbstractFishEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.PACMAN_FROG.get(), PacmanFrogEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.CAPYBARA.get(), CapybaraEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(UAMEntities.ROCKET_KILLIFISH.get(), AbstractFishEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.MANGROVE_SNAKE.get(), MangroveSnakeEntity.createAttributes().build());
        // GlobalEntityTypeAttributes.put(UAMEntities.BLUBBER_JELLY.get(), BlubberJellyEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEventHandler.init();
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

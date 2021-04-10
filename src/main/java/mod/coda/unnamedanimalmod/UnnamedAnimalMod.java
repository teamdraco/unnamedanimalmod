package mod.coda.unnamedanimalmod;

import mod.coda.unnamedanimalmod.client.ClientEventHandler;
import mod.coda.unnamedanimalmod.entity.*;
import mod.coda.unnamedanimalmod.init.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(UnnamedAnimalMod.MOD_ID)
@Mod.EventBusSubscriber(modid = UnnamedAnimalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UnnamedAnimalMod {
    public static final String MOD_ID = "unnamedanimalmod";
    public static final Logger LOGGER = LogManager.getLogger();

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
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        EntitySpawnPlacementRegistry.register(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::func_223363_b);
        EntitySpawnPlacementRegistry.register(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.TOMATO_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.FLASHLIGHT_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlashlightFishEntity::func_223363_b);
        EntitySpawnPlacementRegistry.register(UAMEntities.MUSK_OX.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.MARINE_IGUANA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MarineIguanaEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.PLATYPUS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.BANANA_SLUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BananaSlugEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.ELEPHANTNOSE_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::func_223363_b);
        EntitySpawnPlacementRegistry.register(UAMEntities.PACMAN_FROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.ROCKET_KILLIFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::func_223363_b);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), BlackDiamondStingrayEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.TOMATO_FROG.get(), TomatoFrogEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), SouthernRightWhaleEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), GreaterPrairieChickenEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.FLASHLIGHT_FISH.get(), AbstractFishEntity.func_234176_m_().create());
        GlobalEntityTypeAttributes.put(UAMEntities.HUMPHEAD_PARROTFISH.get(), HumpheadParrotfishEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.MUSK_OX.get(), MuskOxEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.BANANA_SLUG.get(), BananaSlugEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.MARINE_IGUANA.get(), MarineIguanaEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.PLATYPUS.get(), PlatypusEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.ELEPHANTNOSE_FISH.get(), AbstractFishEntity.func_234176_m_().create());
        GlobalEntityTypeAttributes.put(UAMEntities.PACMAN_FROG.get(), PacmanFrogEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.CAPYBARA.get(), CapybaraEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.ROCKET_KILLIFISH.get(), AbstractFishEntity.func_234176_m_().create());
        GlobalEntityTypeAttributes.put(UAMEntities.MANGROVE_SNAKE.get(), MangroveSnakeEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEventHandler.init();
    }

    @SubscribeEvent
    public static void registerBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.JUNGLE) {
            event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), UAMConfig.Common.INSTANCE.blackDiamondStingraySpawnWeight.get(), 1, 1));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.TOMATO_FROG.get(), UAMConfig.Common.INSTANCE.tomatoFrogSpawnWeight.get(), 1, 2));
            event.getSpawns().getSpawner(EntityClassification.WATER_AMBIENT).add(new MobSpawnInfo.Spawners(UAMEntities.ELEPHANTNOSE_FISH.get(), UAMConfig.Common.INSTANCE.elephantnoseFishSpawnWeight.get(), 1, 5));
        }

        if (Biomes.SUNFLOWER_PLAINS.getLocation().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), 15, 4, 4));
        }

        if (event.getCategory() == Biome.Category.ICY) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.MUSK_OX.get(), 1, 2, 4));
        }

        if (Biomes.COLD_OCEAN.getLocation().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), 50, 2, 4));
        }

        if (Biomes.WARM_OCEAN.getLocation().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.HUMPHEAD_PARROTFISH.get(), 50, 1, 3));
            event.getSpawns().getSpawner(EntityClassification.WATER_AMBIENT).add(new MobSpawnInfo.Spawners(UAMEntities.FLASHLIGHT_FISH.get(), 10, 4, 8));
        }

        if (Biomes.STONE_SHORE.getLocation().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.MARINE_IGUANA.get(), 15, 4, 6));
        }

        if (Biomes.GIANT_TREE_TAIGA.getLocation().equals(event.getName()) || Biomes.GIANT_TREE_TAIGA_HILLS.getLocation().equals(event.getName()) || Biomes.GIANT_SPRUCE_TAIGA_HILLS.getLocation().equals(event.getName()) || Biomes.GIANT_SPRUCE_TAIGA.getLocation().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.BANANA_SLUG.get(), 45, 1, 1));
        }

        if (event.getCategory() == Biome.Category.SWAMP) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.PLATYPUS.get(), 5, 1, 1));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.PACMAN_FROG.get(), 2, 1, 2));
        }
    }

    @SubscribeEvent
    public static void spawnEntity(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof WolfEntity) {
            ((WolfEntity) entity).targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((MobEntity) entity, MuskOxEntity.class, true));
        }
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(UAMItems.MARINE_IGUANA_EGG.get());
        }
    };
}

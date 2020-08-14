package mod.coda.unnamedanimalmod;

import mod.coda.unnamedanimalmod.entity.HornedViperEntity;
import mod.coda.unnamedanimalmod.entity.HumpheadParrotfishEntity;
import mod.coda.unnamedanimalmod.entity.PigNosedTurtleEntity;
import mod.coda.unnamedanimalmod.init.ItemInit;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import mod.coda.unnamedanimalmod.init.SoundInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Main {
    public static final String MOD_ID = "unnamedanimalmod";
    public static Main instance;

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        SoundInit.SOUNDS.register(modEventBus);
        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
            Biomes.DARK_FOREST.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.FIRE_SALAMANDER, 30, 1, 2));
            EntitySpawnPlacementRegistry.register(ModEntityTypes.FIRE_SALAMANDER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, AnimalEntity::canAnimalSpawn);

            Biomes.DESERT.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.HORNED_VIPER, 30, 1, 1));
            EntitySpawnPlacementRegistry.register(ModEntityTypes.HORNED_VIPER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, HornedViperEntity::canViperSpawn);

            Biomes.WARM_OCEAN.getSpawns(EntityClassification.WATER_CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.HUMPHEAD_PARROTFISH, 30, 2, 5));
            EntitySpawnPlacementRegistry.register(ModEntityTypes.HUMPHEAD_PARROTFISH, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HumpheadParrotfishEntity::canFishSpawn);

            Biomes.SWAMP.getSpawns(EntityClassification.WATER_CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.PIG_NOSED_TURTLE, 2, 2, 2));
            EntitySpawnPlacementRegistry.register(ModEntityTypes.PIG_NOSED_TURTLE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PigNosedTurtleEntity::canAnimalSpawn);

            Biomes.JUNGLE.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.PACMAN_FROG, 3, 2, 4));
            EntitySpawnPlacementRegistry.register(ModEntityTypes.PACMAN_FROG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);

            Biomes.SNOWY_TUNDRA.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.MUSK_OX, 14, 5, 8));
            EntitySpawnPlacementRegistry.register(ModEntityTypes.MUSK_OX, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
    }

    private void doClientStuff(final FMLCommonSetupEvent event) {
    }

    public final static ItemGroup GROUP = new ItemGroup("uam_item_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.HORNED_VIPER_ITEM.get());
        }
    };
}
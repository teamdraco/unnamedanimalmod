package mod.coda.unnamedanimalmod;

import mod.coda.unnamedanimalmod.client.ClientEventHandler;
import mod.coda.unnamedanimalmod.entity.BlackDiamondStingrayEntity;
import mod.coda.unnamedanimalmod.entity.HonduranWhiteBatEntity;
import mod.coda.unnamedanimalmod.entity.TomatoFrogEntity;
import mod.coda.unnamedanimalmod.entity.VineSnakeEntity;
import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UnnamedAnimalMod.MOD_ID)
@Mod.EventBusSubscriber(modid = UnnamedAnimalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UnnamedAnimalMod {
    public static final String MOD_ID = "unnamedanimalmod";

    public UnnamedAnimalMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerClient);
        bus.addListener(this::registerCommon);

        UAMItems.REGISTRY.register(bus);
        UAMEntities.REGISTRY.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        EntitySpawnPlacementRegistry.register(UAMEntities.HONDURAN_WHITE_BAT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.VINE_SNAKE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::func_223363_b);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(UAMEntities.HONDURAN_WHITE_BAT.get(), HonduranWhiteBatEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.VINE_SNAKE.get(), VineSnakeEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), BlackDiamondStingrayEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(UAMEntities.TOMATO_FROG.get(), TomatoFrogEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEventHandler.init();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        Biome.Climate climate = event.getClimate();
        switch (event.getCategory()) {
            case JUNGLE:
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.HONDURAN_WHITE_BAT.get(), 1, 2, 3));
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.VINE_SNAKE.get(), 2, 1, 1));
                break;
        }
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(UAMItems.HONDURAN_WHITE_BAT_SPAWN_EGG.get());}
    };
}

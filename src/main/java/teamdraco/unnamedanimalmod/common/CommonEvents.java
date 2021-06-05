package teamdraco.unnamedanimalmod.common;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import teamdraco.unnamedanimalmod.UAMConfig;
import teamdraco.unnamedanimalmod.common.block.RichFarmlandBlock;
import teamdraco.unnamedanimalmod.common.entity.MuskOxEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teamdraco.unnamedanimalmod.init.UAMEntities;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent
    public static void doSpawning(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.JUNGLE) {
            event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), UAMConfig.Common.INSTANCE.blackDiamondStingraySpawnWeight.get(), 1, 1));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.TOMATO_FROG.get(), UAMConfig.Common.INSTANCE.tomatoFrogSpawnWeight.get(), 1, 2));
            event.getSpawns().getSpawner(EntityClassification.WATER_AMBIENT).add(new MobSpawnInfo.Spawners(UAMEntities.ELEPHANTNOSE_FISH.get(), UAMConfig.Common.INSTANCE.elephantnoseFishSpawnWeight.get(), 1, 5));
        }

        if (Biomes.SUNFLOWER_PLAINS.location().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), UAMConfig.Common.INSTANCE.greaterPrairieChickenSpawnWeight.get(), 4, 4));
        }

        if (event.getCategory() == Biome.Category.ICY) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.MUSK_OX.get(), UAMConfig.Common.INSTANCE.muskOxSpawnWeight.get(), 2, 4));
        }

        if (Biomes.COLD_OCEAN.location().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), UAMConfig.Common.INSTANCE.southernRightWhaleSpawnWeight.get(), 1, 3));
        }

        if (Biomes.WARM_OCEAN.location().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.HUMPHEAD_PARROTFISH.get(), UAMConfig.Common.INSTANCE.humpheadParrotfishSpawnWeight.get(), 1, 3));
            event.getSpawns().getSpawner(EntityClassification.WATER_AMBIENT).add(new MobSpawnInfo.Spawners(UAMEntities.FLASHLIGHT_FISH.get(), UAMConfig.Common.INSTANCE.flashlightFishSpawnWeight.get(), 4, 8));
        }

        if (Biomes.STONE_SHORE.location().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.MARINE_IGUANA.get(), UAMConfig.Common.INSTANCE.marineIguanaSpawnWeight.get(), 4, 6));
        }

        if (Biomes.GIANT_TREE_TAIGA.location().equals(event.getName()) || Biomes.GIANT_TREE_TAIGA_HILLS.location().equals(event.getName()) || Biomes.GIANT_SPRUCE_TAIGA_HILLS.location().equals(event.getName()) || Biomes.GIANT_SPRUCE_TAIGA.location().equals(event.getName())) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.BANANA_SLUG.get(), UAMConfig.Common.INSTANCE.bananaSlugSpawnWeight.get(), 1, 1));
        }

        if (event.getCategory() == Biome.Category.SWAMP) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.PLATYPUS.get(), UAMConfig.Common.INSTANCE.platypusSpawnWeight.get(), 1, 1));
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(UAMEntities.PACMAN_FROG.get(), UAMConfig.Common.INSTANCE.pacmanFrogSpawnWeight.get(), 1, 2));
        }
    }

    @SubscribeEvent
    public static void harvest(BlockEvent.BreakEvent event) {
        if (event.getWorld() instanceof ServerWorld) {
            BlockState cropState = event.getState();
            if (cropState.getBlock() instanceof CropsBlock) {
                CropsBlock block = (CropsBlock) cropState.getBlock();
                if (block.isMaxAge(cropState)) {
                    ServerWorld world = (ServerWorld) event.getWorld();
                    BlockState farmlandState = world.getBlockState(event.getPos().below());
                    if (farmlandState.getBlock() instanceof RichFarmlandBlock) {
                        List<ItemStack> drops = Block.getDrops(cropState, world, event.getPos(), null);
                        List<ItemStack> uniqueDrops = new ArrayList<>();
                        List<Item> addedItems = new ArrayList<>();
                        drops.forEach(s -> {
                            if (!addedItems.contains(s.getItem())) {
                                addedItems.add(s.getItem());
                                uniqueDrops.add(s);
                            }
                        });
                        if (uniqueDrops.size() > 1) {
                            uniqueDrops.forEach(s -> {
                                if (!(s.getItem() instanceof BlockItem && ((BlockItem) s.getItem()).getBlock() instanceof CropsBlock)) {
                                    ItemEntity entity = new ItemEntity(world, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5f, event.getPos().getZ() + 0.5f, s);
                                    world.addFreshEntity(entity);
                                }
                            });
                        }
                        else {
                            ItemEntity entity = new ItemEntity(world, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5f, event.getPos().getZ() + 0.5f, uniqueDrops.get(0));
                            world.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof WolfEntity) {
            ((WolfEntity) entity).targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((MobEntity) entity, MuskOxEntity.class, true));
        }
    }
}

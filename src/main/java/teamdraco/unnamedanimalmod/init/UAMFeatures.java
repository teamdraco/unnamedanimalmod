package teamdraco.unnamedanimalmod.init;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.common.worldgen.trees.mangrove.MangroveLandTreeFeature;
import teamdraco.unnamedanimalmod.common.worldgen.trees.mangrove.MangroveWaterTreeFeature;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.world.gen.feature.Feature.DISK;
import static net.minecraft.world.gen.feature.NoFeatureConfig.INSTANCE;

public class UAMFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, UnnamedAnimalMod.MOD_ID);

    public static final Feature<NoFeatureConfig> WATER_TREE = new MangroveWaterTreeFeature();
    public static final Feature<NoFeatureConfig> LAND_TREE = new MangroveLandTreeFeature();
    public static final RegistryObject<Feature<NoFeatureConfig>> WATER_TREE_FEATURE = REGISTRY.register("submerged_mangrove_tree", ()-> WATER_TREE);
    public static final RegistryObject<Feature<NoFeatureConfig>> LAND_TREE_FEATURE = REGISTRY.register("mangrove_tree", ()-> LAND_TREE);

    static {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, UnnamedAnimalMod.MOD_ID + ":" + "mangrove_tree_feature", LAND_TREE.configured(INSTANCE).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.5F, 1))));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, UnnamedAnimalMod.MOD_ID + ":" + "disk_mud", DISK.configured(new SphereReplaceConfig(UAMBlocks.MUD.defaultBlockState(), FeatureSpread.of(3, 1), 2, ImmutableList.of(Blocks.DIRT.defaultBlockState(), UAMBlocks.MUD.defaultBlockState()))).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, UnnamedAnimalMod.MOD_ID + ":" + "submerged_mangrove_tree_feature", WATER_TREE.configured(INSTANCE).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.5F, 1))));
    }
}
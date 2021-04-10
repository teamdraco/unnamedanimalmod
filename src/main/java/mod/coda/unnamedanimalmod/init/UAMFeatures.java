package mod.coda.unnamedanimalmod.init;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.worldgen.trees.mangrove.MangroveLandTreeFeature;
import mod.coda.unnamedanimalmod.worldgen.trees.mangrove.MangroveWaterTreeFeature;
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
import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236559_b_;

public class UAMFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, UnnamedAnimalMod.MOD_ID);

    public static final Feature<NoFeatureConfig> WATER_TREE = new MangroveWaterTreeFeature();
    public static final Feature<NoFeatureConfig> LAND_TREE = new MangroveLandTreeFeature();
    public static final RegistryObject<Feature<NoFeatureConfig>> WATER_TREE_FEATURE = REGISTRY.register("submerged_mangrove_tree", ()-> WATER_TREE);
    public static final RegistryObject<Feature<NoFeatureConfig>> LAND_TREE_FEATURE = REGISTRY.register("mangrove_tree", ()-> LAND_TREE);

    static {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, UnnamedAnimalMod.MOD_ID + ":" + "mangrove_tree_feature", LAND_TREE.withConfiguration(field_236559_b_).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 0.5F, 1))));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, UnnamedAnimalMod.MOD_ID + ":" + "disk_mud", DISK.withConfiguration(new SphereReplaceConfig(UAMBlocks.MUD.getDefaultState(), FeatureSpread.func_242253_a(3, 1), 2, ImmutableList.of(Blocks.DIRT.getDefaultState(), UAMBlocks.MUD.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, UnnamedAnimalMod.MOD_ID + ":" + "submerged_mangrove_tree_feature", WATER_TREE.withConfiguration(field_236559_b_).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 0.5F, 1))));
    }
}
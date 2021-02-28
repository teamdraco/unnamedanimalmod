package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.worldgen.trees.mangrove.MangroveLandTreeFeature;
import mod.coda.unnamedanimalmod.worldgen.trees.mangrove.MangroveWaterTreeFeature;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMFeatures
{
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, UnnamedAnimalMod.MOD_ID);
    
    public static final RegistryObject<Feature<?>> WATER_TREE_FEATURE = REGISTRY.register("submerged_mangrove_tree", MangroveWaterTreeFeature::new);
    public static final RegistryObject<Feature<?>> LAND_TREE_FEATURE = REGISTRY.register("mangrove_tree", MangroveLandTreeFeature::new);
}
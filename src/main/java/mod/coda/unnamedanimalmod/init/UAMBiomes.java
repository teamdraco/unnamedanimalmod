package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UAMHelper;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static mod.coda.unnamedanimalmod.UnnamedAnimalMod.MOD_ID;

public class UAMBiomes
{
    
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, UnnamedAnimalMod.MOD_ID);
    
    // Dummy biomes to reserve the numeric ID safely for the json biomes to overwrite.
    // No static variable to hold as these dummy biomes should NOT be held and referenced elsewhere.
    static {
        mangroveForest("mangrove_forest", BiomeMaker::makeVoidBiome);
    }
    
    public static RegistryObject<Biome> mangroveForest(String name, Supplier<Biome> biome) {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(MOD_ID, name)), 1));
        return BIOMES.register(name, biome);
    }
    
    public static final DeferredRegister<SurfaceBuilder<?>> BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, UnnamedAnimalMod.MOD_ID);
    
    static
    {
        BUILDERS.register("mangrove", () -> new DefaultSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    }
}

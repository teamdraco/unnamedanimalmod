package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<Block> SALT_BLOCK = REGISTRY.register("salt_block", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.PINK).sound(SoundType.STONE).hardnessAndResistance(1.5f)));
}
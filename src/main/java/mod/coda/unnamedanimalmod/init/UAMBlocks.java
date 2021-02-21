package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.block.MangroveSaplingBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, UnnamedAnimalMod.MOD_ID);
    
    public static final RegistryObject<Block> SALT_BLOCK = REGISTRY.register("salt_block", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.PINK).sound(SoundType.STONE).hardnessAndResistance(1.5f)));
    public static final RegistryObject<Block> MANGROVE_SAPLING = REGISTRY.register("mangrove_sapling", () -> new MangroveSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
}
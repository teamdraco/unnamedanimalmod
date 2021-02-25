package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.block.MangroveSaplingBlock;
import mod.coda.unnamedanimalmod.block.StripableLogBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING;

public class UAMBlocks {
    
    public static AbstractBlock.Properties MANGROVE_PROPERTIES() //adjust these to your likings
    {
        return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F);
    }
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, UnnamedAnimalMod.MOD_ID);
    
    public static final RegistryObject<Block> SALT_BLOCK = REGISTRY.register("salt_block", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.PINK).sound(SoundType.STONE).hardnessAndResistance(1.5f)));
    public static final RegistryObject<Block> MANGROVE_SAPLING = REGISTRY.register("mangrove_sapling", () -> new MangroveSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    
    public static final RegistryObject<Block> STRIPPED_MANGROVE_LOG = REGISTRY.register("stripped_mangrove_log", () -> new RotatedPillarBlock(MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_LOG = REGISTRY.register("mangrove_log", () -> new StripableLogBlock(MANGROVE_PROPERTIES(), STRIPPED_MANGROVE_LOG.get()));
    public static final RegistryObject<Block> STRIPPED_MANGROVE_WOOD = REGISTRY.register("stripped_mangrove_wood", () -> new RotatedPillarBlock(MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_WOOD = REGISTRY.register("mangrove_wood", () -> new StripableLogBlock(MANGROVE_PROPERTIES(), STRIPPED_MANGROVE_WOOD.get()));
    
    public static final RegistryObject<Block> MANGROVE_PLANKS = REGISTRY.register("mangrove_planks", () -> new Block(MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_PLANKS_SLAB = REGISTRY.register("mangrove_planks_slab", () -> new SlabBlock(MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_PLANKS_STAIRS = REGISTRY.register("mangrove_planks_stairs", () -> new StairsBlock(MANGROVE_PLANKS.get().getDefaultState(), MANGROVE_PROPERTIES()));
    
    public static final RegistryObject<Block> MANGROVE_DOOR = REGISTRY.register("mangrove_door", () -> new DoorBlock(MANGROVE_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> MANGROVE_TRAPDOOR = REGISTRY.register("mangrove_trapdoor", () -> new TrapDoorBlock(MANGROVE_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> MANGROVE_PLANKS_BUTTON = REGISTRY.register("mangrove_planks_button", () -> new WoodButtonBlock(MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_PLANKS_PRESSURE_PLATE = REGISTRY.register("mangrove_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_PLANKS_FENCE = REGISTRY.register("mangrove_planks_fence", () -> new FenceBlock(MANGROVE_PROPERTIES()));
    public static final RegistryObject<Block> MANGROVE_PLANKS_FENCE_GATE = REGISTRY.register("mangrove_planks_fence_gate", () -> new FenceGateBlock(MANGROVE_PROPERTIES()));
    
}
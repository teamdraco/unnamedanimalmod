package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.block.MangroveSaplingBlock;
import mod.coda.unnamedanimalmod.block.RichFarmlandBlock;
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
    
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, UnnamedAnimalMod.MOD_ID);
    
    public static final RegistryObject<Block> SALT_BLOCK = REGISTRY.register("salt_block", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.PINK).sound(SoundType.STONE).hardnessAndResistance(1.5f)));
    public static final RegistryObject<Block> MANGROVE_SAPLING = REGISTRY.register("mangrove_sapling", () -> new MangroveSaplingBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    
    public static final RegistryObject<Block> STRIPPED_MANGROVE_LOG = REGISTRY.register("stripped_mangrove_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_LOG = REGISTRY.register("mangrove_log", () -> new StripableLogBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F), STRIPPED_MANGROVE_LOG.get()));
    public static final RegistryObject<Block> STRIPPED_MANGROVE_WOOD = REGISTRY.register("stripped_mangrove_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_WOOD = REGISTRY.register("mangrove_wood", () -> new StripableLogBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F), STRIPPED_MANGROVE_WOOD.get()));
    
    public static final RegistryObject<Block> MANGROVE_PLANKS = REGISTRY.register("mangrove_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_SLAB = REGISTRY.register("mangrove_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_STAIRS = REGISTRY.register("mangrove_stairs", () -> new StairsBlock(MANGROVE_PLANKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    
    public static final RegistryObject<Block> MANGROVE_DOOR = REGISTRY.register("mangrove_door", () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F).notSolid()));
    public static final RegistryObject<Block> MANGROVE_TRAPDOOR = REGISTRY.register("mangrove_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F).notSolid()));
    public static final RegistryObject<Block> MANGROVE_BUTTON = REGISTRY.register("mangrove_button", () -> new WoodButtonBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_PRESSURE_PLATE = REGISTRY.register("mangrove_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_FENCE = REGISTRY.register("mangrove_fence", () -> new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_FENCE_GATE = REGISTRY.register("mangrove_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F)));

    public static final Block MUD = new Block(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.BROWN).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.6F));
    public static final RegistryObject<Block> MUD_BLOCK = REGISTRY.register("mud_block", () -> MUD);
    public static final RegistryObject<Block> MUD_BRICKS = REGISTRY.register("mud_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5F)));
    public static final RegistryObject<Block> MUD_BRICKS_SLAB = REGISTRY.register("mud_bricks_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5F)));
    public static final RegistryObject<Block> MUD_BRICKS_STAIRS = REGISTRY.register("mud_bricks_stairs", () -> new StairsBlock(MUD_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5F)));

    public static final RegistryObject<Block> RICH_FARMLAND = REGISTRY.register("rich_farmland", () -> new RichFarmlandBlock(AbstractBlock.Properties.from(Blocks.FARMLAND)));
    
}
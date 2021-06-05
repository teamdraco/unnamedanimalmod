package teamdraco.unnamedanimalmod.init;

import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.unnamedanimalmod.common.block.*;

import static net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING;

public class UAMBlocks
{
    
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<Block> SALT_BLOCK = REGISTRY.register("salt_block", () -> new Block(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(1.5f)));
    public static final RegistryObject<Block> SALT = REGISTRY.register("salt", () -> new SaltPowderBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> MANGROVE_SAPLING = REGISTRY.register("mangrove_sapling", () -> new MangroveSaplingBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    
    public static final RegistryObject<Block> STRIPPED_MANGROVE_LOG = REGISTRY.register("stripped_mangrove_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_LOG = REGISTRY.register("mangrove_log", () -> new StripableLogBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F), STRIPPED_MANGROVE_LOG.get()));
    public static final RegistryObject<Block> STRIPPED_MANGROVE_WOOD = REGISTRY.register("stripped_mangrove_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_WOOD = REGISTRY.register("mangrove_wood", () -> new StripableLogBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F), STRIPPED_MANGROVE_WOOD.get()));
    
    public static final RegistryObject<Block> MANGROVE_PLANKS = REGISTRY.register("mangrove_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_SLAB = REGISTRY.register("mangrove_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_STAIRS = REGISTRY.register("mangrove_stairs", () -> new StairsBlock(() -> MANGROVE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_DOOR = REGISTRY.register("mangrove_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F).noOcclusion()));
    public static final RegistryObject<Block> MANGROVE_TRAPDOOR = REGISTRY.register("mangrove_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F).noOcclusion()));
    public static final RegistryObject<Block> MANGROVE_BUTTON = REGISTRY.register("mangrove_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_PRESSURE_PLATE = REGISTRY.register("mangrove_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_FENCE = REGISTRY.register("mangrove_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_FENCE_GATE = REGISTRY.register("mangrove_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));

    public static final Block MUD = new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.GRAVEL).harvestTool(ToolType.SHOVEL).strength(0.6F));
    public static final RegistryObject<Block> MUD_BLOCK = REGISTRY.register("mud_block", () -> MUD);
    public static final RegistryObject<Block> MUD_BRICKS = REGISTRY.register("mud_bricks", () -> new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> CHISELED_MUD_BRICKS = REGISTRY.register("chiseled_mud_bricks", () -> new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> MUD_BRICK_SLAB = REGISTRY.register("mud_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.STONE).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> MUD_BRICK_STAIRS = REGISTRY.register("mud_brick_stairs", () -> new StairsBlock(() -> MUD_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> DRIED_MUD_BLOCK = REGISTRY.register("dried_mud_block", () -> new DriedMudBlock(MUD_BLOCK.get().defaultBlockState(), AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.SHOVEL).strength(0.9F)));
    public static final RegistryObject<Block> DRIED_MUD_BRICKS = REGISTRY.register("dried_mud_bricks", () -> new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> CHISELED_DRIED_MUD_BRICKS = REGISTRY.register("chiseled_dried_mud_bricks", () -> new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> DRIED_MUD_BRICK_SLAB = REGISTRY.register("dried_mud_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.GRAVEL).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> DRIED_MUD_BRICK_STAIRS = REGISTRY.register("dried_mud_brick_stairs", () -> new StairsBlock(() -> DRIED_MUD_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));

    public static final RegistryObject<Block> RICH_FARMLAND = REGISTRY.register("rich_farmland", () -> new RichFarmlandBlock(AbstractBlock.Properties.copy(Blocks.FARMLAND)));

    public static final RegistryObject<Block> MANGROVE_LEAVES = REGISTRY.register("mangrove_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_LEAVES)));
    public static final RegistryObject<Block> FLOWERING_MANGROVE_LEAVES = REGISTRY.register("flowering_mangrove_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_LEAVES)));
    public static final RegistryObject<Block> MANGROVE_FRUIT = REGISTRY.register("mangrove_fruit", () -> new MangroveFruitBlock(AbstractBlock.Properties.copy(Blocks.WHEAT)));
}
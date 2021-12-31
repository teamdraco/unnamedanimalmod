package teamdraco.unnamedanimalmod.init;

import net.minecraft.world.level.block.state.BlockBehaviour;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.unnamedanimalmod.common.UAMWoodType;
import teamdraco.unnamedanimalmod.common.block.*;

import static net.minecraft.world.level.block.PressurePlateBlock.Sensitivity.EVERYTHING;

public class UAMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<Block> SALT_BLOCK = BLOCKS.register("salt_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(1.5f)));
    public static final RegistryObject<Block> SALT = BLOCKS.register("salt", () -> new SaltPowderBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak()));
    public static final RegistryObject<Block> MANGROVE_SAPLING = BLOCKS.register("mangrove_sapling", () -> new MangroveSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    
    public static final RegistryObject<Block> STRIPPED_MANGROVE_LOG = BLOCKS.register("stripped_mangrove_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_LOG = BLOCKS.register("mangrove_log", () -> new StripableLogBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F), STRIPPED_MANGROVE_LOG.get()));
    public static final RegistryObject<Block> STRIPPED_MANGROVE_WOOD = BLOCKS.register("stripped_mangrove_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_WOOD = BLOCKS.register("mangrove_wood", () -> new StripableLogBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F), STRIPPED_MANGROVE_WOOD.get()));
    
    public static final RegistryObject<Block> MANGROVE_PLANKS = BLOCKS.register("mangrove_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_SLAB = BLOCKS.register("mangrove_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_STAIRS = BLOCKS.register("mangrove_stairs", () -> new StairBlock(() -> MANGROVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_DOOR = BLOCKS.register("mangrove_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F).noOcclusion()));
    public static final RegistryObject<Block> MANGROVE_TRAPDOOR = BLOCKS.register("mangrove_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F).noOcclusion()));
    public static final RegistryObject<Block> MANGROVE_BUTTON = BLOCKS.register("mangrove_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_PRESSURE_PLATE = BLOCKS.register("mangrove_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_FENCE = BLOCKS.register("mangrove_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));
    public static final RegistryObject<Block> MANGROVE_FENCE_GATE = BLOCKS.register("mangrove_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).sound(SoundType.WOOD).harvestTool(ToolType.AXE).strength(1.75F, 4.0F)));

    public static final Block MUD = new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.GRAVEL).harvestTool(ToolType.SHOVEL).strength(0.6F));
    public static final RegistryObject<Block> MUD_BLOCK = BLOCKS.register("mud_block", () -> MUD);
    public static final RegistryObject<Block> MUD_BRICKS = BLOCKS.register("mud_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> CHISELED_MUD_BRICKS = BLOCKS.register("chiseled_mud_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> MUD_BRICK_SLAB = BLOCKS.register("mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.STONE).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> MUD_BRICK_STAIRS = BLOCKS.register("mud_brick_stairs", () -> new StairBlock(() -> MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> DRIED_MUD_BLOCK = BLOCKS.register("dried_mud_block", () -> new DriedMudBlock(MUD_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.SHOVEL).strength(0.9F)));
    public static final RegistryObject<Block> DRIED_MUD_BRICKS = BLOCKS.register("dried_mud_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> CHISELED_DRIED_MUD_BRICKS = BLOCKS.register("chiseled_dried_mud_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> DRIED_MUD_BRICK_SLAB = BLOCKS.register("dried_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.GRAVEL).harvestTool(ToolType.PICKAXE).strength(2.5F)));
    public static final RegistryObject<Block> DRIED_MUD_BRICK_STAIRS = BLOCKS.register("dried_mud_brick_stairs", () -> new StairBlock(() -> DRIED_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BROWN).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).strength(2.5F)));

    public static final RegistryObject<Block> RICH_FARMLAND = BLOCKS.register("rich_farmland", () -> new RichFarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND)));

    public static final RegistryObject<Block> MANGROVE_LEAVES = BLOCKS.register("mangrove_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LEAVES)));
    public static final RegistryObject<Block> FLOWERING_MANGROVE_LEAVES = BLOCKS.register("flowering_mangrove_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LEAVES)));
    public static final RegistryObject<Block> MANGROVE_FRUIT = BLOCKS.register("mangrove_fruit", () -> new MangroveFruitBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    // Compat
}

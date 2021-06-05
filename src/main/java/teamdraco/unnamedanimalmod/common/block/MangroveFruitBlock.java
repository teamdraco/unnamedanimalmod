package teamdraco.unnamedanimalmod.common.block;

import teamdraco.unnamedanimalmod.init.UAMBlocks;
import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class MangroveFruitBlock extends CropsBlock implements IGrowable {
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public MangroveFruitBlock(Properties builder) {
        super(builder);
    }

    protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() == UAMBlocks.FLOWERING_MANGROVE_LEAVES.get() || state.getBlock() == UAMBlocks.MANGROVE_LEAVES.get();
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    protected IItemProvider getBaseSeedId() {
        return UAMItems.MANGROVE_FRUIT.get();
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.getValue(this.getAgeProperty())];
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }
}

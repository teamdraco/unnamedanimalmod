package mod.coda.unnamedanimalmod.block;

import mod.coda.unnamedanimalmod.init.UAMBlocks;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class MangroveFruitBlock extends CropsBlock implements IGrowable {
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 9.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public MangroveFruitBlock(Properties builder) {
        super(builder);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() == UAMBlocks.FLOWERING_MANGROVE_LEAVES.get() || state.getBlock() == UAMBlocks.MANGROVE_LEAVES.get();
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.up();
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    protected IItemProvider getSeedsItem() {
        return UAMItems.MANGROVE_FRUIT.get();
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(this.getAgeProperty())];
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }
}

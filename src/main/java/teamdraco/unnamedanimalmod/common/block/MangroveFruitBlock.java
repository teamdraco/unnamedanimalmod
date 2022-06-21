package teamdraco.unnamedanimalmod.common.block;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import teamdraco.unnamedanimalmod.init.UAMBlocks;
import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.world.level.block.*;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.IWorld;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MangroveFruitBlock extends CropBlock implements IPlantable {
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

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.getBlock() == UAMBlocks.FLOWERING_MANGROVE_LEAVES.get() || state.getBlock() == UAMBlocks.MANGROVE_LEAVES.get();
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    protected ItemLike getBaseSeedId() {
        return (ItemLike) UAMItems.MANGROVE_FRUIT.get();
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(this.getAgeProperty())];
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }
}

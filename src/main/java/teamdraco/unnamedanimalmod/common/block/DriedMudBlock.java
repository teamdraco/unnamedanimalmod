package teamdraco.unnamedanimalmod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DriedMudBlock extends Block {
    private final BlockState solidifiedState;

    public DriedMudBlock(BlockState solidifiedState, Properties properties) {
        super(properties);
        this.solidifiedState = solidifiedState;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = iblockreader.getBlockState(blockpos);
        return shouldSolidify(iblockreader, blockpos, blockstate) ? this.solidifiedState : super.getStateForPlacement(context);
    }

    private static boolean shouldSolidify(BlockGetter reader, BlockPos pos, BlockState state) {
        return causesSolidify(state) || isTouchingLiquid(reader, pos);
    }

    private static boolean isTouchingLiquid(BlockGetter reader, BlockPos pos) {
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutable = pos.mutable();

        for(Direction direction : Direction.values()) {
            BlockState blockstate = reader.getBlockState(blockpos$mutable);
            if (direction != Direction.DOWN || causesSolidify(blockstate)) {
                blockpos$mutable.setWithOffset(pos, direction);
                blockstate = reader.getBlockState(blockpos$mutable);
                if (causesSolidify(blockstate) && !blockstate.isFaceSturdy(reader, pos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    private static boolean causesSolidify(BlockState state) {
        return state.getFluidState().is(FluidTags.WATER);
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return isTouchingLiquid(worldIn, currentPos) ? this.solidifiedState : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}

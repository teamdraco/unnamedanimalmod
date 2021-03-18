package mod.coda.unnamedanimalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class DriedMudBlock extends Block {
    private final BlockState solidifiedState;

    public DriedMudBlock(BlockState solidifiedState, Properties properties) {
        super(properties);
        this.solidifiedState = solidifiedState;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = iblockreader.getBlockState(blockpos);
        return shouldSolidify(iblockreader, blockpos, blockstate) ? this.solidifiedState : super.getStateForPlacement(context);
    }

    private static boolean shouldSolidify(IBlockReader reader, BlockPos pos, BlockState state) {
        return causesSolidify(state) || isTouchingLiquid(reader, pos);
    }

    private static boolean isTouchingLiquid(IBlockReader reader, BlockPos pos) {
        boolean flag = false;
        BlockPos.Mutable blockpos$mutable = pos.toMutable();

        for(Direction direction : Direction.values()) {
            BlockState blockstate = reader.getBlockState(blockpos$mutable);
            if (direction != Direction.DOWN || causesSolidify(blockstate)) {
                blockpos$mutable.setAndMove(pos, direction);
                blockstate = reader.getBlockState(blockpos$mutable);
                if (causesSolidify(blockstate) && !blockstate.isSolidSide(reader, pos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    private static boolean causesSolidify(BlockState state) {
        return state.getFluidState().isTagged(FluidTags.WATER);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return isTouchingLiquid(worldIn, currentPos) ? this.solidifiedState : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}

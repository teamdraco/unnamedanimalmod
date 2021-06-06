package teamdraco.unnamedanimalmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class StripableLogBlock extends RotatedPillarBlock {
    public final Block stripped;

    public StripableLogBlock(Properties properties, Block stripped) {
        super(properties);
        this.stripped = stripped;
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        return stripped.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
    }
}

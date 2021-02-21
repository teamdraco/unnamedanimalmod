package mod.coda.unnamedanimalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.BirchTree;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MangroveSaplingBlock extends SaplingBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    
    public static final Tree waterTree = new BirchTree();
    public static final Tree landTree = new OakTree();
    
    public MangroveSaplingBlock(Properties properties)
    {
        super(null, properties);
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(STAGE);
    }
    
    @Override
    public void placeTree(ServerWorld world, BlockPos pos, BlockState state, Random rand)
    {
        if (state.get(STAGE) == 0)
        {
            world.setBlockState(pos, state.func_235896_a_(STAGE), 4);
        }
        else
        {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos))
            {
                return;
            }
            Tree tree;
            if (state.get(WATERLOGGED))
            {
                tree = waterTree;
            }
            else
            {
                tree = landTree;
            }
            tree.attemptGrowTree(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand);
        }
    }
}

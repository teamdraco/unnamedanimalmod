package mod.coda.unnamedanimalmod.worldgen.trees.mangrove;

import com.ibm.icu.impl.Pair;
import mod.coda.unnamedanimalmod.block.MangroveSaplingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class MangroveTreeHelper
{
    //NOTE all random values below have 1 added to them when randomizing, the values determine the maximum possible output, not number of outputs
    //region land
    //trunk placement
    public static int minimumLandTrunkHeight = 7; //the bare minimum height a trunk can be
    public static int landTrunkHeightExtra = 2; //maximum possible randomized increase in height
    public static int minimumSideLandTrunkHeight = 1; //the bare minimum height a trunk can be
    public static int sideLandTrunkHeightExtra = 2; //maximum possible randomized increase in height
    //endregion
    
    //region water
    //trunk core placement - lowest point of the trunk
    public static int minimumWaterTrunkOffset = 2; //the absolute minimum height difference between the sapling the core, important when planted in small bodies of water
    public static int waterTrunkOffsetExtra = 1; //maximum possible randomized extra difference
    
    //full trunk placement, goes all the way up to the crown
    public static int minimumWaterTrunkHeight = 7; //the bare minimum height a trunk can be, should probably be higher than maximumUpwardsRootOffset
    public static int waterTrunkHeightExtra = 2; //maximum possible randomized increase in height
    
    //root placement
    public static int maximumUpwardsWaterRootOffset = 2; //maximum upwards offset of the start of the root
    public static int minimumWaterRootCoreOffset = 2; //minimum distance between the trunk core and the root core
    public static int waterRootCoreOffsetExtra = 1; //maximum possible randomized increase in distance
    //endregion
    
    //treetop placement
    public static int maximumDownwardsBranchOffset = 2; //maximum downwards offset of the start of the branch
    public static int minimumBranchCoreOffset = 3; //minimum distance between the trunk top and the branch core
    public static int branchCoreOffsetExtra = 1; //maximum possible randomized increase in distance
    public static int minimumBranchHeight = 2; //the bare minimum height a branch can be
    public static int branchHeightExtra = 2; //maximum possible randomized increase in height
    
    //leaves placement
    public static int leavesHeight = 4; //the total height of each individual leaves blob
    public static int leavesHeightExtra = 1; //yknow the drill, + random value
    public static int leavesStartDownwardsOffsetExtra = 2; //leaves don't always start atop the brannch, up to x blocks down
    public static int leavesSize = 2; //horizontal size of the blob
    public static int leavesSizeExtra = 1; //once again, randomized extra leaves size;
    public static int leavesShrinkStart = 2; //after how many blob layers do blob layers start to shrink. Until then they grow.
    public static int minimumVineHeight = 7; //the minimum possible height of vines
    public static int vineHeightExtra = 2; //I don't think I need to even write anything here
    
    public static void fill(ISeedReader reader, ArrayList<Pair<BlockPos, BlockState>> filler)
    {
        for (Pair<BlockPos, BlockState> pair : filler)
        {
            reader.setBlockState(pair.first, pair.second, 3);
        }
    }
    
    public static void fillLeaves(ISeedReader reader, Random rand, ArrayList<Pair<BlockPos, BlockState>> filler)
    {
        ArrayList<Pair<BlockPos, BlockState>> vineFiller = (ArrayList<Pair<BlockPos, BlockState>>) filler.stream().filter(p -> p.second.getBlock() instanceof VineBlock).collect(Collectors.toList());
        ArrayList<Pair<BlockPos, BlockState>> leavesFilter = (ArrayList<Pair<BlockPos, BlockState>>) filler.stream().filter(p -> p.second.getBlock() instanceof LeavesBlock).collect(Collectors.toList());
        for (Pair<BlockPos, BlockState> pair : leavesFilter)
        {
            if (canPlace(reader, pair.first))
            {
                reader.setBlockState(pair.first, pair.second, 3);
            }
        }
        for (Pair<BlockPos, BlockState> pair : vineFiller)
        {
            if (canPlace(reader, pair.first))
            {
                int vinesLength = minimumVineHeight + rand.nextInt(vineHeightExtra+1);
                for (int i =0; i < vinesLength; i++)
                {
                    BlockPos vinePos = pair.first.down(i);
                    if (canPlace(reader, vinePos))
                    {
                        reader.setBlockState(vinePos, pair.second, 3);
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
    }
    
    public static void makeLeafBlob(ArrayList<Pair<BlockPos, BlockState>> filler, Random rand, BlockPos pos, int branchHeight)
    {
        int randomOffset = rand.nextInt(leavesStartDownwardsOffsetExtra + 1);
        int startingLeavesOffset = branchHeight - randomOffset;
        int finalLeavesHeight = leavesHeight + rand.nextInt(leavesHeightExtra + 1);
    
        int size = leavesSize - 1 + rand.nextInt(leavesSizeExtra);
        for (int i = 0; i < finalLeavesHeight; i++)
        {
            int y = startingLeavesOffset + i;
            BlockPos blobSliceCenter = pos.up(y);
            if (i < leavesShrinkStart)
            {
                size++;
            }
            else
            {
                size--;
            }
            makeLeafSlice(filler, rand, blobSliceCenter, size, i < leavesShrinkStart);
        }
    }
    
    public static void makeLeafSlice(ArrayList<Pair<BlockPos, BlockState>> filler, Random rand, BlockPos pos, int leavesSize, boolean vines)
    {
        for (int x = -leavesSize; x <= leavesSize; x++)
        {
            for (int z = -leavesSize; z <= leavesSize; z++)
            {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize)
                {
                    continue;
                }
                BlockPos leavesPos = new BlockPos(pos).add(x, 0, z);
                filler.add(Pair.of(leavesPos, Blocks.JUNGLE_LEAVES.getDefaultState()));
                if (vines)
                {
                    if (Math.abs(x) == leavesSize || Math.abs(z) == leavesSize)
                    {
                        if (rand.nextFloat() < 0.2f)
                        {
                            Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
                            for (Direction direction : directions)
                            {
                                filler.add(Pair.of(leavesPos.offset(direction), Blocks.VINE.getDefaultState().with(VineBlock.FACING_TO_PROPERTY_MAP.get(direction.getOpposite()), true)));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static boolean canPlace(ISeedReader reader, BlockPos pos)
    {
        //todo implement some more proper 'is outside of world' check, mekanism has one
        if (pos.getY() > reader.getHeight() || pos.getY() < 0)
        {
            return false;
        }
        return (reader.getBlockState(pos).getBlock() instanceof MangroveSaplingBlock || reader.hasWater(pos) || reader.isAirBlock(pos) || reader.getBlockState(pos).getMaterial().isReplaceable());
    }
}

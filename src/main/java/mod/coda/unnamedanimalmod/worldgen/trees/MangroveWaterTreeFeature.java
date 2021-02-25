package mod.coda.unnamedanimalmod.worldgen.trees;

import com.ibm.icu.impl.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236558_a_;

public class MangroveWaterTreeFeature extends Feature<NoFeatureConfig>
{
    
    public MangroveWaterTreeFeature()
    {
        super(field_236558_a_);
    }
    
    //NOTE all random values below have 1 added to them when randomizing, the values determine the maximum possible output, not number of outputs
    
    //trunk core placement - lowest point of the trunk
    public static int minimumTrunkOffset = 3; //the absolute minimum height difference between the sapling the core, important when planted in small bodies of water
    public static int trunkOffsetExtra = 2; //maximum possible randomized extra difference
    
    //full trunk placement, goes all the way up to the crown
    public static int minimumTrunkHeight = 7; //the bare minimum height a trunk can be, should probably be higher than maximumUpwardsRootOffset
    public static int trunkHeightExtra = 5; //maximum possible randomized increase in height
    
    //root placement
    public static int maximumUpwardsRootOffset = 4; //maximum upwards offset of the start of the root
    public static int minimumRootCoreOffset = 2; //minimum distance between the trunk core and the root core
    public static int rootCoreOffsetExtra = 1; //maximum possible randomized increase in distance
    
    //treetop placement
    public static int maximumDownwardsBranchOffset = 4; //maximum downwards offset of the start of the branch
    public static int minimumBranchCoreOffset = 3; //minimum distance between the trunk top and the branch core
    public static int branchCoreOffsetExtra = 2; //maximum possible randomized increase in distance
    public static int minimumBranchHeight = 2; //the bare minimum height a branch can be
    public static int branchHeightExtra = 2; //maximum possible randomized increase in height
    
    public void fill(ISeedReader reader, ArrayList<Pair<BlockPos, BlockState>> filler)
    {
        for (Pair<BlockPos, BlockState> pair : filler)
        {
            reader.setBlockState(pair.first, pair.second, 3);
        }
    }
    
    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (!reader.hasWater(pos))
        {
            return false;
        }
        ArrayList<Pair<BlockPos, BlockState>> filler = new ArrayList<>();
        int waterSurface = 0;
    
        int worldOffset = reader.getHeight() - pos.getY();
        for (int i = 0; i <= worldOffset; i++) //figuring out where water surface position is
        {
            if (!reader.hasWater(pos.up(i)))
            {
                waterSurface = Math.max(minimumTrunkOffset, i);
                break;
            }
        }
        int trunkHeight = minimumTrunkHeight + rand.nextInt(trunkHeightExtra + 1);
        BlockPos trunkStart = pos.up(waterSurface + rand.nextInt(trunkOffsetExtra + 1));
        BlockPos trunkTop = trunkStart.up(trunkHeight);
        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = trunkStart.up(i);
            if (canPlace(reader, trunkPos))
            {
                filler.add(Pair.of(trunkPos, Blocks.RED_WOOL.getDefaultState()));
            }
            else
            {
                return false;
            }
        }
    
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        Direction lowestDirection = directions[rand.nextInt(directions.length)];
        for (Direction direction : directions) //root placement
        {
            int rootHeight = rand.nextInt(maximumUpwardsRootOffset + 1);
            if (direction.equals(lowestDirection))
            {
                rootHeight = 0;
            }
            int rootOffset = minimumRootCoreOffset + rand.nextInt(rootCoreOffsetExtra + 1);
            BlockPos rootStartPos = trunkStart.up(rootHeight).offset(direction, rootOffset);
            for (int i = 0; i < rootOffset; i++) //root connection placement
            {
                BlockPos rootConnectionPos = rootStartPos.offset(direction.getOpposite(), i);
                if (canPlace(reader, rootConnectionPos))
                {
                    filler.add(Pair.of(rootConnectionPos, Blocks.GREEN_WOOL.getDefaultState()));
                }
                else
                {
                    return false;
                }
            }
            int i = 0;
            do //root placement
            {
                BlockPos rootPos = rootStartPos.down(i);
                if (canPlace(reader, rootPos))
                {
                    filler.add(Pair.of(rootPos, Blocks.ORANGE_WOOL.getDefaultState()));
                }
                else
                {
                    break;
                }
                i++;
            } while (true);
        }
        Direction highestDirection = directions[rand.nextInt(directions.length)];
        boolean failed = false;
        for (Direction direction : directions) //tree top placement
        {
            int branchCoreOffset = rand.nextInt(maximumDownwardsBranchOffset + 1);
            if (direction.equals(highestDirection))
            {
                branchCoreOffset = 0;
            }
            else if (!failed && rand.nextFloat() < 0.25)
            {
                failed = true;
                continue;
            }
            int branchOffset = minimumBranchCoreOffset + rand.nextInt(branchCoreOffsetExtra + 1);
            BlockPos branchStartPos = trunkTop.down(branchCoreOffset).offset(direction, branchOffset);
            for (int i = 0; i < branchOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.offset(direction.getOpposite(), i);
                if (canPlace(reader, branchConnectionPos))
                {
                    filler.add(Pair.of(branchConnectionPos, Blocks.CYAN_WOOL.getDefaultState()));
                }
                else
                {
                    return false;
                }
            }
            int branchHeight = minimumBranchHeight + rand.nextInt(branchHeightExtra + 1);
            for (int i = 0; i <= branchHeight; i++) //trunk placement
            {
                BlockPos branchPos = branchStartPos.up(i);
                if (canPlace(reader, branchPos))
                {
                    filler.add(Pair.of(branchPos, Blocks.PINK_WOOL.getDefaultState()));
                }
                else
                {
                    return false;
                }
            }
        }
        filler.add(Pair.of(pos, Blocks.WATER.getDefaultState()));
        fill(reader, filler);
        return true;
    }
    
    public static boolean canPlace(ISeedReader reader, BlockPos pos)
    {
        //todo implement some more proper 'is outside of world' check, mekanism has one
        if (pos.getY() > reader.getHeight() || pos.getY() < 0)
        {
            return false;
        }
        return (reader.hasWater(pos) || reader.isAirBlock(pos) || reader.getBlockState(pos).getMaterial().isReplaceable());
    }
}
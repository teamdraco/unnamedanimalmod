package mod.coda.unnamedanimalmod.worldgen.trees.mangrove;

import com.ibm.icu.impl.Pair;
import mod.coda.unnamedanimalmod.init.UAMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236558_a_;

public class MangroveWaterTreeFeature extends Feature<NoFeatureConfig> {

    public MangroveWaterTreeFeature()
    {
        super(field_236558_a_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (!reader.hasWater(pos))
        {
            return false;
        }
        BlockState defaultLog = UAMBlocks.MANGROVE_LOG.get().getDefaultState();
        ArrayList<Entry> filler = new ArrayList<>();
        ArrayList<Entry> leavesFiller = new ArrayList<>();
        int waterSurface = 0;

        int worldOffset = reader.getHeight() - pos.getY();
        for (int i = 0; i <= worldOffset; i++) //figuring out where water surface position is
        {
            if (!reader.hasWater(pos.up(i)))
            {
                waterSurface = Math.max(MangroveTreeHelper.minimumWaterTrunkOffset, i);
                break;
            }
        }
        int trunkHeight = MangroveTreeHelper.minimumWaterTrunkHeight + rand.nextInt(MangroveTreeHelper.waterTrunkHeightExtra + 1);
        BlockPos trunkStart = pos.up(waterSurface + rand.nextInt(MangroveTreeHelper.waterTrunkOffsetExtra + 1));
        BlockPos trunkTop = trunkStart.up(trunkHeight);
        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = trunkStart.up(i);
            if (MangroveTreeHelper.canPlace(reader, trunkPos))
            {
                filler.add(new Entry(trunkPos, defaultLog));
            }
            else
            {
                return false;
            }
        }
        MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, trunkStart, trunkHeight);

        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        Direction lowestDirection = directions[rand.nextInt(directions.length)];
        for (Direction direction : directions) //root placement
        {
            int rootHeight = rand.nextInt(MangroveTreeHelper.maximumUpwardsWaterRootOffset + 1);
            if (direction.equals(lowestDirection))
            {
                rootHeight = 0;
            }
            int rootOffset = MangroveTreeHelper.minimumWaterRootCoreOffset + rand.nextInt(MangroveTreeHelper.waterRootCoreOffsetExtra + 1);
            BlockPos rootStartPos = trunkStart.up(rootHeight).offset(direction, rootOffset);
            for (int i = 0; i < rootOffset; i++) //root connection placement
            {
                BlockPos rootConnectionPos = rootStartPos.offset(direction.getOpposite(), i);
                if (MangroveTreeHelper.canPlace(reader, rootConnectionPos))
                {
                    filler.add(new Entry(rootConnectionPos, defaultLog.with(RotatedPillarBlock.AXIS, direction.getAxis())));
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
                if (MangroveTreeHelper.canPlace(reader, rootPos))
                {
                    filler.add(new Entry(rootPos, defaultLog));
                }
                else
                {
                    break;
                }
                i++;
            }
            while (true);
        }
        Direction highestDirection = directions[rand.nextInt(directions.length)];
        boolean failed = false;
        for (Direction direction : directions) //tree top placement
        {
            int branchCoreOffset = rand.nextInt(MangroveTreeHelper.maximumDownwardsBranchOffset + 1);
            if (direction.equals(highestDirection))
            {
                branchCoreOffset = 0;
            }
            else if (!failed && rand.nextFloat() < 0.25)
            {
                failed = true;
                continue;
            }
            int branchOffset = MangroveTreeHelper.minimumBranchCoreOffset + rand.nextInt(MangroveTreeHelper.branchCoreOffsetExtra + 1);
            BlockPos branchStartPos = trunkTop.down(branchCoreOffset).offset(direction, branchOffset);
            for (int i = 0; i < branchOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.offset(direction.getOpposite(), i);
                if (MangroveTreeHelper.canPlace(reader, branchConnectionPos))
                {
                    filler.add(new Entry(branchConnectionPos, defaultLog.with(RotatedPillarBlock.AXIS, direction.getAxis())));
                }
                else
                {
                    return false;
                }
            }
            int branchHeight = MangroveTreeHelper.minimumBranchHeight + rand.nextInt(MangroveTreeHelper.branchHeightExtra + 1);
            for (int i = 0; i <= branchHeight; i++) //trunk placement
            {
                BlockPos branchPos = branchStartPos.up(i);
                if (MangroveTreeHelper.canPlace(reader, branchPos))
                {
                    filler.add(new Entry(branchPos, defaultLog));
                }
                else
                {
                    return false;
                }
            }
            // LEAAAAVEESSSSSSSSSSS
            MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, branchStartPos, branchHeight); // yeah it's just one line
        }
        filler.add(new Entry(pos, Blocks.WATER.getDefaultState()));
        MangroveTreeHelper.fill(reader, filler);
        MangroveTreeHelper.fillLeaves(reader, rand, leavesFiller);
        return true;
    }
}
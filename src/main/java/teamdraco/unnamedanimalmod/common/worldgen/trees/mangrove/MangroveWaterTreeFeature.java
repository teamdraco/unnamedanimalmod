package teamdraco.unnamedanimalmod.common.worldgen.trees.mangrove;

import teamdraco.unnamedanimalmod.init.UAMBlocks;
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

import static net.minecraft.world.gen.feature.NoFeatureConfig.CODEC;

public class MangroveWaterTreeFeature extends Feature<NoFeatureConfig> {

    public MangroveWaterTreeFeature()
    {
        super(CODEC);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (!reader.isWaterAt(pos))
        {
            return false;
        }
        BlockState defaultLog = UAMBlocks.MANGROVE_LOG.get().defaultBlockState();
        ArrayList<Entry> filler = new ArrayList<>();
        ArrayList<Entry> leavesFiller = new ArrayList<>();
        int waterSurface = 0;

        int worldOffset = reader.getMaxBuildHeight() - pos.getY();
        for (int i = 0; i <= worldOffset; i++) //figuring out where water surface position is
        {
            if (!reader.isWaterAt(pos.above(i)))
            {
                waterSurface = Math.max(MangroveTreeHelper.minimumWaterTrunkOffset, i);
                break;
            }
        }
        int trunkHeight = MangroveTreeHelper.minimumWaterTrunkHeight + rand.nextInt(MangroveTreeHelper.waterTrunkHeightExtra + 1);
        BlockPos trunkStart = pos.above(waterSurface + rand.nextInt(MangroveTreeHelper.waterTrunkOffsetExtra + 1));
        BlockPos trunkTop = trunkStart.above(trunkHeight);
        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = trunkStart.above(i);
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
            BlockPos rootStartPos = trunkStart.above(rootHeight).relative(direction, rootOffset);
            for (int i = 0; i < rootOffset; i++) //root connection placement
            {
                BlockPos rootConnectionPos = rootStartPos.relative(direction.getOpposite(), i);
                if (MangroveTreeHelper.canPlace(reader, rootConnectionPos))
                {
                    filler.add(new Entry(rootConnectionPos, defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                }
                else
                {
                    return false;
                }
            }
            int i = 0;
            do //root placement
            {
                BlockPos rootPos = rootStartPos.below(i);
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
            BlockPos branchStartPos = trunkTop.below(branchCoreOffset).relative(direction, branchOffset);
            for (int i = 0; i < branchOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.relative(direction.getOpposite(), i);
                if (MangroveTreeHelper.canPlace(reader, branchConnectionPos))
                {
                    filler.add(new Entry(branchConnectionPos, defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                }
                else
                {
                    return false;
                }
            }
            int branchHeight = MangroveTreeHelper.minimumBranchHeight + rand.nextInt(MangroveTreeHelper.branchHeightExtra + 1);
            for (int i = 0; i <= branchHeight; i++) //trunk placement
            {
                BlockPos branchPos = branchStartPos.above(i);
                if (MangroveTreeHelper.canPlace(reader, branchPos))
                {
                    filler.add(new Entry(branchPos, defaultLog));
                }
                else
                {
                    return false;
                }
            }
            MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, branchStartPos, branchHeight);
        }
        filler.add(new Entry(pos, Blocks.WATER.defaultBlockState()));
        MangroveTreeHelper.fill(reader, filler);
        MangroveTreeHelper.fillLeaves(reader, rand, leavesFiller);
        return true;
    }
}
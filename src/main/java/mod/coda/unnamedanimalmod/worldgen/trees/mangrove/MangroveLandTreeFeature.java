package mod.coda.unnamedanimalmod.worldgen.trees.mangrove;

import com.ibm.icu.impl.Pair;
import mod.coda.unnamedanimalmod.init.UAMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
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

public class MangroveLandTreeFeature extends Feature<NoFeatureConfig>
{
    public MangroveLandTreeFeature() {
        super(field_236558_a_);
    }
    
    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (reader.isAirBlock(pos.down()) || reader.getBlockState(pos.down()).getBlock() instanceof LeavesBlock) {
            return false;
        }
        if (reader.hasWater(pos) || reader.hasWater(pos.down())) {
            return false;
        }
        BlockState defaultLog = UAMBlocks.MANGROVE_LOG.get().getDefaultState();
        ArrayList<Pair<BlockPos, BlockState>> filler = new ArrayList<>();
        ArrayList<Pair<BlockPos, BlockState>> leavesFiller = new ArrayList<>();
    
        int trunkHeight = MangroveTreeHelper.minimumLandTrunkHeight + rand.nextInt(MangroveTreeHelper.landTrunkHeightExtra + 1);
        BlockPos trunkTop = pos.up(trunkHeight);
        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = pos.up(i);
            if (MangroveTreeHelper.canPlace(reader, trunkPos))
            {
                filler.add(Pair.of(trunkPos, defaultLog));
            }
            else
            {
                return false;
            }
        }
        MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, pos, trunkHeight);
    
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        for (Direction direction : directions) //side trunk placement
        {
            int sideTrunkHeight = MangroveTreeHelper.minimumSideLandTrunkHeight + rand.nextInt(MangroveTreeHelper.sideLandTrunkHeightExtra + 1);
            for (int i = 0; i <= sideTrunkHeight; i++) //trunk placement
            {
                BlockPos trunkPos = pos.offset(direction).up(i);
                if (MangroveTreeHelper.canPlace(reader, trunkPos))
                {
                    filler.add(Pair.of(trunkPos, defaultLog));
                }
                else
                {
                    return false;
                }
            }
            int i = 0;
            do
            {
                i++;
                BlockPos trunkPos = pos.offset(direction).down(i);
                if (MangroveTreeHelper.canPlace(reader, trunkPos))
                {
                    filler.add(Pair.of(trunkPos, defaultLog));
                }
                else
                {
                    break;
                }
                if (i > reader.getHeight())
                {
                    break;
                }
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
                    filler.add(Pair.of(branchConnectionPos, defaultLog.with(RotatedPillarBlock.AXIS, direction.getAxis())));
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
                    filler.add(Pair.of(branchPos, defaultLog));
                }
                else
                {
                    return false;
                }
            }
            // LEAAAAVEESSSSSSSSSSS
            MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, branchStartPos, branchHeight); // yeah it's just one line
        }
        MangroveTreeHelper.fill(reader, filler);
        MangroveTreeHelper.fillLeaves(reader, rand, leavesFiller);
        return true;
    }
}
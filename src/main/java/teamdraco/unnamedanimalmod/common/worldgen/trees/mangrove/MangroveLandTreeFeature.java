package teamdraco.unnamedanimalmod.common.worldgen.trees.mangrove;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import teamdraco.unnamedanimalmod.init.UAMBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.CODEC;

public class MangroveLandTreeFeature extends Feature<NoneFeatureConfiguration> {
    public MangroveLandTreeFeature() {
        super(CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel reader = ctx.level();
        BlockPos pos = ctx.origin();
        Random rand = ctx.random();

        if (reader.isEmptyBlock(pos.below()) || reader.getBlockState(pos.below()).getBlock() instanceof LeavesBlock) {
            return false;
        }
        if (reader.isWaterAt(pos) || reader.isWaterAt(pos.below())) {
            return false;
        }
        BlockState defaultLog = UAMBlocks.MANGROVE_LOG.get().defaultBlockState();
        ArrayList<Entry> filler = new ArrayList<>();
        ArrayList<Entry> leavesFiller = new ArrayList<>();

        int trunkHeight = MangroveTreeHelper.minimumLandTrunkHeight + rand.nextInt(MangroveTreeHelper.landTrunkHeightExtra + 1);
        BlockPos trunkTop = pos.above(trunkHeight);
        for (int i = 0; i <= trunkHeight; i++) {
            BlockPos trunkPos = pos.above(i);
            if (MangroveTreeHelper.canPlace(reader, trunkPos)) {
                filler.add(new Entry(trunkPos, defaultLog));
            }
            else {
                return false;
            }
        }
        MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, pos, trunkHeight);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

        for (Direction direction : directions) {
            int sideTrunkHeight = MangroveTreeHelper.minimumSideLandTrunkHeight + rand.nextInt(MangroveTreeHelper.sideLandTrunkHeightExtra + 1);
            for (int i = 0; i <= sideTrunkHeight; i++) {
                BlockPos trunkPos = pos.relative(direction).above(i);
                if (MangroveTreeHelper.canPlace(reader, trunkPos)) {
                    filler.add(new Entry(trunkPos, defaultLog));
                }
                else {
                    return false;
                }
            }
            int i = 0;
            do {
                i++;
                BlockPos trunkPos = pos.relative(direction).below(i);
                if (MangroveTreeHelper.canPlace(reader, trunkPos)) {
                    filler.add(new Entry(trunkPos, defaultLog));
                }
                else {
                    break;
                }
                if (i > reader.getMaxBuildHeight()) {
                    break;
                }
            }
            while (true);
        }
        Direction highestDirection = directions[rand.nextInt(directions.length)];
        boolean failed = false;
        for (Direction direction : directions) {
            int branchCoreOffset = rand.nextInt(MangroveTreeHelper.maximumDownwardsBranchOffset + 1);
            if (direction.equals(highestDirection)) {
                branchCoreOffset = 0;
            }
            else if (!failed && rand.nextFloat() < 0.25) {
                failed = true;
                continue;
            }
            int branchOffset = MangroveTreeHelper.minimumBranchCoreOffset + rand.nextInt(MangroveTreeHelper.branchCoreOffsetExtra + 1);
            BlockPos branchStartPos = trunkTop.below(branchCoreOffset).relative(direction, branchOffset);
            for (int i = 0; i < branchOffset; i++) {
                BlockPos branchConnectionPos = branchStartPos.relative(direction.getOpposite(), i);
                if (MangroveTreeHelper.canPlace(reader, branchConnectionPos)) {
                    filler.add(new Entry(branchConnectionPos, defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                }
                else {
                    return false;
                }
            }
            int branchHeight = MangroveTreeHelper.minimumBranchHeight + rand.nextInt(MangroveTreeHelper.branchHeightExtra + 1);
            for (int i = 0; i <= branchHeight; i++) {
                BlockPos branchPos = branchStartPos.above(i);
                if (MangroveTreeHelper.canPlace(reader, branchPos)) {
                    filler.add(new Entry(branchPos, defaultLog));
                }
                else {
                    return false;
                }
            }
            MangroveTreeHelper.makeLeafBlob(leavesFiller, rand, branchStartPos, branchHeight);
        }
        MangroveTreeHelper.fill(reader, filler);
        MangroveTreeHelper.fillLeaves(reader, rand, leavesFiller);
        return true;
    }
}

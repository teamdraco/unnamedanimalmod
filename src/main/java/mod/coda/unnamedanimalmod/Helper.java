package mod.coda.unnamedanimalmod;

import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mod.coda.unnamedanimalmod.UnnamedAnimalMod.MOD_ID;

public class Helper {

    public static ArrayList<Vector3d> blockOutlinePositions(World world, BlockPos pos) {
        ArrayList<Vector3d> arrayList = new ArrayList<>();
        double d0 = 0.5625D;
        Random random = world.rand;
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();
                arrayList.add(new Vector3d((double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3));
            }
        }
        return arrayList;
    }
}
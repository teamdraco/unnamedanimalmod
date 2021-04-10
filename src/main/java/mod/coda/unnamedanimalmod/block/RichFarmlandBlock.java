package mod.coda.unnamedanimalmod.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class RichFarmlandBlock extends FarmlandBlock
{
    public RichFarmlandBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
    }
    
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable)
    {
        return true;
//        net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.offset(facing));
//        if (net.minecraftforge.common.PlantType.PLAINS.equals(type))
//        {
//            return true;
//        }
//        return super.canSustainPlant(state, world, pos, facing, plantable);
    }
}
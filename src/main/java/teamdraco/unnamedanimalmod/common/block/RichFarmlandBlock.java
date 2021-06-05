package teamdraco.unnamedanimalmod.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import net.minecraft.block.AbstractBlock.Properties;

public class RichFarmlandBlock extends FarmlandBlock
{
    public RichFarmlandBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
    }
    
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable)
    {
        net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.relative(facing));
        if (PlantType.CROP.equals(type)) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }
}
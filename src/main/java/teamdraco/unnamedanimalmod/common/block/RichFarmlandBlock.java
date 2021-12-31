package teamdraco.unnamedanimalmod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class RichFarmlandBlock extends FarmBlock
{
    public RichFarmlandBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable)
    {
        net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.relative(facing));
        if (PlantType.CROP.equals(type)) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }
}

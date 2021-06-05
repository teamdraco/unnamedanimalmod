package teamdraco.unnamedanimalmod.common.item;

import teamdraco.unnamedanimalmod.UAMHelper;
import teamdraco.unnamedanimalmod.init.UAMBlocks;
import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;

import net.minecraft.item.Item.Properties;

public class MudBallItem extends Item
{
    public MudBallItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (world.getBlockState(pos).getBlock().equals(Blocks.FARMLAND)) {
            world.setBlockAndUpdate(pos, UAMBlocks.RICH_FARMLAND.get().defaultBlockState());
            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
            if (world.isClientSide) {
                addItemParticles(context.getPlayer(), pos);
            }
            else {
                world.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1, 1f);
                world.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1, 0.75f);
                world.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1, 0.5f);
            }
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }
    
    public void addItemParticles(PlayerEntity playerEntity, BlockPos pos) {
        World world = playerEntity.level;
        for (int i = 0; i < 5; i++) {
            ArrayList<Vector3d> particlePositions = UAMHelper.blockOutlinePositions(world, pos);
            particlePositions.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.DIRT.defaultBlockState()), p.x, p.y, p.z, 0, world.random.nextFloat() * 0.1f, 0));
        }
        for (int i = 0; i < 10; ++i) {
            double d0 = (double) (-playerEntity.level.random.nextFloat()) * 0.6D - 0.3D;
            Vector3d position = new Vector3d(((double) playerEntity.level.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            position = position.xRot(-playerEntity.xRot * ((float) Math.PI / 180F));
            position = position.yRot(-playerEntity.yRot * ((float) Math.PI / 180F));
            position = position.add(playerEntity.getX(), playerEntity.getEyeY(), playerEntity.getZ());
    
            Vector3d velocity = new Vector3d(((double) playerEntity.level.random.nextFloat() - 0.5D) * 0.2D, Math.random() * 0.1D + 0.1D, 0.0D);
            velocity = velocity.xRot(-playerEntity.xRot * ((float) Math.PI / 180F));
            velocity = velocity.yRot(-playerEntity.yRot * ((float) Math.PI / 180F));
            playerEntity.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, UAMItems.MUD_BALL.get().getDefaultInstance()), position.x, position.y, position.z, velocity.x, velocity.y + 0.05D, velocity.z);
        }
    }
}
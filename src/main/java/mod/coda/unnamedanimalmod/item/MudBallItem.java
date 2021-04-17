package mod.coda.unnamedanimalmod.item;

import mod.coda.unnamedanimalmod.UAMHelper;
import mod.coda.unnamedanimalmod.init.UAMBlocks;
import mod.coda.unnamedanimalmod.init.UAMItems;
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

public class MudBallItem extends Item
{
    public MudBallItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        if (world.getBlockState(pos).getBlock().equals(Blocks.FARMLAND)) {
            world.setBlockState(pos, UAMBlocks.RICH_FARMLAND.get().getDefaultState());
            if (!context.getPlayer().isCreative()) {
                context.getItem().shrink(1);
            }
            if (world.isRemote) {
                addItemParticles(context.getPlayer(), pos);
            }
            else {
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 1f);
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 0.75f);
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 0.5f);
            }
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
    
    public void addItemParticles(PlayerEntity playerEntity, BlockPos pos) {
        World world = playerEntity.world;
        for (int i = 0; i < 5; i++) {
            ArrayList<Vector3d> particlePositions = UAMHelper.blockOutlinePositions(world, pos);
            particlePositions.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState()), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
        }
        for (int i = 0; i < 10; ++i) {
            double d0 = (double) (-playerEntity.world.rand.nextFloat()) * 0.6D - 0.3D;
            Vector3d position = new Vector3d(((double) playerEntity.world.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            position = position.rotatePitch(-playerEntity.rotationPitch * ((float) Math.PI / 180F));
            position = position.rotateYaw(-playerEntity.rotationYaw * ((float) Math.PI / 180F));
            position = position.add(playerEntity.getPosX(), playerEntity.getPosYEye(), playerEntity.getPosZ());
    
            Vector3d velocity = new Vector3d(((double) playerEntity.world.rand.nextFloat() - 0.5D) * 0.2D, Math.random() * 0.1D + 0.1D, 0.0D);
            velocity = velocity.rotatePitch(-playerEntity.rotationPitch * ((float) Math.PI / 180F));
            velocity = velocity.rotateYaw(-playerEntity.rotationYaw * ((float) Math.PI / 180F));
            playerEntity.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, UAMItems.MUD_BALL.get().getDefaultInstance()), position.x, position.y, position.z, velocity.x, velocity.y + 0.05D, velocity.z);
        }
    }
}
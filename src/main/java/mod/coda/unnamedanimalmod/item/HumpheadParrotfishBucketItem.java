package mod.coda.unnamedanimalmod.item;

import mod.coda.unnamedanimalmod.init.UAMEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HumpheadParrotfishBucketItem extends BucketItem {
    public HumpheadParrotfishBucketItem(Item.Properties builder) {
        super(() -> Fluids.WATER, builder);
    }

    public void onLiquidPlaced(World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) {
            UAMEntities.HUMPHEAD_PARROTFISH.get().spawn((ServerWorld) world, stack, null, pos, SpawnReason.BUCKET, true, false);
        }
    }

    protected void playEmptySound(PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}

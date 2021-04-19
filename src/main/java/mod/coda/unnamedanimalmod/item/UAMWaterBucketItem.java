package mod.coda.unnamedanimalmod.item;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class UAMWaterBucketItem extends BucketItem {
    private final Supplier<EntityType<?>> entityType;

    public UAMWaterBucketItem(Supplier<EntityType<?>> entityType, Properties properties) {
        super(Fluids.WATER, properties);
        this.entityType = entityType;
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> UnnamedAnimalMod.CALLBACKS.add(() -> ItemModelsProperties.registerProperty(this, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "variant"), (stack, world, player) -> stack.hasTag() ? stack.getTag().getInt("Variant") : 0)));
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        else {
            ItemStack itemstack = context.getItem();
            BlockPos blockpos = context.getPos();
            Direction direction = context.getFace();
            BlockState blockstate = world.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            }
            else {
                blockpos1 = blockpos.offset(direction);
            }
            Supplier<EntityType<?>> entitytype = entityType;
            Entity entityType = entitytype.get().spawn((ServerWorld) world, itemstack, context.getPlayer(), blockpos1, SpawnReason.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (entityType != null) {
                if(!context.getPlayer().abilities.isCreativeMode) {
                    itemstack.shrink(1);
                    context.getPlayer().addItemStackToInventory(new ItemStack(Items.BUCKET));
                }

                playEmptySound(context.getPlayer(), world, blockpos);
            }
            return ActionResultType.SUCCESS;
        }
    }

    protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}
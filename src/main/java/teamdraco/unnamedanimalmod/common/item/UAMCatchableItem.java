package teamdraco.unnamedanimalmod.common.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.*;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class UAMCatchableItem extends BucketItem {
    private final Supplier<EntityType<?>> entityType;
    private final Item item1;

    public UAMCatchableItem(Supplier<EntityType<?>> entityType, Item item, Properties properties) {
        super(Fluids.EMPTY, properties);
        this.entityType = entityType;
        this.item1 = item;
    }

    public InteractionResult useOn(ItemUseContext context) {
        Level world = context.getLevel();
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            }
            else {
                blockpos1 = blockpos.relative(direction);
            }
            Supplier<EntityType<?>> entitytype = entityType;
            Item item = item1;
            Entity entityType = entitytype.get().spawn((ServerLevel) world, itemstack, context.getPlayer(), blockpos1, MobSpawnType.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (entityType != null) {
                if(!context.getPlayer().abilities.instabuild) {
                    itemstack.shrink(1);
                    context.getPlayer().addItem(new ItemStack(item));
                }

                playEmptySound(context.getPlayer(), world, blockpos);
            }
            return InteractionResult.SUCCESS;
        }
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}

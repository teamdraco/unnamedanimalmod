package teamdraco.unnamedanimalmod.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import teamdraco.unnamedanimalmod.common.entity.TomatoFrogEntity;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class FrogEggItem extends Item {
    private Supplier<EntityType<?>> entityTypeSupplier;

    public FrogEggItem(Supplier<EntityType<?>> entityType, Item.Properties builder) {
        super(builder);
        this.entityTypeSupplier = entityType;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World world = context.getLevel();
        ItemStack itemstack = player.getItemInHand(hand);
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState blockstate = world.getBlockState(pos);

        AnimalEntity frog = (AnimalEntity) entityTypeSupplier.get().create(world);
        if (!world.isClientSide && frog != null) {
            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, pos).isEmpty()) {
                blockpos1 = pos;
            } else {
                blockpos1 = pos.relative(direction);
            }

            frog.setAge(-24000);
            frog.setPos(blockpos1.getX() + 0.5F, blockpos1.getY(), blockpos1.getZ() + 0.5F);
            world.addFreshEntity(frog);
            world.playSound(player, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }



        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return super.useOn(context);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand p_77659_3_) {
        ItemStack itemstack = player.getItemInHand(p_77659_3_);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else if (!(world instanceof ServerWorld)) {
            return ActionResult.success(itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            if (!(world.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(itemstack);
            } else if (world.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemstack)) {
                AnimalEntity frog = (AnimalEntity) this.entityTypeSupplier.get().create(world);

                if (!world.isClientSide && frog != null) {
                    frog.setAge(-24000);
                    frog.setPos(blockpos.getX() + 0.5F, blockpos.getY(), blockpos.getZ() + 0.5F);
                    world.playSound(player, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                }
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.consume(itemstack);
            }
            else {
                return ActionResult.fail(itemstack);
            }
        }
    }

}
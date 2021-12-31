package teamdraco.unnamedanimalmod.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.ILiquidContainer;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

import net.minecraft.world.item.Item.Properties;

public class UAMWaterBucketItem extends BucketItem {
    private final Supplier<EntityType<?>> entityType;
    private final Supplier<Fluid> fluid;

    public UAMWaterBucketItem(Supplier<EntityType<?>> entityType, Supplier<Fluid> fluid, Properties properties) {
        super(fluid, properties);
        this.entityType = entityType;
        this.fluid = fluid;
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> UnnamedAnimalMod.CALLBACKS.add(() -> ItemModelsProperties.register(this, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "variant"), (stack, world, player) -> stack.hasTag() ? stack.getTag().getInt("Variant") : 0)));
    }

    @Override
    public ActionResult<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        BlockHitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else if (raytraceresult.getType() != HitResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();
            Direction direction = raytraceresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos1, direction, itemstack)) {
                BlockState blockstate = worldIn.getBlockState(blockpos);
                BlockPos blockpos2 = blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, blockpos, blockstate, fluid.get()) ? blockpos : blockpos1;
                this.emptyBucket(playerIn, worldIn, blockpos2, raytraceresult);
                if (worldIn instanceof ServerLevel) this.placeEntity((ServerLevel)worldIn, itemstack, blockpos2);
                if (playerIn instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) playerIn, blockpos2, itemstack);
                }

                playerIn.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.sidedSuccess(this.getEmptySuccessItem(itemstack, playerIn), worldIn.isClientSide());
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }

    private void placeEntity(ServerLevel worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(worldIn, stack, (Player)null, pos, MobSpawnType.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof AbstractFish) {
                ((AbstractFish)entity).setFromBucket(true);
            }
        }
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}

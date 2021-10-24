package teamdraco.unnamedanimalmod.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.ForgeEventFactory;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
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

import net.minecraft.item.Item.Properties;

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
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        BlockRayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();
            Direction direction = raytraceresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos1, direction, itemstack)) {
                BlockState blockstate = worldIn.getBlockState(blockpos);
                BlockPos blockpos2 = blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, blockpos, blockstate, fluid.get()) ? blockpos : blockpos1;
                this.emptyBucket(playerIn, worldIn, blockpos2, raytraceresult);
                if (worldIn instanceof ServerWorld) this.placeEntity((ServerWorld)worldIn, itemstack, blockpos2);
                if (playerIn instanceof ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos2, itemstack);
                }

                playerIn.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.sidedSuccess(this.getEmptySuccessItem(itemstack, playerIn), worldIn.isClientSide());
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }

    private void placeEntity(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(worldIn, stack, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof AbstractFishEntity) {
                ((AbstractFishEntity)entity).setFromBucket(true);
            }
        }
    }

    protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}
package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class PlatypusEntity extends AnimalEntity {

    public PlatypusEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
        this.moveControl = new PlatypusEntity.MoveHelperController(this);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.maxUpStep = 1;
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new PlatypusEntity.Navigator(this, level);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).getBlock() == Blocks.GRASS && worldIn.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 2.0D, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new PlatypusEntity.WanderGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new PlatypusEntity.GoToWaterGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.15);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.SPIDER_EYE;
    }

    public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).is(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return TurtleEggBlock.onSand(worldIn, pos) ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
        }
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() == Items.BUCKET && this.isAlive() && !this.isBaby()) {
            playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(UAMItems.PLATYPUS_BUCKET.get());
            this.setBucketData(itemstack1);
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
            }
            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.inventory.add(itemstack1)) {
                player.drop(itemstack1, false);
            }
            this.remove();
            return ActionResultType.SUCCESS;
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    protected void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.PLATYPUS_SPAWN_EGG.get());
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!source.isMagic() && source.getDirectEntity() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)source.getDirectEntity();
            if (!source.isExplosion()) {
                livingentity.addEffect(new EffectInstance(Effects.POISON, 100, 0));
            }
        }

        return super.hurt(source, amount);
    }


    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        this.spawnAtLocation(new ItemStack(UAMItems.PLATYPUS_EGG.get(), getRandom().nextInt(1) + 1));
        this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        ((AnimalEntity) p_241840_2_).resetLove();
        return null;
    }


    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getDeathSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }

    static class MoveHelperController extends MovementController {
        private final PlatypusEntity platypus;

        MoveHelperController(PlatypusEntity platypus) {
            super(platypus);
            this.platypus = platypus;
        }

        private void updateSpeed() {
            if (this.platypus.isInWater()) {
                this.platypus.setDeltaMovement(this.platypus.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

                if (this.platypus.isBaby()) {
                    this.platypus.setSpeed(Math.max(this.platypus.getSpeed() / 3.0F, 0.06F));
                }
            }
            else if (this.platypus.onGround) {
                this.platypus.setSpeed(Math.max(this.platypus.getSpeed() / 1.0F, 0.06F));
            }
        }

        public void tick() {
            this.updateSpeed();
            if (this.operation == MovementController.Action.MOVE_TO && !this.platypus.getNavigation().isDone()) {
                double d0 = this.wantedX - this.platypus.getX();
                double d1 = this.wantedY - this.platypus.getY();
                double d2 = this.wantedZ - this.platypus.getZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.platypus.yRot = this.rotlerp(this.platypus.yRot, f, 90.0F);
                this.platypus.yBodyRot = this.platypus.yRot;
                float f1 = (float)(this.speedModifier * this.platypus.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.platypus.setSpeed(MathHelper.lerp(0.125F, this.platypus.getSpeed(), f1));
                this.platypus.setDeltaMovement(this.platypus.getDeltaMovement().add(0.0D, (double)this.platypus.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.platypus.setSpeed(0.0F);
            }
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(PlatypusEntity platypus, World worldIn) {
            super(platypus, worldIn);
        }

        protected boolean canUpdatePath() {
            return true;
        }

        protected PathFinder createPathFinder(int p_179679_1_) {
            this.nodeEvaluator = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeEvaluator, p_179679_1_);
        }

        public boolean isStableDestination(BlockPos pos) {
            if (this.mob instanceof PlatypusEntity) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
            else return !this.level.getBlockState(pos.below()).isAir();
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final PlatypusEntity platypus;

        private WanderGoal(PlatypusEntity platypus, double speedIn, int chance) {
            super(platypus, speedIn, chance);
            this.platypus = platypus;
        }

        public boolean canUse() {
            return !this.mob.isInWater() && super.canUse();
        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final PlatypusEntity platypus;

        private GoToWaterGoal(PlatypusEntity platypus, double speedIn) {
            super(platypus, platypus.isBaby() ? 2.0D : speedIn, 24);
            this.platypus = platypus;
            this.verticalSearchStart = -1;
        }

        public boolean canContinueToUse() {
            return !this.platypus.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.platypus.level, this.blockPos);
        }

        public boolean canUse() {
            if (this.platypus.isBaby() && !this.platypus.isInWater()) {
                return super.canUse();
            } else {
                return !this.platypus.isInWater() && super.canUse();
            }
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 160 == 0;
        }

        protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos).is(Blocks.WATER);
        }
    }
}
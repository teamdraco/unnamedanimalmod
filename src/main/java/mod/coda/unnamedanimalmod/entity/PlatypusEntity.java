package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
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
        this.moveController = new PlatypusEntity.MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.stepHeight = 1;
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new PlatypusEntity.Navigator(this, world);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRASS && worldIn.getLightSubtracted(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 2.0D, 1) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new PlatypusEntity.WanderGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new PlatypusEntity.GoToWaterGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SPIDER_EYE;
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return TurtleEggBlock.hasProperHabitat(worldIn, pos) ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
        }
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() == Items.BUCKET && this.isAlive() && !this.isChild()) {
            playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(UAMItems.PLATYPUS_BUCKET.get());
            this.setBucketData(itemstack1);
            if (!this.world.isRemote) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
            }
            if (heldItem.isEmpty()) {
                player.setHeldItem(hand, itemstack1);
            } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
                player.dropItem(itemstack1, false);
            }
            this.remove();
            return ActionResultType.SUCCESS;
        }
        else {
            return super.func_230254_b_(player, hand);
        }
    }

    protected void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setDisplayName(this.getCustomName());
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.PLATYPUS_SPAWN_EGG.get());
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)source.getImmediateSource();
            if (!source.isExplosion()) {
                livingentity.addPotionEffect(new EffectInstance(Effects.POISON, 100, 0));
            }
        }

        return super.attackEntityFrom(source, amount);
    }


    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        this.entityDropItem(new ItemStack(UAMItems.PLATYPUS_EGG.get(), getRNG().nextInt(1) + 1));
        this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        ((AnimalEntity) p_241840_2_).resetInLove();
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
                this.platypus.setMotion(this.platypus.getMotion().add(0.0D, 0.005D, 0.0D));

                if (this.platypus.isChild()) {
                    this.platypus.setAIMoveSpeed(Math.max(this.platypus.getAIMoveSpeed() / 3.0F, 0.06F));
                }
            }
            else if (this.platypus.onGround) {
                this.platypus.setAIMoveSpeed(Math.max(this.platypus.getAIMoveSpeed() / 1.0F, 0.06F));
            }
        }

        public void tick() {
            this.updateSpeed();
            if (this.action == MovementController.Action.MOVE_TO && !this.platypus.getNavigator().noPath()) {
                double d0 = this.posX - this.platypus.getPosX();
                double d1 = this.posY - this.platypus.getPosY();
                double d2 = this.posZ - this.platypus.getPosZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.platypus.rotationYaw = this.limitAngle(this.platypus.rotationYaw, f, 90.0F);
                this.platypus.renderYawOffset = this.platypus.rotationYaw;
                float f1 = (float)(this.speed * this.platypus.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.platypus.setAIMoveSpeed(MathHelper.lerp(0.125F, this.platypus.getAIMoveSpeed(), f1));
                this.platypus.setMotion(this.platypus.getMotion().add(0.0D, (double)this.platypus.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.platypus.setAIMoveSpeed(0.0F);
            }
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(PlatypusEntity platypus, World worldIn) {
            super(platypus, worldIn);
        }

        protected boolean canNavigate() {
            return true;
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            if (this.entity instanceof PlatypusEntity) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
            else return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final PlatypusEntity platypus;

        private WanderGoal(PlatypusEntity platypus, double speedIn, int chance) {
            super(platypus, speedIn, chance);
            this.platypus = platypus;
        }

        public boolean shouldExecute() {
            return !this.creature.isInWater() && super.shouldExecute();
        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final PlatypusEntity platypus;

        private GoToWaterGoal(PlatypusEntity platypus, double speedIn) {
            super(platypus, platypus.isChild() ? 2.0D : speedIn, 24);
            this.platypus = platypus;
            this.field_203112_e = -1;
        }

        public boolean shouldContinueExecuting() {
            return !this.platypus.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.platypus.world, this.destinationBlock);
        }

        public boolean shouldExecute() {
            if (this.platypus.isChild() && !this.platypus.isInWater()) {
                return super.shouldExecute();
            } else {
                return !this.platypus.isInWater() && super.shouldExecute();
            }
        }

        public boolean shouldMove() {
            return this.timeoutCounter % 160 == 0;
        }

        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos).isIn(Blocks.WATER);
        }
    }
}
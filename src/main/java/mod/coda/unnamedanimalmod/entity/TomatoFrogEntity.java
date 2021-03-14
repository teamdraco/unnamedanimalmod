package mod.coda.unnamedanimalmod.entity;

import com.google.common.collect.Sets;
import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
import mod.coda.unnamedanimalmod.pathfinding.GroundAndSwimmerNavigator;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public class TomatoFrogEntity extends AnimalEntity {
    private Goal swimGoal;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;

    public TomatoFrogEntity(EntityType<? extends TomatoFrogEntity> type, World world) {
        super(type, world);
        this.moveController = new FrogMoveController(this);
        this.stepHeight = 1;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, swimGoal = new SwimGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new FrogMovementGoal(this));
        this.goalSelector.addGoal(3, new TomatoFrogEntity.PlayerTemptGoal(this, 1.0D, Items.SPIDER_EYE));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    @Override
    public void setGrowingAge(int age) {
        boolean wasChild = isChild();
        super.setGrowingAge(age);
        boolean isChild = isChild();
        if (!wasChild && isChild) {
            this.goalSelector.removeGoal(swimGoal);
            this.stepHeight = 1.0f;
        } else if (wasChild && !isChild) {
            this.goalSelector.addGoal(0, swimGoal);
            this.stepHeight = 0.0f;
        }
    }

    protected PathNavigator createNavigator(World world) {
        return new GroundAndSwimmerNavigator(this, world);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return this.isChild();
    }

    protected void updateAir(int air) {
        if (this.isChild()) {
            if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
                this.setAir(air - 1);
                if (this.getAir() == -20) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            } else {
                this.setAir(300);
            }
        }
    }

    public void baseTick() {
        int lvt_1_1_ = this.getAir();
        super.baseTick();
        this.updateAir(lvt_1_1_);
    }

    public void updateAITasks() {
        if (!isChild()) {
            if (this.currentMoveTypeDuration > 0) {
                --this.currentMoveTypeDuration;
            }

            if (this.onGround) {
                if (!this.wasOnGround) {
                    this.checkLandingDelay();
                }

                if (this.currentMoveTypeDuration == 0) {
                    LivingEntity livingentity = this.getAttackTarget();
                    if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
                        this.calculateRotationYaw(livingentity.getPosX(), livingentity.getPosZ());
                        this.moveController.setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), this.moveController.getSpeed());
                        this.wasOnGround = true;
                    }
                }
            }

            this.wasOnGround = this.onGround;
        }
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        this.entityDropItem(new ItemStack(UAMItems.TOMATO_FROG_EGG.get(), getRNG().nextInt(3) + 1));
        this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        ((AnimalEntity) p_241840_2_).resetInLove();
        return null;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);

        if (heldItem.getItem() == Items.BOWL && this.isAlive() && !this.isChild()) {
            playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(UAMItems.TOMATO_FROG_BOWL.get());
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
        return super.func_230254_b_(player, hand);
    }

    private void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setDisplayName(this.getCustomName());
        }
    }

    protected SoundEvent getAmbientSound() {
        return !this.isChild() ? UAMSounds.FROG_AMBIENT.get() : SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return !this.isChild() ? UAMSounds.FROG_HURT.get() : SoundEvents.ENTITY_COD_HURT;
    }

    protected SoundEvent getDeathSound() {
        return !this.isChild() ? UAMSounds.FROG_DEATH.get() : SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    protected float getSoundVolume() {
        return 0.3F;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SPIDER_EYE;
    }

    public boolean isPushedByWater() {
        return false;
    }

    public CreatureAttribute getCreatureAttribute() {
        return this.isChild() ? CreatureAttribute.WATER : CreatureAttribute.UNDEFINED;
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float) (MathHelper.atan2(z - this.getPosZ(), x - this.getPosX()) * (double) (180F / (float) Math.PI)) - 90.0F;
    }

    private void updateMoveTypeDuration() {
        if (this.moveController.getSpeed() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
    }

    public void livingTick() {
        super.livingTick();
        if (this.isChild() && !this.isInWater() && this.onGround && this.collidedVertically) {
            this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.onGround = false;
            this.isAirBorne = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public void travel(Vector3d p_213352_1_) {
        if (isChild() && this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_213352_1_);
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.TOMATO_FROG_SPAWN_EGG.get());
    }

    private static class FrogMoveController extends MovementController {
        private final TomatoFrogEntity frog;

        private FrogMoveController(TomatoFrogEntity frog) {
            super(frog);
            this.frog = frog;
        }

        public void tick() {
            if (this.frog.areEyesInFluid(FluidTags.WATER)) {
                this.frog.setMotion(this.frog.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.frog.getNavigator().noPath()) {
                double d0 = this.posX - this.frog.getPosX();
                double d1 = this.posY - this.frog.getPosY();
                double d2 = this.posZ - this.frog.getPosZ();
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.frog.rotationYaw = this.limitAngle(this.frog.rotationYaw, f, 90.0F);
                this.frog.renderYawOffset = this.frog.rotationYaw;
                float f1 = (float) (this.speed * this.frog.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                if (frog.isChild()) {
                    f1 *= 2.8;
                }
                this.frog.setAIMoveSpeed(MathHelper.lerp(0.125F, this.frog.getAIMoveSpeed(), f1));
                this.frog.setMotion(this.frog.getMotion().add(0.0D, (double) this.frog.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.frog.setAIMoveSpeed(0.0F);
            }
        }
    }

    private static class PlayerTemptGoal extends Goal {
        private static final EntityPredicate field_220834_a = (new EntityPredicate()).setDistance(10.0D).allowFriendlyFire().allowInvulnerable();
        private final TomatoFrogEntity frog;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Set<Item> temptItems;

        private PlayerTemptGoal(TomatoFrogEntity frog, double speedIn, Item temptItem) {
            this.frog = frog;
            this.speed = speedIn;
            this.temptItems = Sets.newHashSet(temptItem);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean shouldExecute() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return false;
            } else {
                this.tempter = this.frog.world.getClosestPlayer(field_220834_a, this.frog);
                if (this.tempter == null) {
                    return false;
                } else {
                    return this.isTemptedBy(this.tempter.getHeldItemMainhand()) || this.isTemptedBy(this.tempter.getHeldItemOffhand());
                }
            }
        }

        private boolean isTemptedBy(ItemStack p_203131_1_) {
            return this.temptItems.contains(p_203131_1_.getItem());
        }

        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        public void resetTask() {
            this.tempter = null;
            this.frog.getNavigator().clearPath();
            this.cooldown = 100;
        }

        public void tick() {
            this.frog.getLookController().setLookPositionWithEntity(this.tempter, (float) (this.frog.getHorizontalFaceSpeed() + 20), (float) this.frog.getVerticalFaceSpeed());
            if (this.frog.getDistanceSq(this.tempter) < 6.25D) {
                this.frog.getNavigator().clearPath();
            } else {
                this.frog.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
            }
        }
    }

    private static class FrogMovementGoal extends WaterAvoidingRandomWalkingGoal {
        public FrogMovementGoal(CreatureEntity creature) {
            super(creature, 1.0D);
        }

        @Override
        protected Vector3d getPosition() {
            if (creature.isChild()) return RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);
            return super.getPosition();
        }
    }
}

package mod.coda.unnamedanimalmod.entity;

import com.google.common.collect.Sets;
import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.pathfinding.GroundAndSwimmerNavigator;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public class TomatoFrogEntity extends AnimalEntity {
    private final Goal panicGoal = new PanicGoal(this, 1.25D);
    private final Goal tadpoleSwimGoal = new TomatoFrogEntity.TadpoleSwimGoal(this);
    private final Goal swimGoal = new SwimGoal(this);
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;

    public TomatoFrogEntity(EntityType<? extends TomatoFrogEntity> type, World world) {
        super(type, world);
        if (!this.isChild()) {
            this.moveController = new TomatoFrogEntity.AdultMoveHelperController(this);
            this.jumpController = new TomatoFrogEntity.JumpHelperController(this);
        } else {
            this.moveController = new TomatoFrogEntity.ChildMoveHelperController(this);
        }
        if(this.isChild()) {
            this.stepHeight = 1.0f;
        }
        else {
            this.stepHeight = 0.0f;
        }
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new TomatoFrogEntity.PlayerTemptGoal(this, 1.0D, Items.FERMENTED_SPIDER_EYE));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    @Override
    public void setGrowingAge(int age) {
        boolean wasChild = isChild();
        super.setGrowingAge(age);
        boolean isChild = isChild();
        if (!wasChild && isChild) {
            this.goalSelector.addGoal(0, panicGoal);
            this.goalSelector.addGoal(1, tadpoleSwimGoal);
            this.goalSelector.removeGoal(swimGoal);
        }
        else if (wasChild && !isChild) {
            this.goalSelector.removeGoal(panicGoal);
            this.goalSelector.removeGoal(tadpoleSwimGoal);
            this.goalSelector.addGoal(0, swimGoal);
        }
    }

    protected PathNavigator createNavigator(World world) {
        return new GroundAndSwimmerNavigator(this, world);
    }

    @Override
    public boolean canBreatheUnderwater() {
        if(this.isChild()) {
            return true;
        }
        else {
            return false;
        }
    }

    protected void updateAir(int air) {
        if(this.isChild()) {
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


    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.getPosY() + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                Vector3d vec3d = path.getPosition(this);
                if (vec3d.y > this.getPosY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    protected void jump() {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D && this.isChild()) {
            double d1 = horizontalMag(this.getMotion());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vector3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float p_175521_1_) {
        return this.jumpDuration == 0 ? 0.0F : ((float) this.jumpTicks + p_175521_1_) / (float) this.jumpDuration;
    }


    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.currentMoveTypeDuration == 0) {
                LivingEntity livingentity = this.getAttackTarget();
                if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
                    this.calculateRotationYaw(livingentity.getPosX(), livingentity.getPosZ());
                    this.moveController.setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), this.moveController.getSpeed());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            TomatoFrogEntity.JumpHelperController helperController = (TomatoFrogEntity.JumpHelperController)this.jumpController;
            if (!helperController.getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vector3d vec3d = new Vector3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        vec3d = path.getPosition(this);
                    }

                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            } else if (!helperController.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        this.resetInLove();
//        this.entityDropItem(ItemInit.Tomato_FROG_EGG.get());
        this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        return null;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (this.isBreedingItem(heldItem)) {
            if (this.getGrowingAge() >= 0 && this.canBreed()) {
                this.setInLove(player);
                player.swing(hand, true);
                heldItem.shrink(1);
            }
            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(player, hand);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.handleRunningEffect();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public class JumpHelperController extends JumpController {
        private final TomatoFrogEntity frog;
        private boolean canJump;

        public JumpHelperController(TomatoFrogEntity frog) {
            super(frog);
            this.frog = frog;
        }

        public boolean getIsJumping() {
            return this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        public void tick() {
            if (this.isJumping) {
                this.frog.startJumping();
                this.isJumping = false;
            }

        }
    }

    protected SoundEvent getAmbientSound() {
        if(!this.isChild()) {
            return SoundEvents.ENTITY_COD_AMBIENT; //UAMSounds.Tomato_FROG_AMBIENT.get();
        }
        else {
            return SoundEvents.ENTITY_COD_AMBIENT;
        }
    }

    protected float getSoundVolume() {
        return 0.3F;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        if(!this.isChild()) {
            return SoundEvents.ENTITY_COD_HURT; //UAMSounds.FROG_HURT.get();
        }
        else {
            return SoundEvents.ENTITY_COD_HURT;
        }
    }

    protected SoundEvent getDeathSound() {
        if(!this.isChild()) {
            return SoundEvents.ENTITY_COD_DEATH; //UAMSounds.FROG_DEATH.get();
        }
        else {
            return SoundEvents.ENTITY_COD_DEATH;
        }
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.FERMENTED_SPIDER_EYE;
    }

    public boolean isPushedByWater() {
        return false;
    }

    public CreatureAttribute getCreatureAttribute() {
        if(this.isChild()) {
            return CreatureAttribute.WATER;
        }
        else {
            return CreatureAttribute.UNDEFINED;
        }
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.getPosZ(), x - this.getPosX()) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    private void enableJumpControl() {
        ((TomatoFrogEntity.JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((TomatoFrogEntity.JumpHelperController)this.jumpController).setCanJump(false);
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
        this.disableJumpControl();
    }

    public void livingTick() {
        super.livingTick();
        if (!this.isInWater() && this.onGround && this.collidedVertically && this.isChild()) {
            this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.onGround = false;
            this.isAirBorne = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    static class AdultMoveHelperController extends MovementController {
        private final TomatoFrogEntity frog;
        private double nextJumpSpeed;

        public AdultMoveHelperController(TomatoFrogEntity frog) {
            super(frog);
            this.frog = frog;
        }

        public void tick() {
            if (this.frog.onGround && !this.frog.isJumping && !((TomatoFrogEntity.JumpHelperController)this.frog.jumpController).getIsJumping()) {
                this.frog.setMovementSpeed(0.0D);
            } else if (this.isUpdating()) {
                this.frog.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.frog.isInWater()) {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }


    static class ChildMoveHelperController extends MovementController {
        private final TomatoFrogEntity frog;

        ChildMoveHelperController(TomatoFrogEntity frog) {
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
                double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.frog.rotationYaw = this.limitAngle(this.frog.rotationYaw, f, 90.0F);
                this.frog.renderYawOffset = this.frog.rotationYaw;
                float f1 = (float)(this.speed * this.frog.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                this.frog.setAIMoveSpeed(MathHelper.lerp(0.125F, this.frog.getAIMoveSpeed(), f1));
                this.frog.setMotion(this.frog.getMotion().add(0.0D, (double)this.frog.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.frog.setAIMoveSpeed(0.0F);
            }
        }
    }

    @Override
    public void travel(Vector3d p_213352_1_) {
        if (this.isServerWorld() && this.isInWater()) {
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

    static class PlayerTemptGoal extends Goal {
        private static final EntityPredicate field_220834_a = (new EntityPredicate()).setDistance(10.0D).allowFriendlyFire().allowInvulnerable();
        private final TomatoFrogEntity frog;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Set<Item> temptItems;

        PlayerTemptGoal(TomatoFrogEntity frog, double speedIn, Item temptItem) {
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
            this.frog.getLookController().setLookPositionWithEntity(this.tempter, (float)(this.frog.getHorizontalFaceSpeed() + 20), (float)this.frog.getVerticalFaceSpeed());
            if (this.frog.getDistanceSq(this.tempter) < 6.25D) {
                this.frog.getNavigator().clearPath();
            } else {
                this.frog.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
            }
        }
    }

    static class TadpoleSwimGoal extends RandomSwimmingGoal {
        private final TomatoFrogEntity frog;

        public TadpoleSwimGoal(TomatoFrogEntity frog) {
            super(frog, 1.0D, 500);
            this.frog = frog;
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.TOMATO_FROG_SPAWN_EGG.get());
    }
}
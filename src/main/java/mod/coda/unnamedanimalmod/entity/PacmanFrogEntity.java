package mod.coda.unnamedanimalmod.entity;

import com.google.common.collect.Sets;
import mod.coda.unnamedanimalmod.init.ItemInit;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import mod.coda.unnamedanimalmod.init.SoundInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public class PacmanFrogEntity extends AnimalEntity {
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;

    public PacmanFrogEntity(EntityType<? extends PacmanFrogEntity> type, World world) {
        super(type, world);
        if (!this.isChild()) {
            this.moveController = new PacmanFrogEntity.AdultMoveHelperController(this);
        } else {
            this.moveController = new PacmanFrogEntity.ChildMoveHelperController(this);
        }
        this.stepHeight = 1.0f;
        this.jumpController = new PacmanFrogEntity.JumpHelperController(this);
    }

    protected void registerGoals() {
        if (this.isChild()) {
            this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
            this.goalSelector.addGoal(1, new FindWaterGoal(this));
            this.goalSelector.addGoal(2, new PacmanFrogEntity.SwimGoal(this));
        } else {
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new PanicGoal(this, 2.2D));
            this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
            this.goalSelector.addGoal(3, new PacmanFrogEntity.PlayerTemptGoal(this, 1.0D, Items.FERMENTED_SPIDER_EYE));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
            this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        }
    }

    protected PathNavigator createNavigator(World world) {
        if(this.isChild()) {
            return new SwimmerPathNavigator(this, world);
        }
        else {
            return new GroundPathNavigator(this, world);
        }
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
                Vec3d vec3d = path.getPosition(this);
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
                this.moveRelative(0.1F, new Vec3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float p_175521_1_) {
        if (this.isChild()) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + p_175521_1_) / (float)this.jumpDuration;
        }
        else {
            return 0.0f;
        }
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

            PacmanFrogEntity.JumpHelperController helperController = (PacmanFrogEntity.JumpHelperController)this.jumpController;
            if (!helperController.getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vec3d vec3d = new Vec3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
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
    public AgeableEntity createChild(AgeableEntity ageable) {
        this.resetInLove();
        this.entityDropItem(ItemInit.PACMAN_FROG_EGG.get());
        this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        return null;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            PacmanFrogEntity child = ModEntityTypes.PACMAN_FROG.create(world);
            if (child != null) {
                child.setGrowingAge(-24000);
                child.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
                this.world.addEntity(child);
                if (heldItem.hasDisplayName()) {
                    child.setCustomName(heldItem.getDisplayName());
                }
                this.onChildSpawnFromEgg(player, child);
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
            }
            return true;
        }
        if (this.isBreedingItem(heldItem)) {
            if (this.getGrowingAge() == 0 && this.canBreed()) {
                this.consumeItemFromStack(player, heldItem);
                this.setInLove(player);
                player.swing(hand, true);
            }
            return true;
        }
        if (this.isChild()) {
            this.consumeItemFromStack(player, heldItem);
            this.ageUp((int) (this.getGrowingAge() / -20.0 * 0.1), true);
        }
        return false;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        if(this.isChild() && this.isInWater()) {
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.5D);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.createRunningParticles();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public class JumpHelperController extends JumpController {
        private final PacmanFrogEntity frog;
        private boolean canJump;

        public JumpHelperController(PacmanFrogEntity frog) {
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
        return SoundInit.PACMAN_FROG_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundInit.PACMAN_FROG_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.PACMAN_FROG_DEATH.get();
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : super.attackEntityFrom(source, amount);
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
        ((PacmanFrogEntity.JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((PacmanFrogEntity.JumpHelperController)this.jumpController).setCanJump(false);
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
        private final PacmanFrogEntity frog;
        private double nextJumpSpeed;

        public AdultMoveHelperController(PacmanFrogEntity frog) {
            super(frog);
            this.frog = frog;
        }

        public void tick() {
            if (frog.isChild()) {
                if (this.frog.areEyesInFluid(FluidTags.WATER)) {
                    this.frog.setMotion(this.frog.getMotion().add(0.0D, 0.005D, 0.0D));
                }
                if (this.action == Action.MOVE_TO && !this.frog.getNavigator().noPath()) {
                    double lvt_1_1_ = this.posX - this.frog.getPosX();
                    double lvt_3_1_ = this.posY - this.frog.getPosY();
                    double lvt_5_1_ = this.posZ - this.frog.getPosZ();
                    double lvt_7_1_ = (double) MathHelper.sqrt(lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_);
                    lvt_3_1_ /= lvt_7_1_;
                    float lvt_9_1_ = (float) (MathHelper.atan2(lvt_5_1_, lvt_1_1_) * 57.2957763671875D) - 90.0F;
                    this.frog.rotationYaw = this.limitAngle(this.frog.rotationYaw, lvt_9_1_, 90.0F);
                    this.frog.renderYawOffset = this.frog.rotationYaw;
                    float lvt_10_1_ = (float) (this.speed * this.frog.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                    this.frog.setAIMoveSpeed(MathHelper.lerp(0.125F, this.frog.getAIMoveSpeed(), lvt_10_1_));
                    this.frog.setMotion(this.frog.getMotion().add(0.0D, (double) this.frog.getAIMoveSpeed() * lvt_3_1_ * 0.1D, 0.0D));
                } else {
                    this.frog.setAIMoveSpeed(0.0F);
                }
            }
            if (this.frog.onGround && !this.frog.isJumping && !((PacmanFrogEntity.JumpHelperController) this.frog.jumpController).getIsJumping()) {
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
        private final PacmanFrogEntity frog;

        ChildMoveHelperController(PacmanFrogEntity frog) {
            super(frog);
            this.frog = frog;
        }

        public void tick() {
            if (this.frog.areEyesInFluid(FluidTags.WATER) && frog.isChild()) {
                this.frog.setMotion(this.frog.getMotion().add(0.0D, 0.005D, 0.0D));
                if (this.action == Action.MOVE_TO && !this.frog.getNavigator().noPath()) {
                    double lvt_1_1_ = this.posX - this.frog.getPosX();
                    double lvt_3_1_ = this.posY - this.frog.getPosY();
                    double lvt_5_1_ = this.posZ - this.frog.getPosZ();
                    double lvt_7_1_ = (double) MathHelper.sqrt(lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_);
                    lvt_3_1_ /= lvt_7_1_;
                    float lvt_9_1_ = (float) (MathHelper.atan2(lvt_5_1_, lvt_1_1_) * 57.2957763671875D) - 90.0F;
                    this.frog.rotationYaw = this.limitAngle(this.frog.rotationYaw, lvt_9_1_, 90.0F);
                    this.frog.renderYawOffset = this.frog.rotationYaw;
                    float lvt_10_1_ = (float) (this.speed * this.frog.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                    this.frog.setAIMoveSpeed(MathHelper.lerp(0.125F, this.frog.getAIMoveSpeed(), lvt_10_1_));
                    this.frog.setMotion(this.frog.getMotion().add(0.0D, (double) this.frog.getAIMoveSpeed() * lvt_3_1_ * 0.1D, 0.0D));
                } else {
                    this.frog.setAIMoveSpeed(0.0F);
                }
            }
        }
    }

    @Override
    public void travel(Vec3d p_213352_1_) {
        if (this.isServerWorld() && this.isChild() && this.isInWater()) {
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
        private final PacmanFrogEntity frog;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Set<Item> temptItems;

        PlayerTemptGoal(PacmanFrogEntity frog, double speedIn, Item temptItem) {
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

    static class SwimGoal extends RandomSwimmingGoal {
        private final PacmanFrogEntity frog;

        public SwimGoal(PacmanFrogEntity frog) {
            super(frog, 1.0D, 500);
            this.frog = frog;
        }
    }
}
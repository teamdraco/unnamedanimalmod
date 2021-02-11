package mod.coda.unnamedanimalmod.entity;

import com.google.common.collect.Sets;
import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
import mod.coda.unnamedanimalmod.pathfinding.GroundAndSwimmerNavigator;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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

public class PacmanFrogEntity extends AnimalEntity {
    private static final String HIDDEN_DATA = "Hidden";
    private static final DataParameter<Boolean> HIDDEN = EntityDataManager.createKey(PacmanFrogEntity.class, DataSerializers.BOOLEAN);
    private Goal swimGoal;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;

    public PacmanFrogEntity(EntityType<? extends PacmanFrogEntity> type, World world) {
        super(type, world);
        this.moveController = new FrogMoveController(this);
        this.stepHeight = 1;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new HideGoal());
        if (!this.isHidden()) {
            this.goalSelector.addGoal(0, swimGoal = new SwimGoal(this));
            this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
            this.goalSelector.addGoal(1, new BreedGoal(this, 0.8D));
            this.goalSelector.addGoal(2, new FrogMovementGoal(this));
            this.goalSelector.addGoal(3, new PacmanFrogEntity.PlayerTemptGoal(this, 1.0D, Items.SPIDER_EYE));
            this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        }
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

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(HIDDEN, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean(HIDDEN_DATA, false);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setHidden(compound.getBoolean(HIDDEN_DATA));
    }

    public boolean isHidden() {
        return dataManager.get(HIDDEN);
    }

    public void setHidden(boolean hide) {
        dataManager.set(HIDDEN, hide);
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
        this.entityDropItem(new ItemStack(UAMItems.PACMAN_FROG_EGG.get(), getRNG().nextInt(4) + 1));
        this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
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
            ItemStack itemstack1 = new ItemStack(UAMItems.PACMAN_FROG_BOWL.get());
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

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isChild() && !this.isInWater() && this.onGround && this.collidedVertically) {
            this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.onGround = false;
            this.isAirBorne = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        if (isHidden()) {
            if (world.getBlockState(getPosition().down(1)).getMaterial() != Material.EARTH) setHidden(false);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() || isHidden();
    }

    public boolean canBePushed() {
        if (this.isHidden()) {
            return false;
        }
        else {
            return true;
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
        return new ItemStack(UAMItems.PACMAN_FROG_SPAWN_EGG.get());
    }

    private static class FrogMoveController extends MovementController {
        private final PacmanFrogEntity frog;

        private FrogMoveController(PacmanFrogEntity frog) {
            super(frog);
            this.frog = frog;
        }

        public void tick() {
            if (this.frog.areEyesInFluid(FluidTags.WATER)) {
                this.frog.setMotion(this.frog.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == Action.MOVE_TO && !this.frog.getNavigator().noPath()) {
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
        private final PacmanFrogEntity frog;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Set<Item> temptItems;

        private PlayerTemptGoal(PacmanFrogEntity frog, double speedIn, Item temptItem) {
            this.frog = frog;
            this.speed = speedIn;
            this.temptItems = Sets.newHashSet(temptItem);
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
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
            super(creature, 1.0D, 100);
        }

        @Override
        protected Vector3d getPosition() {
            if (creature.isChild()) return RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);
            return super.getPosition();
        }
    }

    class HideGoal extends Goal {
        private int hideTicks = 30;

        public HideGoal() {
            setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean shouldExecute() {
            return !isHidden() && belowIsEarth() && !isChild();
        }

        @Override
        public void resetTask() {
            hideTicks = 30;
        }

        @Override
        public void tick() {
            if (!isInWater() && !isChild())
                if (--hideTicks <= 0)
                {
                    setHidden(true);
                    hideTicks = 30;
                }
        }

/*        @Override
        public boolean shouldContinueExecuting() {
            for (LivingEntity entity : PacmanFrogEntity.this.world.getEntitiesWithinAABB(LivingEntity.class, PacmanFrogEntity.this.getBoundingBox().grow(1.0D, 1.0D, 1.0D))) {
                if (entity instanceof PacmanFrogEntity) {
                    return true;
                }
                else {
                    return false;
                }

            }
            return super.shouldContinueExecuting();
        }*/

        private boolean belowIsEarth() {
            return world.getBlockState(getPosition().down(1)).getMaterial() == Material.EARTH;
        }
    }
}

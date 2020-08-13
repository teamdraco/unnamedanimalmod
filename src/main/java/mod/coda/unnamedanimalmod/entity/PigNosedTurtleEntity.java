package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ItemInit;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PigNosedTurtleEntity extends AnimalEntity {
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(PigNosedTurtleEntity.class, DataSerializers.BOOLEAN);

    public PigNosedTurtleEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController =  new PigNosedTurtleEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(new IItemProvider[]{Items.SEAGRASS}), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        if(!this.isInWater()) {
            this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 2.0D, 100));
        }
        else {
            this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 2.0D, 100));
        }
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SEAGRASS;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        PigNosedTurtleEntity entity = new PigNosedTurtleEntity(ModEntityTypes.PIG_NOSED_TURTLE, this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
        return entity;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

/*    @Override
    public void livingTick() {
        limbSwingAmount = 1.5f;
        super.livingTick();
    }*/

    @Override
    public void travel(Vec3d p_213352_1_) {
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

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            PigNosedTurtleEntity child = ModEntityTypes.PIG_NOSED_TURTLE.create(world);
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
        if (heldItem.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(ItemInit.PIG_NOSED_TURTLE_BUCKET.get());
            if (!this.world.isRemote) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
            }
            if (heldItem.isEmpty()) {
                player.setHeldItem(hand, itemstack1);
            } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
                player.dropItem(itemstack1, false);
            }
            this.remove();
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

    public boolean preventDespawn() {
        return this.isFromBucket();
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(FROM_BUCKET, false);
    }

    private boolean isFromBucket() {
        return this.dataManager.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.dataManager.set(FROM_BUCKET, p_203706_1_);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TURTLE_AMBIENT_LAND;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_TURTLE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TURTLE_DEATH;
    }

    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(SoundEvents.ENTITY_TURTLE_SHAMBLE, 0.15F, 1.0F);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? size.height * 0.85F : size.height * 0.92F;
    }

    static class MoveHelperController extends MovementController {
        private final PigNosedTurtleEntity turtle;

        public MoveHelperController(PigNosedTurtleEntity turtleIn) {
            super(turtleIn);
            this.turtle = turtleIn;
        }

        public void tick() {
            if (this.turtle.areEyesInFluid(FluidTags.WATER)) {
                this.turtle.setMotion(this.turtle.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.turtle.getNavigator().noPath()) {
                double d0 = this.posX - this.turtle.getPosX();
                double d1 = this.posY - this.turtle.getPosY();
                double d2 = this.posZ - this.turtle.getPosZ();
                double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.turtle.rotationYaw = this.limitAngle(this.turtle.rotationYaw, f, 90.0F);
                this.turtle.renderYawOffset = this.turtle.rotationYaw;
                float f1 = (float)0.25D;
                this.turtle.setAIMoveSpeed(MathHelper.lerp(0.125F, this.turtle.getAIMoveSpeed(), f1));
                this.turtle.setMotion(this.turtle.getMotion().add(0.0D, (double)this.turtle.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.turtle.setAIMoveSpeed(0.0F);
            }
        }
    }
}

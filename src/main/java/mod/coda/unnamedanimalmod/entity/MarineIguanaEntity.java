package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class MarineIguanaEntity extends AnimalEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(MarineIguanaEntity.class, DataSerializers.VARINT);
    public int timeUntilNextSneeze = this.rand.nextInt(8000) + 8000;
    private UUID lightningUUID;
    private boolean didSneeze;

    public MarineIguanaEntity(EntityType<? extends MarineIguanaEntity> type, World world) {
        super(type, world);
        this.moveController = new MarineIguanaEntity.MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.stepHeight = 1;
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new MarineIguanaEntity.Navigator(this, world);
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
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.STONE && worldIn.getLightSubtracted(pos, 0) > 8;
    }

    @Override
    public int getTalkInterval() {
        return 360;
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
        this.goalSelector.addGoal(2, new MarineIguanaEntity.WanderGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new MarineIguanaEntity.GoToWaterGoal(this, 1.0D));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SEAGRASS;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15);
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return 1.5F;
        }
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
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isAlive() && !this.isChild() && --this.timeUntilNextSneeze <= 0) {
            if (!this.isSilent()) {
                this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            }
            this.entityDropItem(new ItemStack(UAMItems.SALT.get(), rand.nextInt(4)));
            MarineIguanaEntity iguanaEntity = (MarineIguanaEntity)this;
            this.timeUntilNextSneeze = this.rand.nextInt(8000) + 6000;
            this.didSneeze = true;

            iguanaEntity.setDidSneeze(false);
        }
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.down()).isIn(Blocks.STONE) && worldIn.getLightSubtracted(pos, 0) > 8;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (dataTag == null) {
            setVariant(rand.nextInt(4));
        } else {
            if (dataTag.contains("BucketVariantTag", 3)){
                this.setVariant(dataTag.getInt("BucketVariantTag"));
            }
        }
        return spawnDataIn;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("BucketVariantTag", getVariant());
        compound.putInt("SneezeTime", this.timeUntilNextSneeze);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setVariant(compound.getInt("BucketVariantTag"));
        if (compound.contains("SneezeTime")) {
            this.timeUntilNextSneeze = compound.getInt("SneezeTime");
        }
   }

    public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
        UUID uuid = p_241841_2_.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setVariant(4);
            this.lightningUUID = uuid;
            this.playSound(UAMSounds.MARINE_IGUANA_TRANSFORMS.get(), 2.0F, 1.0F);
        }
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        this.entityDropItem(new ItemStack(UAMItems.MARINE_IGUANA_EGG.get(), getRNG().nextInt(1) + 1));
        this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        ((AnimalEntity) p_241840_2_).resetInLove();
        return null;
    }

    private void setDidSneeze(boolean didSneezeIn) {
        this.didSneeze = didSneezeIn;
    }

    protected SoundEvent getAmbientSound() {
        return UAMSounds.MARINE_IGUANA_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return UAMSounds.MARINE_IGUANA_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UAMSounds.MARINE_IGUANA_HURT.get();
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.MARINE_IGUANA_SPAWN_EGG.get());
    }

    static class MoveHelperController extends MovementController {
        private final MarineIguanaEntity iguana;

        MoveHelperController(MarineIguanaEntity iguana) {
            super(iguana);
            this.iguana = iguana;
        }

        private void updateSpeed() {
            if (this.iguana.isInWater()) {
                this.iguana.setMotion(this.iguana.getMotion().add(0.0D, 0.005D, 0.0D));

                if (this.iguana.isChild()) {
                    this.iguana.setAIMoveSpeed(Math.max(this.iguana.getAIMoveSpeed() / 3.0F, 0.06F));
                }
            } else if (this.iguana.onGround) {
                this.iguana.setAIMoveSpeed(Math.max(this.iguana.getAIMoveSpeed() / 1.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.action == MovementController.Action.MOVE_TO && !this.iguana.getNavigator().noPath()) {
                double d0 = this.posX - this.iguana.getPosX();
                double d1 = this.posY - this.iguana.getPosY();
                double d2 = this.posZ - this.iguana.getPosZ();
                double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.iguana.rotationYaw = this.limitAngle(this.iguana.rotationYaw, f, 90.0F);
                this.iguana.renderYawOffset = this.iguana.rotationYaw;
                float f1 = (float)(this.speed * this.iguana.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.iguana.setAIMoveSpeed(MathHelper.lerp(0.125F, this.iguana.getAIMoveSpeed(), f1));
                this.iguana.setMotion(this.iguana.getMotion().add(0.0D, (double)this.iguana.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.iguana.setAIMoveSpeed(0.0F);
            }
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(MarineIguanaEntity iguana, World worldIn) {
            super(iguana, worldIn);
        }

        protected boolean canNavigate() {
            return true;
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            if (this.entity instanceof MarineIguanaEntity) {
                return !this.world.getBlockState(pos.down()).isAir();
                }
            else return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final MarineIguanaEntity iguana;

        private WanderGoal(MarineIguanaEntity iguana, double speedIn, int chance) {
            super(iguana, speedIn, chance);
            this.iguana = iguana;
        }

        public boolean shouldExecute() {
            return !this.creature.isInWater() && super.shouldExecute();
        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final MarineIguanaEntity iguana;

        private GoToWaterGoal(MarineIguanaEntity iguana, double speedIn) {
            super(iguana, iguana.isChild() ? 2.0D : speedIn, 24);
            this.iguana = iguana;
            this.field_203112_e = -1;
        }

        public boolean shouldContinueExecuting() {
            return !this.iguana.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.iguana.world, this.destinationBlock);
        }

        public boolean shouldExecute() {
            if (this.iguana.isChild() && !this.iguana.isInWater()) {
                return super.shouldExecute();
            } else {
                return !this.iguana.isInWater() && super.shouldExecute();
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
package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;

import javax.annotation.Nullable;
import java.util.Random;

public class MangroveSnakeEntity extends CreatureEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(MangroveSnakeEntity.class, DataSerializers.VARINT);

    public MangroveSnakeEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new MangroveSnakeEntity.MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.stepHeight = 1;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 2.0D, 1) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new MangroveSnakeEntity.WanderGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new MangroveSnakeEntity.GoToWaterGoal(this, 1.0D));
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new MangroveSnakeEntity.Navigator(this, world);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return UAMSounds.MANGROVE_SNAKE_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return UAMSounds.MANGROVE_SNAKE_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UAMSounds.MANGROVE_SNAKE_HURT.get();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (rand.nextFloat() > 0.2D) {
            setVariant(0);
        }
        else {
            setVariant(1);
        }
        return spawnDataIn;
    }

    public static boolean canAnimalSpawn(EntityType<? extends MangroveSnakeEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK) && worldIn.getLightSubtracted(pos, 0) > 8;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.MANGROVE_SNAKE_SPAWN_EGG.get());
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

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0);
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return 1.5F;
        }
    }

    static class MoveHelperController extends MovementController {
        private final MangroveSnakeEntity snake;

        MoveHelperController(MangroveSnakeEntity snake) {
            super(snake);
            this.snake = snake;
        }

        private void updateSpeed() {
            if (this.snake.isInWater()) {
                this.snake.setMotion(this.snake.getMotion().add(0.0D, 0.005D, 0.0D));

                if (this.snake.isChild()) {
                    this.snake.setAIMoveSpeed(Math.max(this.snake.getAIMoveSpeed() / 3.0F, 0.06F));
                }
            } else if (this.snake.onGround) {
                this.snake.setAIMoveSpeed(Math.max(this.snake.getAIMoveSpeed(), 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.action == MovementController.Action.MOVE_TO && !this.snake.getNavigator().noPath()) {
                double d0 = this.posX - this.snake.getPosX();
                double d1 = this.posY - this.snake.getPosY();
                double d2 = this.posZ - this.snake.getPosZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.snake.rotationYaw = this.limitAngle(this.snake.rotationYaw, f, 90.0F);
                this.snake.renderYawOffset = this.snake.rotationYaw;
                float f1 = (float)(this.speed * this.snake.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.snake.setAIMoveSpeed(MathHelper.lerp(0.125F, this.snake.getAIMoveSpeed(), f1));
                this.snake.setMotion(this.snake.getMotion().add(0.0D, (double)this.snake.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.snake.setAIMoveSpeed(0.0F);
            }
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(MangroveSnakeEntity snake, World worldIn) {
            super(snake, worldIn);
        }

        protected boolean canNavigate() {
            return true;
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            if (this.entity instanceof MangroveSnakeEntity) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
            else return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final MangroveSnakeEntity snake;

        private WanderGoal(MangroveSnakeEntity snake, double speedIn, int chance) {
            super(snake, speedIn, chance);
            this.snake = snake;
        }

        public boolean shouldExecute() {
            return !this.creature.isInWater() && super.shouldExecute();
        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final MangroveSnakeEntity snake;

        private GoToWaterGoal(MangroveSnakeEntity snake, double speedIn) {
            super(snake, snake.isChild() ? 2.0D : speedIn, 24);
            this.snake = snake;
            this.field_203112_e = -1;
        }

        public boolean shouldContinueExecuting() {
            return !this.snake.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.snake.world, this.destinationBlock);
        }

        public boolean shouldExecute() {
            if (this.snake.isChild() && !this.snake.isInWater()) {
                return super.shouldExecute();
            } else {
                return !this.snake.isInWater() && super.shouldExecute();
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

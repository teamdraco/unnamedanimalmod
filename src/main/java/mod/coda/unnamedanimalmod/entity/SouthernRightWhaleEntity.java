package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.entity.goals.WhaleBreachGoal;
import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class SouthernRightWhaleEntity extends AnimalEntity {
    protected boolean noBlow = false;

    public SouthernRightWhaleEntity(EntityType<? extends SouthernRightWhaleEntity> type, World world) {
        super(type, world);
        this.moveController = new SouthernRightWhaleEntity.MoveHelperController(this);
        this.lookController = new DolphinLookController(this, 10);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreatheAirGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.goalSelector.addGoal(2, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(5, new WhaleBreachGoal(this, 10));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 120.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.2F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setAir(this.getMaxAir());
        this.rotationPitch = 0.0F;
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.SOUTHERN_RIGHT_WHALE.get().create(this.world);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public static boolean canWhaleSpawn(EntityType<? extends SouthernRightWhaleEntity> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getBlockState(pos).isIn(Blocks.WATER) && worldIn.getWorldInfo().getDayTime() < 12000 && worldIn.getWorldInfo().getDayTime() > 24000;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SALMON;
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? size.height * 0.4F : size.height * 0.85F;
    }

    public void tick() {
        super.tick();
        BlockPos pos = getPosition().up();

        if (!noBlow && world.getBlockState(pos).getBlock() == Blocks.AIR && inWater){
            playBlowAnimation();
            noBlow = true;
        }
        if (inWater && world.getBlockState(pos).getBlock() != Blocks.AIR){
            noBlow = false;
        }
    }

    protected void playBlowAnimation(){
/*        int blowHeight = 10;
        int intensity = 40;
        if (this.world.isRemote) {
            double d0 = 0;
            double d1 = 0;
            double d2 = 0;

            for (int i = 0; i < blowHeight + 3; i++) {
                for(int b = 0; b < intensity; ++b) {
                    this.world.addParticle(ParticleTypes.FALLING_WATER,
                            this.getPosX() - MathHelper.sin(-renderYawOffset * 0.017453292F)
                                    + (MathHelper.sin(-renderYawOffset * 0.017453292F) * 2.8F),
                            this.getPosY() + 1.4F + (i * 0.4F), this.getPosZ() - MathHelper.cos(renderYawOffset * 0.017453292F)
                                    + (MathHelper.cos(renderYawOffset * 0.017453292F) * 2.8F),
                            d0, d1, d2);
                }
            }

            for (int i = 0; i < 3; i++) {
                this.world.addParticle(ParticleTypes.CLOUD,
                        this.getPosX() - MathHelper.sin(-renderYawOffset * 0.017453292F)
                                + (MathHelper.sin(-renderYawOffset * 0.017453292F) * 2.8F),
                        this.getPosY() + 1.4F + (i * 0.4F), this.getPosZ() - MathHelper.cos(renderYawOffset * 0.017453292F)
                                + (MathHelper.cos(renderYawOffset * 0.017453292F) * 2.8F),
                        d0, d1, d2);
            }
        }*/
        playSound(UAMSounds.SOUTHERN_RIGHT_WHALE_SONG.get(), 5.0f, 1.0f);
    }

    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    static class MoveHelperController extends MovementController {
        private final SouthernRightWhaleEntity whale;

        public MoveHelperController(SouthernRightWhaleEntity whaleIn) {
            super(whaleIn);
            this.whale = whaleIn;
        }

        public void tick() {
            if (this.whale.isInWater()) {
                this.whale.setMotion(this.whale.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.whale.getNavigator().noPath()) {
                double d0 = this.posX - this.whale.getPosX();
                double d1 = this.posY - this.whale.getPosY();
                double d2 = this.posZ - this.whale.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setMoveForward(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.whale.rotationYaw = this.limitAngle(this.whale.rotationYaw, f, 10.0F);
                    this.whale.renderYawOffset = this.whale.rotationYaw;
                    this.whale.rotationYawHead = this.whale.rotationYaw;
                    float f1 = (float)(this.speed * this.whale.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                    if (this.whale.isInWater()) {
                        this.whale.setAIMoveSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.whale.rotationPitch = this.limitAngle(this.whale.rotationPitch, f2, 5.0F);
                        float f3 = MathHelper.cos(this.whale.rotationPitch * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.whale.rotationPitch * ((float)Math.PI / 180F));
                        this.whale.moveForward = f3 * f1;
                        this.whale.moveVertical = -f4 * f1;
                    } else {
                        this.whale.setAIMoveSpeed(f1 * 0.1F);
                    }
                }
            } else {
                this.whale.setAIMoveSpeed(0.0F);
                this.whale.setMoveStrafing(0.0F);
                this.whale.setMoveVertical(0.0F);
                this.whale.setMoveForward(0.0F);
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return UAMSounds.SOUTHERN_RIGHT_WHALE_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return UAMSounds.SOUTHERN_RIGHT_WHALE_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UAMSounds.SOUTHERN_RIGHT_WHALE_HURT.get();
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.SOUTHERN_RIGHT_WHALE_SPAWN_EGG.get());
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final SouthernRightWhaleEntity whale;

        public SwimGoal(SouthernRightWhaleEntity whale) {
            super(whale, 1.0D, 40);
            this.whale = whale;
        }

        public boolean shouldExecute() {
            return super.shouldExecute();
        }
    }
}
package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.passive.DolphinEntity;
import teamdraco.unnamedanimalmod.common.entity.util.ai.WhaleBreachGoal;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
import teamdraco.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
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
    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(SouthernRightWhaleEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> MOISTNESS_LEVEL = EntityDataManager.defineId(SouthernRightWhaleEntity.class, DataSerializers.INT);
    protected boolean noBlow = false;

    public SouthernRightWhaleEntity(EntityType<? extends SouthernRightWhaleEntity> type, World world) {
        super(type, world);
        this.moveControl = new SouthernRightWhaleEntity.MoveHelperController(this);
        this.lookControl = new DolphinLookController(this, 10);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
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
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 120.0D).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 4.0F);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    public int getMoistnessLevel() {
        return this.entityData.get(MOISTNESS_LEVEL);
    }

    public void setMoisntessLevel(int p_211137_1_) {
        this.entityData.set(MOISTNESS_LEVEL, p_211137_1_);
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setAirSupply(this.getMaxAirSupply());
        this.xRot = 0.0F;
        if (dataTag == null) {
            if (random.nextFloat() > 0.1D) {
                setVariant(random.nextInt(3));
            }
            else {
                setVariant(3);
            }
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(MOISTNESS_LEVEL, 2400);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.SOUTHERN_RIGHT_WHALE.get().create(this.level);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static boolean checkWhaleSpawnRules(EntityType<SouthernRightWhaleEntity> p_223364_0_, IWorld p_223364_1_, SpawnReason reason, BlockPos p_223364_3_, Random p_223364_4_) {
        return p_223364_1_.getRandom().nextFloat() > 0.99F && p_223364_1_.getBlockState(p_223364_3_.below()).is(Blocks.WATER);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.SALMON;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return size.height * 0.4F;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos pos = blockPosition().above();

        if (!noBlow && level.getBlockState(pos).getBlock() == Blocks.AIR && wasTouchingWater){
            playBlowAnimation();
            noBlow = true;
        }
        if (wasTouchingWater && level.getBlockState(pos).getBlock() != Blocks.AIR){
            noBlow = false;
        }
        if (this.isInWaterRainOrBubble()) {
            this.setMoisntessLevel(2400);
        } else {
            this.setMoisntessLevel(this.getMoistnessLevel() - 1);
            if (this.getMoistnessLevel() <= 0) {
                this.hurt(DamageSource.DRY_OUT, 1.0F);
            }
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

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
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
    protected SoundEvent getAmbientSound() {
        return UAMSounds.SOUTHERN_RIGHT_WHALE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UAMSounds.SOUTHERN_RIGHT_WHALE_DEATH.get();
    }

    @Override
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
            super(whale, 1.0D, 2);
            this.whale = whale;
        }

        public boolean canUse() {
            return super.canUse();
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
                this.whale.setDeltaMovement(this.whale.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.whale.getNavigation().isDone()) {
                double d0 = this.wantedX - this.whale.getX();
                double d1 = this.wantedY - this.whale.getY();
                double d2 = this.wantedZ - this.whale.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.whale.yRot = this.rotlerp(this.whale.yRot, f, 10.0F);
                    this.whale.yBodyRot = this.whale.yRot;
                    this.whale.yHeadRot = this.whale.yRot;
                    float f1 = (float)(this.speedModifier * this.whale.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.whale.isInWater()) {
                        this.whale.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.whale.xRot = this.rotlerp(this.whale.xRot, f2, 5.0F);
                        float f3 = MathHelper.cos(this.whale.xRot * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.whale.xRot * ((float)Math.PI / 180F));
                        this.whale.zza = f3 * f1;
                        this.whale.yya = -f4 * f1;
                    } else {
                        this.whale.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.whale.setSpeed(0.0F);
                this.whale.setXxa(0.0F);
                this.whale.setYya(0.0F);
                this.whale.setZza(0.0F);
            }
        }
    }
}
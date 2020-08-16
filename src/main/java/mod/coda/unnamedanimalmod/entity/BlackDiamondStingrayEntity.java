package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlackDiamondStingrayEntity extends AbstractFishEntity {
    public BlackDiamondStingrayEntity(EntityType<? extends AbstractFishEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new BlackDiamondStingrayEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    static class MoveHelperController extends MovementController {
        private final BlackDiamondStingrayEntity ray;

        MoveHelperController(BlackDiamondStingrayEntity ray) {
            super(ray);
            this.ray = ray;
        }

        public void tick() {
            if (this.ray.areEyesInFluid(FluidTags.WATER)) {
                this.ray.setMotion(this.ray.getMotion().add(0.0D, 0.0D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.ray.getNavigator().noPath()) {
                double d0 = this.posX - this.ray.getPosX();
                double d1 = this.posY - this.ray.getPosY();
                double d2 = this.posZ - this.ray.getPosZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.ray.rotationYaw = this.limitAngle(this.ray.rotationYaw, f, 90.0F);
                this.ray.renderYawOffset = this.ray.rotationYaw;
                float f1 = (float)(this.speed * this.ray.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                this.ray.setAIMoveSpeed(MathHelper.lerp(0.125F, this.ray.getAIMoveSpeed(), f1));
                this.ray.setMotion(this.ray.getMotion().add(0.0D, (double)this.ray.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.ray.setAIMoveSpeed(0.0F);
            }
        }
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    }

    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (entityIn instanceof ServerPlayerEntity && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 1)) {
            ((ServerPlayerEntity)entityIn).connection.sendPacket(new SChangeGameStatePacket(9, 0.0F));
            entityIn.addPotionEffect(new EffectInstance(Effects.POISON, 60, 0));
        }
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.95F;
    }

    protected ItemStack getFishBucket() {
        return new ItemStack(ItemInit.BLACK_DIAMOND_STINGRAY_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PUFFER_FISH_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_FLOP;
    }
}
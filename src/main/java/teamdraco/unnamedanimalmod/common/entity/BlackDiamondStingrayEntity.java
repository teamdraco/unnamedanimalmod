package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.init.UAMItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import net.minecraft.entity.ai.controller.MovementController.Action;

public class BlackDiamondStingrayEntity extends AbstractFishEntity {
    public BlackDiamondStingrayEntity(EntityType<? extends AbstractFishEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new MoveHelperController(this);
        this.setPathfindingMalus(PathNodeType.WATER, 1.0F);
        this.maxUpStep = 0.0f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    protected PathNavigator createNavigation(World world) {
        return new GroundPathNavigator(this, world);
    }

    static class MoveHelperController extends MovementController {
        private final BlackDiamondStingrayEntity ray;

        MoveHelperController(BlackDiamondStingrayEntity ray) {
            super(ray);
            this.ray = ray;
        }

        public void tick() {
            if (this.ray.isEyeInFluid(FluidTags.WATER)) {
                this.ray.setDeltaMovement(this.ray.getDeltaMovement().add(0.0D, 0.0D, 0.0D));
            }

            if (this.operation == Action.MOVE_TO && !this.ray.getNavigation().isDone()) {
                double d0 = this.wantedX - this.ray.getX();
                double d1 = this.wantedY - this.ray.getY();
                double d2 = this.wantedZ - this.ray.getZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.ray.yRot = this.rotlerp(this.ray.yRot, f, 90.0F);
                this.ray.yBodyRot = this.ray.yRot;
                float f1 = (float)(this.speedModifier * this.ray.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                this.ray.setSpeed(MathHelper.lerp(0.125F, this.ray.getSpeed(), f1));
                this.ray.setDeltaMovement(this.ray.getDeltaMovement().add(0.0D, (double)this.ray.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.ray.setSpeed(0.0F);
            }
        }
    }

    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D);
    }

    public void playerTouch(PlayerEntity entityIn) {
        if (entityIn instanceof ServerPlayerEntity && entityIn.hurt(DamageSource.mobAttack(this), 1)) {
            ((ServerPlayerEntity)entityIn).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.PUFFER_FISH_STING, 0.0F));
            entityIn.addEffect(new EffectInstance(Effects.POISON, 60, 0));
        }
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.95F;
    }

    protected ItemStack getBucketItemStack() {
        return new ItemStack(UAMItems.BLACK_DIAMOND_STINGRAY_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PUFFER_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PUFFER_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PUFFER_FISH_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.PUFFER_FISH_FLOP;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.BLACK_DIAMOND_STINGRAY_SPAWN_EGG.get());
    }
}
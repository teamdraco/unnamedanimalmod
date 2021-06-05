package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
import teamdraco.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Predicate;

public class MuskOxEntity extends AnimalEntity {
    public MuskOxEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.GRASS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal());
        this.targetSelector.addGoal(1, new AttackWolfGoal());
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }
        return flag;
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return random.nextDouble() > 0.5 && worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && worldIn.getLevelData().getDayTime() > 11500 && worldIn.getLevelData().getDayTime() < 13500;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected SoundEvent getAmbientSound() {
        return UAMSounds.MUSK_OX_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UAMSounds.MUSK_OX_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UAMSounds.MUSK_OX_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.MUSK_OX.get().create(this.level);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        Item item = heldItem.getItem();

        if (item.getItem() == Items.BUCKET && !player.abilities.instabuild && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            heldItem.shrink(1);
            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
            } else if (!player.inventory.add(new ItemStack(Items.MILK_BUCKET))) {
                player.drop(new ItemStack(Items.MILK_BUCKET), false);
            }
            return ActionResultType.SUCCESS;
        }
        else {
            return super.mobInteract(player, hand);
        }
    }


    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95F : 1.0F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.GRASS;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.MUSK_OX_SPAWN_EGG.get());
    }

    class AttackWolfGoal extends NearestAttackableTargetGoal<WolfEntity> {
        public AttackWolfGoal() {
            super(MuskOxEntity.this, WolfEntity.class, 20, true, true, (Predicate<LivingEntity>) null);
        }

        public boolean canUse() {
            if (MuskOxEntity.this.isBaby()) {
                return false;
            } else {
                if (super.canUse()) {
                    for (MuskOxEntity muskOxEntity : MuskOxEntity.this.level.getEntitiesOfClass(MuskOxEntity.class, MuskOxEntity.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
                        if (muskOxEntity.isBaby()) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }

        protected double getFollowDistance() {
            return super.getFollowDistance() * 0.5D;
        }
    }

    class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal() {
            super(MuskOxEntity.this);
        }

        public void start() {
            super.start();
            if (MuskOxEntity.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }
        }

        protected void alertOther(MobEntity mobIn, LivingEntity targetIn) {
            if (mobIn instanceof MuskOxEntity && !mobIn.isBaby()) {
                super.alertOther(mobIn, targetIn);
            }
        }
    }

}
package teamdraco.unnamedanimalmod.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
import teamdraco.unnamedanimalmod.init.UAMSounds;

import javax.annotation.Nullable;
import java.util.Random;

public class MuskOxEntity extends Animal {
    public MuskOxEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.GRASS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
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

    public static boolean canAnimalSpawn(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
        return random.nextDouble() > 0.5 && worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && worldIn.getLevelData().getDayTime() > 11500 && worldIn.getLevelData().getDayTime() < 13500;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 2.0D);
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
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return UAMEntities.MUSK_OX.get().create(this.level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        Item item = heldItem.getItem();

        if (item == Items.BUCKET && !player.getAbilities().instabuild && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            heldItem.shrink(1);
            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
            } else if (!player.getInventory().add(new ItemStack(Items.MILK_BUCKET))) {
                player.drop(new ItemStack(Items.MILK_BUCKET), false);
            }
            return InteractionResult.SUCCESS;
        }
        else {
            return super.mobInteract(player, hand);
        }
    }


    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95F : 1.0F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.GRASS;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(UAMItems.MUSK_OX_SPAWN_EGG.get());
    }

    class AttackWolfGoal extends NearestAttackableTargetGoal<Wolf> {
        public AttackWolfGoal() {
            super(MuskOxEntity.this, Wolf.class, 20, true, true, null);
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

    class HurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal {
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

        protected void alertOther(Mob mobIn, LivingEntity targetIn) {
            if (mobIn instanceof MuskOxEntity && !mobIn.isBaby()) {
                super.alertOther(mobIn, targetIn);
            }
        }
    }

}

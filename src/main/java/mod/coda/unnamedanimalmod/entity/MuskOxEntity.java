package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
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
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
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
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.GRASS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal());
        this.targetSelector.addGoal(1, new AttackWolfGoal());
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return random.nextDouble() > 0.5 && worldIn.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK) && worldIn.getWorldInfo().getDayTime() > 11500 && worldIn.getWorldInfo().getDayTime() < 13500;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
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
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.MUSK_OX.get().create(this.world);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item.getItem() == Items.BUCKET && !player.abilities.isCreativeMode && !this.isChild()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            heldItem.shrink(1);
            if (heldItem.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
            } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
                player.dropItem(new ItemStack(Items.MILK_BUCKET), false);
            }
            return ActionResultType.SUCCESS;
        }
        else {
            return super.func_230254_b_(player, hand);
        }
    }


    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.95F : 1.0F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
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

        public boolean shouldExecute() {
            if (MuskOxEntity.this.isChild()) {
                return false;
            } else {
                if (super.shouldExecute()) {
                    for (MuskOxEntity muskOxEntity : MuskOxEntity.this.world.getEntitiesWithinAABB(MuskOxEntity.class, MuskOxEntity.this.getBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
                        if (muskOxEntity.isChild()) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }

        protected double getTargetDistance() {
            return super.getTargetDistance() * 0.5D;
        }
    }

    class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal() {
            super(MuskOxEntity.this);
        }

        public void startExecuting() {
            super.startExecuting();
            if (MuskOxEntity.this.isChild()) {
                this.alertOthers();
                this.resetTask();
            }
        }

        protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
            if (mobIn instanceof MuskOxEntity && !mobIn.isChild()) {
                super.setAttackTarget(mobIn, targetIn);
            }
        }
    }

}
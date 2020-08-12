package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import mod.coda.unnamedanimalmod.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Predicate;

public class HornedViperEntity extends AnimalEntity {
    public static final Predicate<LivingEntity> TARGET_ENTITY = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype == EntityType.RABBIT;
    };

    public HornedViperEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static boolean canViperSpawn(EntityType<? extends AnimalEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.SAND && world.getLightSubtracted(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0D, Ingredient.fromItems(new IItemProvider[]{Items.RABBIT}), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        HornedViperEntity entity = new HornedViperEntity(ModEntityTypes.HORNED_VIPER_ENTITY.get(), this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
        return entity;
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean lvt_2_1_ = super.attackEntityAsMob(entity);
        if (lvt_2_1_ && this.getHeldItemMainhand().isEmpty() && entity instanceof LivingEntity) {
            float lvt_3_1_ = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.POISON, 70 * (int)lvt_3_1_));
        }

        return lvt_2_1_;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            HornedViperEntity child = ModEntityTypes.HORNED_VIPER_ENTITY.get().create(world);
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
                return true;
            }
        return false;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }


    protected SoundEvent getAmbientSound() {
        return SoundInit.HORNED_VIPER_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundInit.HORNED_VIPER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.HORNED_VIPER_DEATH.get();
    }

    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(SoundEvents.ENTITY_RABBIT_JUMP, 0.15F, 1.0F);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? size.height * 0.85F : size.height * 0.92F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.RABBIT;
    }
}
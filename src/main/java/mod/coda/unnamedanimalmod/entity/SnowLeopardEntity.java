package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import mod.coda.unnamedanimalmod.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SnowLeopardEntity extends TameableEntity {

    public SnowLeopardEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
    }

    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, this.sitGoal);
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.5f));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.MUTTON), false));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal());
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, SheepEntity.class, false));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean lvt_2_1_ = super.attackEntityAsMob(entity);
        if (lvt_2_1_ && this.getHeldItemMainhand().isEmpty() && entity instanceof LivingEntity) {
            float lvt_3_1_ = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
        }
        return lvt_2_1_;
    }

    class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal() {
            super(SnowLeopardEntity.this);
        }

        public void startExecuting() {
            super.startExecuting();
            if (!SnowLeopardEntity.this.isTamed()) {
                this.resetTask();
            }
        }

        protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
            if (mobIn instanceof SnowLeopardEntity && !mobIn.isChild()) {
                super.setAttackTarget(mobIn, targetIn);
            }
        }
    }

    protected void registerAttributes() {
        super.registerAttributes();
        if(!this.isTamed()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(36.0D);
        }
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    protected SoundEvent getAmbientSound() {
        return SoundInit.SNOW_LEOPARD_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundInit.SNOW_LEOPARD_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.SNOW_LEOPARD_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        SnowLeopardEntity entity = new SnowLeopardEntity(ModEntityTypes.SNOW_LEOPARD, this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
        return entity;
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            SnowLeopardEntity child = ModEntityTypes.SNOW_LEOPARD.create(world);
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
        if (this.isTamed()) {
            if (item.isFood() && item.getFood().isMeat() && this.getHealth() < this.getMaxHealth()) {
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
                this.heal((float) item.getFood().getHealing());
                return true;
            }
            if (this.isOwner(player) && !this.isBreedingItem(heldItem) && this.sitGoal != null) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        }

        else if (item == Items.BONE) {
            if (!player.abilities.isCreativeMode) {
                heldItem.shrink(1);
            }
            if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player) && this.sitGoal != null) {
                this.setTamedBy(player);
                this.navigator.clearPath();
                this.setAttackTarget(null);
                this.sitGoal.setSitting(true);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.world.setEntityState(this, (byte) 6);
            }
            return true;
        }
        return false;
    }

    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof SnowLeopardEntity)) {
            return false;
        } else {
            SnowLeopardEntity leopardEntity = (SnowLeopardEntity)otherAnimal;
            if (!leopardEntity.isTamed()) {
                return false;
            } else if (leopardEntity.isSitting()) {
                return false;
            } else {
                return this.isInLove() && leopardEntity.isInLove();
            }
        }
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity) && !(target instanceof SnowLeopardEntity)) {
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canAttackPlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
                return false;
            } else {
            return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
        }
        return false;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.85F : 1.0F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.MUTTON;
    }
}
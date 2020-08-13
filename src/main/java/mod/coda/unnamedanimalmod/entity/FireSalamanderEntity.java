package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.ItemInit;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import mod.coda.unnamedanimalmod.items.ModPotItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FireSalamanderEntity extends AnimalEntity {
    private static final DataParameter<Boolean> FROM_POT = EntityDataManager.createKey(FireSalamanderEntity.class, DataSerializers.BOOLEAN);

    public FireSalamanderEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(new IItemProvider[]{Items.FERMENTED_SPIDER_EYE}), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        FireSalamanderEntity entity = new FireSalamanderEntity(ModEntityTypes.FIRE_SALAMANDER, this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
        return entity;
    }

    public boolean preventDespawn() {
        return this.isFromPot();
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return !this.isFromPot() && !this.hasCustomName();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(FROM_POT, false);
    }

    private boolean isFromPot() {
        return this.dataManager.get(FROM_POT);
    }

    public void setFromPot(boolean p_203706_1_) {
        this.dataManager.set(FROM_POT, p_203706_1_);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("FromPot", this.isFromPot());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFromPot(compound.getBoolean("FromPot"));
    }

    protected void setPotData(ItemStack pot) {
        if (this.hasCustomName()) {
            pot.setDisplayName(this.getCustomName());
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            FireSalamanderEntity child = ModEntityTypes.FIRE_SALAMANDER.create(world);
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
                return true;
            }
        }
        {
            if (heldItem.getItem() == Items.FLOWER_POT && this.isAlive()) {
                playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                heldItem.shrink(1);
                ItemStack itemstack1 = new ItemStack(ItemInit.FIRE_SALAMANDER_POT.get());
                this.setPotData(itemstack1);
                if (!this.world.isRemote) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
                }
                if (heldItem.isEmpty()) {
                    player.setHeldItem(hand, itemstack1);
                } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
                    player.dropItem(itemstack1, false);
                }
                this.remove();
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
            }
            return false;
        }
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.15F, 1.0F);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? size.height * 0.85F : size.height * 0.92F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.FERMENTED_SPIDER_EYE;
    }
}

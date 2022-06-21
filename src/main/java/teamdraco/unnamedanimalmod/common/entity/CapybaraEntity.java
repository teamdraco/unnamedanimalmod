package teamdraco.unnamedanimalmod.common.entity;

import com.google.common.base.Suppliers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import teamdraco.unnamedanimalmod.common.entity.util.ai.CapybaraAnimalAttractionGoal;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
import teamdraco.unnamedanimalmod.init.UAMSounds;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CapybaraEntity extends TamableAnimal implements MenuProvider {
    private static final Supplier<Set<ItemLike>> TEMPT_ITEMS = Suppliers.memoize(() -> {
        Stream<ItemLike> stream = Stream.of(Blocks.MELON, Items.APPLE, Items.SUGAR_CANE, Items.MELON_SLICE, UAMItems.MANGROVE_FRUIT.get());
        return stream.map(ItemLike::asItem).collect(Collectors.toSet());
    });
    private static final EntityDataAccessor<Integer> CHESTS = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    public Container inventory;

    public CapybaraEntity(EntityType<? extends CapybaraEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(TEMPT_ITEMS.get().toArray(new ItemLike[0])), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new CapybaraAnimalAttractionGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CHESTS, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.65f;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Blocks.MELON.asItem();
    }

    protected SoundEvent getAmbientSound() {
        return UAMSounds.CAPYBARA_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UAMSounds.CAPYBARA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UAMSounds.CAPYBARA_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else {
            Entity entity = p_70097_1_.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
            }

            return super.hurt(p_70097_1_, p_70097_2_);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (stack.getItem() == Blocks.CHEST.asItem()) {
                if (inventory == null || inventory.getContainerSize() < 27) {
                    inventory = new SimpleContainer(27);
                    entityData.set(CHESTS, 1);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResult.sidedSuccess(this.level.isClientSide);
                }
                else if (inventory.getContainerSize() < 54) {
                    Container inv = new SimpleContainer(54);
                    for (int i = 0; i < 27; i++) {
                        inv.setItem(i, inventory.getItem(i));
                    }
                    inventory = inv;
                    entityData.set(CHESTS, 2);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResult.sidedSuccess(this.level.isClientSide);
                }
            }
            if (stack.getItem() == Items.STICK) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            else {
                player.openMenu(this);
                return InteractionResult.SUCCESS;
            }
        }
        else if (TEMPT_ITEMS.get().contains(stack.getItem()) && !isTame()) {
            if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level.broadcastEntityEvent(this, (byte) 7);
            }
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            else {
                this.level.broadcastEntityEvent(this, (byte) 6);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        else if (!this.isVehicle() && !player.isSecondaryUseActive() && !this.isBaby() && !isInSittingPose()) {
            boolean flag = this.isFood(player.getItemInHand(hand));
            if (!flag && !this.isVehicle() && !player.isSecondaryUseActive()) {
                if (!this.level.isClientSide) {
                    player.startRiding(this);
                }

                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
        else if (!this.getPassengers().isEmpty()) {
            ejectPassengers();
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public CapybaraEntity getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return UAMEntities.CAPYBARA.get().create(p_241840_1_);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? 0.5F : 0.9F;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(UAMItems.CAPYBARA_SPAWN_EGG.get());
    }

    @Override
    public void tick() {
        super.tick();
        this.floatStrider();
        this.checkInsideBlocks();
        if (getPassengers().isEmpty()) {
            for (Entity e : level.getEntities(this, getBoundingBox().inflate(0.5))) {
                if (e instanceof Mob && e.getBbWidth() <= 0.75f && e.getBbHeight() <= 0.75f && !this.isBaby() && ((Mob) e).getMobType() != MobType.WATER && !isInWater()) {
                    e.startRiding(this);
                }
            }
        } else if (isInWater()) {
            ejectPassengers();
        }
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        return new CapybaraEntity.WaterPathNavigation(this, worldIn);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeControlledByRider() {
        return false;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnData == null) {
            spawnData = new AgeableMobGroupData(1);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnData, dataTag);
    }

    private void floatStrider() {
        if (this.isInWater()) {
            CollisionContext iselectioncontext = CollisionContext.of(this);
            if (iselectioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.onGround = true;
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (inventory != null) {
            final ListTag inv = new ListTag();
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                inv.add(inventory.getItem(i).save(new CompoundTag()));
            }
            compound.put("Inventory", inv);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Inventory")) {
            final ListTag inv = compound.getList("Inventory", 10);
            inventory = new SimpleContainer(inv.size());
            for (int i = 0; i < inv.size(); i++) {
                inventory.setItem(i, ItemStack.of(inv.getCompound(i)));
            }
            entityData.set(CHESTS, inv.size() > 27 ? 2 : 1);
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        if (inventory == null) {
            return null;
        }
        return inventory.getContainerSize() < 54 ? ChestMenu.threeRows(p_createMenu_1_, p_createMenu_2_, inventory) : ChestMenu.sixRows(p_createMenu_1_, p_createMenu_2_, inventory);
    }

    public int getChestCount() {
        return entityData.get(CHESTS);
    }

    static class WaterPathNavigation extends GroundPathNavigation {
        WaterPathNavigation(CapybaraEntity p_i231565_1_, Level p_i231565_2_) {
            super(p_i231565_1_, p_i231565_2_);
        }

        @Override
        protected PathFinder createPathFinder(int p_179679_1_) {
            this.nodeEvaluator = new WalkNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, p_179679_1_);
        }

        @Override
        protected boolean hasValidPathType(BlockPathTypes types) {
            return types == BlockPathTypes.WATER || super.hasValidPathType(types);
        }

        @Override
        public boolean isStableDestination(BlockPos pos) {
            return this.level.getBlockState(pos).is(Blocks.WATER) || super.isStableDestination(pos);
        }
    }
}

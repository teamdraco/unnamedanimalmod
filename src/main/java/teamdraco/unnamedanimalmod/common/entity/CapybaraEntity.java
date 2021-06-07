package teamdraco.unnamedanimalmod.common.entity;

import teamdraco.unnamedanimalmod.common.entity.util.ai.CapybaraAnimalAttractionGoal;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
import teamdraco.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CapybaraEntity extends TameableEntity implements INamedContainerProvider {
    private static final LazyValue<Set<IItemProvider>> TEMPT_ITEMS = new LazyValue<>(() -> {
        Stream<IItemProvider> stream = Stream.of(Blocks.MELON, Items.APPLE, Items.SUGAR_CANE, Items.MELON_SLICE, UAMItems.MANGROVE_FRUIT.get());
        return stream.map(IItemProvider::asItem).collect(Collectors.toSet());
    });
    public IInventory inventory;

    public CapybaraEntity(EntityType<? extends CapybaraEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(TEMPT_ITEMS.get().toArray(new IItemProvider[0])), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(9, new CapybaraAnimalAttractionGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
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

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (stack.getItem() == Blocks.CHEST.asItem()) {
                if (inventory == null || inventory.getContainerSize() < 27) {
                    inventory = new Inventory(27);
                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                }
                else if (inventory.getContainerSize() < 54) {
                    IInventory inv = new Inventory(54);
                    for (int i = 0; i < 27; i++) {
                        inv.setItem(i, inventory.getItem(i));
                    }
                    inventory = inv;
                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                }
            }
            else {
                player.openMenu(this);
                return ActionResultType.SUCCESS;
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
            else {
                this.level.broadcastEntityEvent(this, (byte) 6);
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }
        else if (!this.isVehicle() && !player.isSecondaryUseActive() && !this.isBaby()) {
            boolean flag = this.isFood(player.getItemInHand(hand));
            if (!flag && !this.isVehicle() && !player.isSecondaryUseActive()) {
                if (!this.level.isClientSide) {
                    player.startRiding(this);
                }

                return ActionResultType.sidedSuccess(this.level.isClientSide);
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
    public CapybaraEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.CAPYBARA.get().create(p_241840_1_);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? 0.5F : 0.9F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.CAPYBARA_SPAWN_EGG.get());
    }

    @Override
    public void tick() {
        super.tick();
        this.floatStrider();
        this.checkInsideBlocks();
        if (getPassengers().isEmpty()) {
            for (Entity e : level.getEntities(this, getBoundingBox().inflate(0.5))) {
                if (e instanceof MobEntity && e.getBbWidth() <= 0.75f && e.getBbHeight() <= 0.75f && !this.isBaby() && ((MobEntity) e).getMobType() != CreatureAttribute.WATER) {
                    e.startRiding(this);
                }
            }
        }
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new CapybaraEntity.WaterPathNavigator(this, worldIn);
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
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        if (spawnData == null) {
            spawnData = new AgeableData(1);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnData, dataTag);
    }

    private void floatStrider() {
        if (this.isInWater()) {
            ISelectionContext iselectioncontext = ISelectionContext.of(this);
            if (iselectioncontext.isAbove(FlowingFluidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.onGround = true;
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (inventory != null) {
            final ListNBT inv = new ListNBT();
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                inv.add(inventory.getItem(i).save(new CompoundNBT()));
            }
            compound.put("Inventory", inv);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Inventory")) {
            final ListNBT inv = compound.getList("Inventory", 10);
            inventory = new Inventory(inv.size());
            for (int i = 0; i < inv.size(); i++) {
                inventory.setItem(i, ItemStack.of(inv.getCompound(i)));
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        if (inventory == null) {
            return null;
        }
        return inventory.getContainerSize() < 54 ? ChestContainer.threeRows(p_createMenu_1_, p_createMenu_2_, inventory) : ChestContainer.sixRows(p_createMenu_1_, p_createMenu_2_, inventory);
    }

    static class WaterPathNavigator extends GroundPathNavigator {
        WaterPathNavigator(CapybaraEntity p_i231565_1_, World p_i231565_2_) {
            super(p_i231565_1_, p_i231565_2_);
        }

        protected PathFinder createPathFinder(int p_179679_1_) {
            this.nodeEvaluator = new WalkNodeProcessor();
            return new PathFinder(this.nodeEvaluator, p_179679_1_);
        }

        protected boolean hasValidPathType(PathNodeType p_230287_1_) {
            return p_230287_1_ == PathNodeType.WATER || super.hasValidPathType(p_230287_1_);
        }

        public boolean isStableDestination(BlockPos pos) {
            return this.level.getBlockState(pos).is(Blocks.WATER) || super.isStableDestination(pos);
        }
    }
}

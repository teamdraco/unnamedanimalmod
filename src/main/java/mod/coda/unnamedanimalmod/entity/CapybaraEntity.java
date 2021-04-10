package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.entity.goals.CapybaraAnimalAttractionGoal;
import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import mod.coda.unnamedanimalmod.init.UAMSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
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

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(TEMPT_ITEMS.getValue().toArray(new IItemProvider[0])), false));
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
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 14.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.65f;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
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
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (stack.getItem() == Blocks.CHEST.asItem()) {
                if (inventory == null || inventory.getSizeInventory() < 27) {
                    inventory = new Inventory(27);
                    return ActionResultType.func_233537_a_(this.world.isRemote);
                }
                else if (inventory.getSizeInventory() < 54) {
                    IInventory inv = new Inventory(54);
                    for (int i = 0; i < 27; i++) {
                        inv.setInventorySlotContents(i, inventory.getStackInSlot(i));
                    }
                    inventory = inv;
                    return ActionResultType.func_233537_a_(this.world.isRemote);
                }
            }
            else {
                player.openContainer(this);
                return ActionResultType.SUCCESS;
            }
        }
        else if (TEMPT_ITEMS.getValue().contains(stack.getItem()) && !isTamed()) {
            if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.setTamedBy(player);
                this.navigator.clearPath();
                this.setAttackTarget(null);
                this.func_233687_w_(true);
                this.world.setEntityState(this, (byte) 7);
            }
            else {
                this.world.setEntityState(this, (byte) 6);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        else if (!this.isBeingRidden() && !player.isSecondaryUseActive() && !this.isChild()) {
            boolean flag = this.isBreedingItem(player.getHeldItem(hand));
            if (!flag && !this.isBeingRidden() && !player.isSecondaryUseActive()) {
                if (!this.world.isRemote) {
                    player.startRiding(this);
                }

                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
        }
        else if (!this.getPassengers().isEmpty()) {
            removePassengers();
        }
        return super.func_230254_b_(player, hand);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public CapybaraEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return UAMEntities.CAPYBARA.get().create(p_241840_1_);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? 0.5F : 0.9F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.CAPYBARA_SPAWN_EGG.get());
    }

    @Override
    public void tick() {
        super.tick();
        this.func_234318_eL_();
        this.doBlockCollisions();
        if (getPassengers().isEmpty()) {
            for (Entity e : world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(0.5))) {
                if (e instanceof MobEntity && e.getWidth() <= 0.75f && e.getHeight() <= 0.75f && !this.isChild()) {
                    e.startRiding(this);
                }
            }
        }
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new CapybaraEntity.WaterPathNavigator(this, worldIn);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public boolean canBeSteered() {
        return false;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        if (spawnData == null) {
            spawnData = new AgeableData(1);
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnData, dataTag);
    }

    private void func_234318_eL_() {
        if (this.isInWater()) {
            ISelectionContext iselectioncontext = ISelectionContext.forEntity(this);
            if (iselectioncontext.func_216378_a(FlowingFluidBlock.LAVA_COLLISION_SHAPE, this.getPosition(), true) && !this.world.getFluidState(this.getPosition().up()).isTagged(FluidTags.WATER)) {
                this.onGround = true;
            } else {
                this.setMotion(this.getMotion().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
    }


    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (inventory != null) {
            final ListNBT inv = new ListNBT();
            for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
                inv.add(inventory.getStackInSlot(i).write(new CompoundNBT()));
            }
            compound.put("Inventory", inv);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Inventory")) {
            final ListNBT inv = compound.getList("Inventory", 10);
            inventory = new Inventory(inv.size());
            for (int i = 0; i < inv.size(); i++) {
                inventory.setInventorySlotContents(i, ItemStack.read(inv.getCompound(i)));
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        if (inventory == null) {
            return null;
        }
        return inventory.getSizeInventory() < 54 ? ChestContainer.createGeneric9X3(p_createMenu_1_, p_createMenu_2_, inventory) : ChestContainer.createGeneric9X6(p_createMenu_1_, p_createMenu_2_, inventory);
    }

    static class WaterPathNavigator extends GroundPathNavigator {
        WaterPathNavigator(CapybaraEntity p_i231565_1_, World p_i231565_2_) {
            super(p_i231565_1_, p_i231565_2_);
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        protected boolean func_230287_a_(PathNodeType p_230287_1_) {
            return p_230287_1_ == PathNodeType.WATER || super.func_230287_a_(p_230287_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            return this.world.getBlockState(pos).isIn(Blocks.WATER) || super.canEntityStandOnPos(pos);
        }
    }
}

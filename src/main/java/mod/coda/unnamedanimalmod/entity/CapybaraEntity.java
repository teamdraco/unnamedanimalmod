package mod.coda.unnamedanimalmod.entity;

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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CapybaraEntity extends AnimalEntity {

    public CapybaraEntity(EntityType<? extends CapybaraEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Blocks.MELON, Items.APPLE, Items.SUGAR_CANE, Items.MELON_SLICE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
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
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new CapybaraEntity.WaterPathNavigator(this, worldIn);
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

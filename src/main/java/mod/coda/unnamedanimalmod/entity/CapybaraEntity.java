package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CapybaraEntity extends AnimalEntity {

    public CapybaraEntity(EntityType<? extends CapybaraEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        // this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Blocks.MELON, Items.APPLE, Items.SUGAR_CANE, Items.MELON_SLICE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 14.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9f;
    }

    @Override
    public void travel(Vector3d travelVector) {
        super.travel(travelVector);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Blocks.MELON.asItem();
    }

//    protected SoundEvent getAmbientSound() {
//        return SoundEvents.ENTITY_COW_AMBIENT;
//    }

//    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//        return SoundEvents.ENTITY_COW_HURT;
//    }

//    protected SoundEvent getDeathSound() {
//        return SoundEvents.ENTITY_COW_DEATH;
//    }

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
        return this.isChild() ? sizeIn.height * 0.55F : 1.3F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.CAPYBARA_SPAWN_EGG.get());
    }
}

package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class FlashlightFishEntity extends AbstractGroupFishEntity {
    public FlashlightFishEntity(EntityType<? extends FlashlightFishEntity> p_i50279_1_, World p_i50279_2_) {
        super(p_i50279_1_, p_i50279_2_);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.6D, 1.4D, EntityPredicates.NOT_SPECTATING::test));
        this.goalSelector.addGoal(4, new FlashlightFishEntity.SwimGoal(this));
    }

    public static boolean func_223363_b(EntityType<? extends AbstractFishEntity> type, IWorld worldIn, SpawnReason reason, BlockPos p_223363_3_, Random randomIn) {
        return worldIn.getBlockState(p_223363_3_).isIn(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.up()).isIn(Blocks.WATER) && worldIn.getWorldInfo().getDayTime() > 12000 && worldIn.getWorldInfo().getDayTime() < 24000;
    }

    protected ItemStack getFishBucket() {
        return null;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Override
    public int getMaxGroupSize() {
        return 8;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(UAMItems.FLASHLIGHT_FISH_SPAWN_EGG.get());
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final FlashlightFishEntity fish;

        public SwimGoal(FlashlightFishEntity fish) {
            super(fish, 1.0D, 1);
            this.fish = fish;
        }

        public boolean shouldExecute() {
            return this.fish.func_212800_dy() && super.shouldExecute();
        }
    }
}

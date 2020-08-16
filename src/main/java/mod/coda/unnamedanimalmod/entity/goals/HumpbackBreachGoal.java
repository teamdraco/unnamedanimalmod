package mod.coda.unnamedanimalmod.entity.goals;

import mod.coda.unnamedanimalmod.entity.HumpbackWhaleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class HumpbackBreachGoal extends JumpGoal {
    private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
    private final HumpbackWhaleEntity whaleEntity;
    private final int field_220712_c;
    private boolean field_220713_d;

    public HumpbackBreachGoal(HumpbackWhaleEntity whale, int p_i50329_2_) {
        this.whaleEntity = whale;
        this.field_220712_c = p_i50329_2_;
    }

    public boolean shouldExecute() {
        if (this.whaleEntity.getRNG().nextInt(this.field_220712_c) != 0) {
            return false;
        } else {
            Direction direction = this.whaleEntity.getAdjustedHorizontalFacing();
            int i = direction.getXOffset();
            int j = direction.getZOffset();
            BlockPos blockpos = new BlockPos(this.whaleEntity);

            for(int k : JUMP_DISTANCES) {
                if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.add(dx * scale, 0, dz * scale);
        return this.whaleEntity.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.whaleEntity.world.getBlockState(blockpos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.whaleEntity.world.getBlockState(pos.add(dx * scale, 1, dz * scale)).isAir() && this.whaleEntity.world.getBlockState(pos.add(dx * scale, 2, dz * scale)).isAir();
    }

    public boolean shouldContinueExecuting() {
        double d0 = this.whaleEntity.getMotion().y;
        return (!(d0 * d0 < (double)0.03F) || this.whaleEntity.rotationPitch == 0.0F || !(Math.abs(this.whaleEntity.rotationPitch) < 10.0F) || !this.whaleEntity.isInWater()) && !this.whaleEntity.onGround;
    }

    public boolean isPreemptible() {
        return false;
    }

    public void startExecuting() {
        Direction direction = this.whaleEntity.getAdjustedHorizontalFacing();
        this.whaleEntity.setMotion(this.whaleEntity.getMotion().add((double)direction.getXOffset() * 0.6D, 0.7D, (double)direction.getZOffset() * 0.6D));
        this.whaleEntity.getNavigator().clearPath();
    }

    public void resetTask() {
        this.whaleEntity.rotationPitch = 0.0F;
    }

    public void tick() {
        boolean flag = this.field_220713_d;
        if (!flag) {
            IFluidState ifluidstate = this.whaleEntity.world.getFluidState(new BlockPos(this.whaleEntity));
            this.field_220713_d = ifluidstate.isTagged(FluidTags.WATER);
        }

        if (this.field_220713_d && !flag) {
            this.whaleEntity.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3d vec3d = this.whaleEntity.getMotion();
        if (vec3d.y * vec3d.y < (double)0.03F && this.whaleEntity.rotationPitch != 0.0F) {
            this.whaleEntity.rotationPitch = MathHelper.rotLerp(this.whaleEntity.rotationPitch, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.horizontalMag(vec3d));
            double d1 = Math.signum(-vec3d.y) * Math.acos(d0 / vec3d.length()) * (double)(180F / (float)Math.PI);
            this.whaleEntity.rotationPitch = (float)d1;
        }

    }
}
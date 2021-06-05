package teamdraco.unnamedanimalmod.common.entity.util.ai;

import teamdraco.unnamedanimalmod.common.entity.SouthernRightWhaleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class WhaleBreachGoal extends JumpGoal {
    private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
    private final SouthernRightWhaleEntity whale;
    private final int interval;
    private boolean inWater;

    public WhaleBreachGoal(SouthernRightWhaleEntity whale, int p_i50329_2_) {
        this.whale = whale;
        this.interval = p_i50329_2_;
    }

    public boolean canUse() {
        if (this.whale.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.whale.getMotionDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockpos = this.whale.blockPosition();

            for(int k : JUMP_DISTANCES) {
                if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
        return this.whale.level.getFluidState(blockpos).is(FluidTags.WATER) && !this.whale.level.getBlockState(blockpos).getMaterial().blocksMotion();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.whale.level.getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir() && this.whale.level.getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
    }

    public boolean canContinueToUse() {
        double d0 = this.whale.getDeltaMovement().y;
        return (!(d0 * d0 < (double)0.03F) || this.whale.xRot == 0.0F || !(Math.abs(this.whale.xRot) < 10.0F) || !this.whale.isInWater()) && !this.whale.isOnGround();
    }

    public boolean isInterruptable() {
        return false;
    }

    public void start() {
        Direction direction = this.whale.getMotionDirection();
        this.whale.setDeltaMovement(this.whale.getDeltaMovement().add((double)direction.getStepX() * 0.6D, 0.7D, (double)direction.getStepZ() * 0.6D));
        this.whale.getNavigation().stop();
    }

    public void stop() {
        this.whale.xRot = 0.0F;
    }

    public void tick() {
        boolean flag = this.inWater;
        if (!flag) {
            FluidState fluidstate = this.whale.level.getFluidState(this.whale.blockPosition());
            this.inWater = fluidstate.is(FluidTags.WATER);
        }

        if (this.inWater && !flag) {
            this.whale.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vector3d vector3d = this.whale.getDeltaMovement();
        if (vector3d.y * vector3d.y < (double)0.03F && this.whale.xRot != 0.0F) {
            this.whale.xRot = MathHelper.rotlerp(this.whale.xRot, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.getHorizontalDistanceSqr(vector3d));
            double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * (double)(180F / (float)Math.PI);
            this.whale.xRot = (float)d1;
        }

    }
}
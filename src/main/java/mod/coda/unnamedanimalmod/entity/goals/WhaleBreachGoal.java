package mod.coda.unnamedanimalmod.entity.goals;

import mod.coda.unnamedanimalmod.entity.SouthernRightWhaleEntity;
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
    private final int field_220712_c;
    private boolean inWater;

    public WhaleBreachGoal(SouthernRightWhaleEntity whale, int p_i50329_2_) {
        this.whale = whale;
        this.field_220712_c = p_i50329_2_;
    }

    public boolean shouldExecute() {
        if (this.whale.getRNG().nextInt(this.field_220712_c) != 0) {
            return false;
        } else {
            Direction direction = this.whale.getAdjustedHorizontalFacing();
            int i = direction.getXOffset();
            int j = direction.getZOffset();
            BlockPos blockpos = this.whale.getPosition();

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
        return this.whale.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.whale.world.getBlockState(blockpos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.whale.world.getBlockState(pos.add(dx * scale, 1, dz * scale)).isAir() && this.whale.world.getBlockState(pos.add(dx * scale, 2, dz * scale)).isAir();
    }

    public boolean shouldContinueExecuting() {
        double d0 = this.whale.getMotion().y;
        return (!(d0 * d0 < (double)0.03F) || this.whale.rotationPitch == 0.0F || !(Math.abs(this.whale.rotationPitch) < 10.0F) || !this.whale.isInWater()) && !this.whale.isOnGround();
    }

    public boolean isPreemptible() {
        return false;
    }

    public void startExecuting() {
        Direction direction = this.whale.getAdjustedHorizontalFacing();
        this.whale.setMotion(this.whale.getMotion().add((double)direction.getXOffset() * 0.6D, 0.7D, (double)direction.getZOffset() * 0.6D));
        this.whale.getNavigator().clearPath();
    }

    public void resetTask() {
        this.whale.rotationPitch = 0.0F;
    }

    public void tick() {
        boolean flag = this.inWater;
        if (!flag) {
            FluidState fluidstate = this.whale.world.getFluidState(this.whale.getPosition());
            this.inWater = fluidstate.isTagged(FluidTags.WATER);
        }

        if (this.inWater && !flag) {
            this.whale.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vector3d vector3d = this.whale.getMotion();
        if (vector3d.y * vector3d.y < (double)0.03F && this.whale.rotationPitch != 0.0F) {
            this.whale.rotationPitch = MathHelper.rotLerp(this.whale.rotationPitch, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.horizontalMag(vector3d));
            double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * (double)(180F / (float)Math.PI);
            this.whale.rotationPitch = (float)d1;
        }

    }
}
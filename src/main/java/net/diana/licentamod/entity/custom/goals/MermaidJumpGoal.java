package net.diana.licentamod.entity.custom.goals;

import net.diana.licentamod.entity.custom.MermaidEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class MermaidJumpGoal extends JumpGoal {
    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
    private final MermaidEntity mermaid;
    private final int interval;
    private boolean breached;

    public MermaidJumpGoal(MermaidEntity mermaid, int interval) {
        this.mermaid = mermaid;
        this.interval = reducedTickDelay(interval);
    }

    public boolean canUse() {
        if (this.mermaid.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.mermaid.getMotionDirection();
            int stepX = direction.getStepX();
            int stepZ = direction.getStepZ();
            BlockPos position = this.mermaid.blockPosition();

            for (int step : STEPS_TO_CHECK) {
                if (!this.waterIsClear(position, stepX, stepZ, step) || !this.surfaceIsClear(position, stepX, stepZ, step)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean waterIsClear(BlockPos position, int stepX, int stepZ, int step) {
        BlockPos blockPos = position.offset(stepX * step, 0, stepZ * step);
        return this.mermaid.level.getFluidState(blockPos).is(FluidTags.WATER) && !this.mermaid.level.getBlockState(blockPos).getMaterial().blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos position, int stepX, int stepZ, int step) {
        return this.mermaid.level.getBlockState(position.offset(stepX * step, 1, stepZ * step)).isAir() && this.mermaid.level.getBlockState(position.offset(stepX * step, 2, stepZ * step)).isAir();
    }

    public boolean canContinueToUse() {
        double yMovement = this.mermaid.getDeltaMovement().y;
        return (!this.mermaid.isInWater() || yMovement * yMovement >= 0.029999999329447746) && !this.mermaid.isOnGround();
    }

    public boolean isInterruptable() {
        return false;
    }

    public void start() {
        Direction direction = this.mermaid.getMotionDirection();
        this.mermaid.setDeltaMovement(this.mermaid.getDeltaMovement().add((double)direction.getStepX() * 0.6, 0.7, (double)direction.getStepZ() * 0.6));
        this.mermaid.getNavigation().stop();
    }

    public void stop() {
        this.mermaid.setXRot(0.0F);
    }

    public void tick() {
        boolean wasBreached = this.breached;
        if (!wasBreached) {
            FluidState fluidState = this.mermaid.level.getFluidState(this.mermaid.blockPosition());
            this.breached = fluidState.is(FluidTags.WATER);
        }

//        if (this.breached && !wasBreached) {
//            this.mermaid.playSound(SoundEvents., 1.0F, 1.0F);
//        }

        Vec3 deltaMovement = this.mermaid.getDeltaMovement();
        if (deltaMovement.y * deltaMovement.y < 0.029999999329447746 && this.mermaid.getXRot() != 0.0F) {
            this.mermaid.setXRot(Mth.rotLerp(this.mermaid.getXRot(), 0.0F, 0.2F));
        } else if (deltaMovement.lengthSqr() > 1.0E-7) {
            double horizontalDistance = deltaMovement.horizontalDistance();
            double angle = Math.atan2(-deltaMovement.y, horizontalDistance) * 57.2957763671875;
            this.mermaid.setXRot((float)angle);
        }
    }
}

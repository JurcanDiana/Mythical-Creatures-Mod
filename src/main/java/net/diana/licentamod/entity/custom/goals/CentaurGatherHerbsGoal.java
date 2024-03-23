package net.diana.licentamod.entity.custom.goals;

import net.diana.licentamod.entity.custom.CentaurEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CentaurGatherHerbsGoal extends Goal {
    private final CentaurEntity centaur;
    private final Level world;
    private BlockPos targetHerbBlock = null;
    private static final int SEARCH_RADIUS = 10;
    private static final double MIN_DISTANCE_SQR = 2.0;
    private static final double MOVEMENT_SPEED = 1.0;
    

    public CentaurGatherHerbsGoal(CentaurEntity centaur) {
        this.centaur = centaur;
        this.world = centaur.level;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; ++x) {
            for (int y = -SEARCH_RADIUS; y <= SEARCH_RADIUS; ++y) {
                for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; ++z) {
                    BlockPos blockPos = new BlockPos((int) (centaur.getX() + x), (int) (centaur.getY() + y), (int) (centaur.getZ() + z));
                    BlockState blockState = world.getBlockState(blockPos);

                    if (blockState.is(BlockTags.WART_BLOCKS)
                            || blockState.is(BlockTags.CORAL_BLOCKS)
                            || blockState.is(BlockTags.BAMBOO_PLANTABLE_ON)) {

                        this.targetHerbBlock = blockPos;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetHerbBlock != null && centaur.blockPosition().distSqr(targetHerbBlock) > MIN_DISTANCE_SQR;
    }

    @Override
    public void start() {
        moveTowardsTarget();
    }

    @Override
    public void stop() {
        this.targetHerbBlock = null;
    }

    @Override
    public void tick() {
        if (this.targetHerbBlock != null && centaur.blockPosition().distSqr(targetHerbBlock) > MIN_DISTANCE_SQR) {
            moveTowardsTarget();
        } else if (this.targetHerbBlock != null && centaur.blockPosition().distSqr(targetHerbBlock) <= MIN_DISTANCE_SQR) {
            world.destroyBlock(targetHerbBlock, true, centaur);
            this.targetHerbBlock = null;
        }
    }

    private void moveTowardsTarget() {
        Vec3 targetVec = Vec3.atBottomCenterOf(this.targetHerbBlock);
        this.centaur.getNavigation().moveTo(targetVec.x, targetVec.y, targetVec.z, MOVEMENT_SPEED);
    }
}


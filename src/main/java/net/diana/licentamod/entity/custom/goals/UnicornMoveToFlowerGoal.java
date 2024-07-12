package net.diana.licentamod.entity.custom.goals;

import com.mojang.logging.LogUtils;
import net.diana.licentamod.entity.custom.UnicornEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;
import java.util.EnumSet;

public class UnicornMoveToFlowerGoal extends Goal {
    private final UnicornEntity unicorn;
    private final double speedModifier;
    private BlockPos targetFlower = null;
    private static final Logger LOGGER = LogUtils.getLogger();

    public UnicornMoveToFlowerGoal(UnicornEntity unicorn, double speed) {
        this.unicorn = unicorn;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (unicorn.getRandom().nextInt(100) == 0) {
            findNearestFlower();
            return targetFlower != null;
        }
        return false;
    }

    private void findNearestFlower() {
        BlockPos pos = unicorn.blockPosition();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 2; k++) {
                    mutablePos.set(pos.getX() + i - 5, pos.getY() + k, pos.getZ() + j - 5);
                    if (unicorn.level.getBlockState(mutablePos).is(BlockTags.FLOWERS)) {
                        targetFlower = mutablePos.immutable();
                        return;
                    }
                }
            }
        }

        targetFlower = null;
    }

    @Override
    public void start() {
        LOGGER.info("Moving towards flower at " + targetFlower);
        if (targetFlower != null) {
            unicorn.getNavigation().moveTo(targetFlower.getX(), targetFlower.getY(), targetFlower.getZ(), speedModifier);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !unicorn.getNavigation().isDone() && targetFlower != null && unicorn.level.getBlockState(targetFlower).is(BlockTags.FLOWERS);
    }

    @Override
    public void stop() {
        LOGGER.info("Reached the flower/Goal stopped.");
        targetFlower = null;
        unicorn.getNavigation().stop();
    }
}


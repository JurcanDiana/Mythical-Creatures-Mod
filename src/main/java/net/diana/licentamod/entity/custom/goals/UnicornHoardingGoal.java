package net.diana.licentamod.entity.custom.goals;

import com.mojang.logging.LogUtils;
import net.diana.licentamod.entity.custom.UnicornEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

import java.util.EnumSet;
import java.util.List;

public class UnicornHoardingGoal extends Goal {
    private final UnicornEntity unicorn;
    private UnicornEntity attackedUnicorn;
    private long lastCheckTime = 0;

    private static final int COOLDOWN = 100;

    private static final double SPEED = 1.0D;

    private static final double DISTANCE = 6.0D;

    private static final Logger LOGGER = LogUtils.getLogger();

    public UnicornHoardingGoal(UnicornEntity unicorn) {
        this.unicorn = unicorn;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (unicorn.level.getGameTime() - lastCheckTime < COOLDOWN) {
            return false;
        }

        lastCheckTime = unicorn.level.getGameTime();
        attackedUnicorn = findAttackedUnicorn();

        return attackedUnicorn != null && !attackedUnicorn.equals(unicorn);
    }

    private UnicornEntity findAttackedUnicorn() {
        List<UnicornEntity> nearbyUnicorns =
                unicorn.level.getEntitiesOfClass(UnicornEntity.class,
                        unicorn.getBoundingBox().inflate(30),
                        e -> e != unicorn && e.isAttacked());

        LOGGER.info("UnicornHoardingGoal: Checking for attacked unicorns, found " + nearbyUnicorns.size());
        return nearbyUnicorns.isEmpty() ? null : nearbyUnicorns.get(0);
    }

    @Override
    public void start() {
        LOGGER.info("UnicornHoardingGoal: Moving towards attacked unicorn at " + attackedUnicorn.blockPosition());
        if (attackedUnicorn != null) {
            unicorn.getNavigation().moveTo(attackedUnicorn, SPEED);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return attackedUnicorn != null && attackedUnicorn.isAlive() && unicorn.distanceToSqr(attackedUnicorn) > DISTANCE;
    }

    @Override
    public void stop() {
        LOGGER.info("UnicornHoardingGoal: Stopping");
        attackedUnicorn = null;
        unicorn.getNavigation().stop();
    }
}

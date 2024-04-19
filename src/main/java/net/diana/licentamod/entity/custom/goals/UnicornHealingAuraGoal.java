package net.diana.licentamod.entity.custom.goals;

import com.mojang.logging.LogUtils;
import net.diana.licentamod.entity.custom.UnicornEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import java.util.EnumSet;
import java.util.List;

public class UnicornHealingAuraGoal extends Goal {
    private final UnicornEntity unicorn;
    private long lastUsedTime;
    private static final int COOLDOWN = 100; // 5 seconds
    private static final double RADIUS = 15.0; // 15 blocks radius

    private static final Logger LOGGER = LogUtils.getLogger();

    public UnicornHealingAuraGoal(UnicornEntity unicorn) {
        this.unicorn = unicorn;
        this.lastUsedTime = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE)); // unicorn is able to move while performing this goal
    }

    @Override
    public boolean canUse() {
        long timeSinceLastUse = unicorn.level.getGameTime() - lastUsedTime;
        if (timeSinceLastUse < COOLDOWN) {
            return false;
        }

        List<LivingEntity> nearbyFriends =
                unicorn.level.getEntitiesOfClass(LivingEntity.class,
                        unicorn.getBoundingBox().inflate(RADIUS),
                        this::isFriendlyEntity);

        for (LivingEntity entity : nearbyFriends) {
            if (entity.getHealth() < entity.getMaxHealth()) {
                LOGGER.info("Found injured friendly entity");
                return true;
            }
        }

        return false;
    }

    @Override
    public void start() {
        LOGGER.info("UnicornHealingAuraGoal started");
        Level level = unicorn.level;
        List<Entity> nearbyEntities = level.getEntities(unicorn, unicorn.getBoundingBox().inflate(RADIUS), this::isFriendlyEntity);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity livingEntity) {
                float missingHealth = livingEntity.getMaxHealth() - livingEntity.getHealth();
                livingEntity.heal(Math.min(4.0F, missingHealth / 2.0F));

                removeNegativeEffects(livingEntity);
            }
        }

        lastUsedTime = unicorn.level.getGameTime();
        LOGGER.info("UnicornHealingAuraGoal finished");
    }

    private void removeNegativeEffects(LivingEntity entity) {
        entity.removeEffect(MobEffects.POISON);
        entity.removeEffect(MobEffects.WEAKNESS);
        entity.removeEffect(MobEffects.BLINDNESS);
        entity.removeEffect(MobEffects.DARKNESS);
    }

    private boolean isFriendlyEntity(Entity entity) {
        return entity instanceof Player ||
                entity instanceof AbstractVillager ||
                entity instanceof TamableAnimal ||
                entity.isAlliedTo(unicorn);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}

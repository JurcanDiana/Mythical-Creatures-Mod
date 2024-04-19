package net.diana.licentamod.entity.custom.goals;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.diana.licentamod.entity.custom.CentaurEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class CentaurUseWeaponGoal extends Goal {
    private final CentaurEntity centaur;
    private LivingEntity target;
    private static final double TARGET_SEARCH_RADIUS = 8.0D;
    private static final double TARGET_SEARCH_HEIGHT = 3.0D;
    private static final double MAX_ATTACK_DISTANCE_SQR = 64.0D;
    private static final double APPROACH_DISTANCE_SQR = 36.0D;
    private static final double MOVEMENT_SPEED = 1.0D;
    private static final int ATTACK_INTERVAL = 20;
    private static final Logger LOGGER = LogUtils.getLogger();

    public CentaurUseWeaponGoal(CentaurEntity centaur) {
        this.centaur = centaur;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        List<LivingEntity> list =
                this.centaur.level.getEntitiesOfClass(LivingEntity.class,
                        this.centaur.getBoundingBox().inflate(TARGET_SEARCH_RADIUS, TARGET_SEARCH_HEIGHT, TARGET_SEARCH_RADIUS),
                        entity -> entity instanceof Monster && !(entity instanceof CentaurEntity));

        if (list.isEmpty()) {
            return false;
        }

        list.sort(Comparator.comparingDouble(entity -> entity.distanceToSqr(this.centaur)));

        if (list.isEmpty()) {
            LOGGER.info("No valid targets found for Centaur.");
            return false;
        }

        this.target = list.get(0);
        LOGGER.info("Centaur found a target: " + target);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.target.isAlive() && this.centaur.distanceToSqr(this.target) <= MAX_ATTACK_DISTANCE_SQR;
    }

    @Override
    public void start() {
        this.centaur.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        LOGGER.info("Centaur equipped with an Iron Sword.");
    }

    @Override
    public void stop() {
        this.centaur.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        LOGGER.info("Centaur has unequipped its weapon.");
    }

    @Override
    public void tick() {
        this.centaur.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        double distanceToTargetSqr = this.centaur.distanceToSqr(this.target);

        if (distanceToTargetSqr > APPROACH_DISTANCE_SQR) {
            this.centaur.getNavigation().moveTo(this.target, MOVEMENT_SPEED);
        } else {
            if (this.centaur.tickCount % ATTACK_INTERVAL == 0) {
                LOGGER.info("Centaur is attacking: " + target);

                this.centaur.swing(InteractionHand.MAIN_HAND);
                this.target.hurt(DamageSource.mobAttack(this.centaur), (float) this.centaur.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
        }
    }
}


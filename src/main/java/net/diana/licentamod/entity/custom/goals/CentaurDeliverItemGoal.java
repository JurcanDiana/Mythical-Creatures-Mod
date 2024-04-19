package net.diana.licentamod.entity.custom.goals;

import com.mojang.logging.LogUtils;
import net.diana.licentamod.entity.custom.CentaurEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import org.slf4j.Logger;
import java.util.Random;

public class CentaurDeliverItemGoal extends Goal {
    private final CentaurEntity centaur;
    private static final double DELIVERY_DISTANCE_SQUARE = 4.0;
    private static final int UNBREAKING_ENCHANTMENT_LEVEL = 1;
    private static final int ARROW_QUANTITY = 64;
    private static final double MOVEMENT_SPEED = 1.0;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Random random = new Random();

    public CentaurDeliverItemGoal(CentaurEntity centaur) {
        this.centaur = centaur;
    }

    @Override
    public boolean canUse() {
        return centaur.isReadyToDeliverItems() && centaur.getLastInteractingPlayer() != null;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        centaur.setReadyToDeliverItems(false);
    }

    @Override
    public void tick() {
        Player targetPlayer = centaur.getLastInteractingPlayer();
        if (targetPlayer == null) {
            return;
        }

        if (centaur.distanceToSqr(targetPlayer) < DELIVERY_DISTANCE_SQUARE) {
            if (random.nextInt(5) == 0) {
                deliverItemToPlayer(targetPlayer);
                centaur.setReadyToDeliverItems(false);
            }
        } else {
            centaur.getNavigation().moveTo(targetPlayer, MOVEMENT_SPEED);
        }
    }

    private void deliverItemToPlayer(Player targetPlayer) {
        ItemStack bow = new ItemStack(Items.BOW);
        bow.enchant(Enchantments.UNBREAKING, UNBREAKING_ENCHANTMENT_LEVEL);
        ItemStack arrows = new ItemStack(Items.ARROW, ARROW_QUANTITY);

        boolean wasBowAdded = targetPlayer.getInventory().add(bow);
        boolean wereArrowsAdded = targetPlayer.getInventory().add(arrows);

        if (!wasBowAdded) {
            targetPlayer.drop(bow, false);
        }

        if (!wereArrowsAdded) {
            targetPlayer.drop(arrows, false);
        }

        LOGGER.info("Unbreaking bow and arrows delivered to player: " + targetPlayer.getName().getString());
    }
}
package net.diana.licentamod.entity.custom.goals;

import net.diana.licentamod.entity.custom.MermaidEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import java.util.Random;

public class MermaidDeliverItemGoal extends Goal {
    private final MermaidEntity mermaid;
    private static final double DELIVERY_DISTANCE_SQUARE = 9.0;
    private static final double MOVEMENT_SPEED = 0.5;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Random random = new Random();

    public MermaidDeliverItemGoal(MermaidEntity mermaid) {
        this.mermaid = mermaid;
    }

    @Override
    public boolean canUse() {
        return mermaid.isReadyToDeliverItems() && mermaid.getLastInteractingPlayer() != null;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        mermaid.setReadyToDeliverItems(false);
    }

    @Override
    public void tick() {
        Player targetPlayer = mermaid.getLastInteractingPlayer();
        if (targetPlayer == null) {
            return;
        }

        if (mermaid.distanceToSqr(targetPlayer) < DELIVERY_DISTANCE_SQUARE) {
            if (random.nextInt(5) == 0) {
                deliverItemToPlayer(targetPlayer);
                mermaid.setReadyToDeliverItems(false);
            }
        } else {
            mermaid.getNavigation().moveTo(targetPlayer, MOVEMENT_SPEED);
        }
    }

    private void deliverItemToPlayer(Player targetPlayer) {
        ItemStack tropicalFish = new ItemStack(Items.TROPICAL_FISH_BUCKET);

        boolean wasFishAdded = targetPlayer.getInventory().add(tropicalFish);

        if (!wasFishAdded) {
            targetPlayer.drop(tropicalFish, false);
        }

        LOGGER.info("Bucket of tropical fish delivered to player: " + targetPlayer.getName().getString());
    }
}
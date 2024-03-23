package net.diana.licentamod.entity.custom.goals;

import net.diana.licentamod.entity.custom.MermaidEntity;
import net.diana.licentamod.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import java.util.List;

public class MermaidSingGoal extends Goal {
    private final MermaidEntity mermaid;
    private int singCooldown;
    private static final int SING_COOLDOWN_TIME = 200;
    private static final double EFFECT_RADIUS = 10.0D;
    private static final int CONFUSION_DURATION = 200;
    private static final int CONFUSION_AMPLIFIER = 0;
    private static final float VOLUME = 1.0F;
    private static final float PITCH = 1.0F;

    public MermaidSingGoal(MermaidEntity mermaid) {
        this.mermaid = mermaid;
        this.singCooldown = 0;
    }

    @Override
    public boolean canUse() {
        if (this.singCooldown <= 0) {
            List<Player> list =
                    this.mermaid.level.getEntitiesOfClass(Player.class,
                            this.mermaid.getBoundingBox().inflate(EFFECT_RADIUS),
                            player -> !player.isSpectator());
            return !list.isEmpty();
        }
        return false;
    }

    @Override
    public void start() {
        List<Player> list =
                this.mermaid.level.getEntitiesOfClass(Player.class,
                        this.mermaid.getBoundingBox().inflate(EFFECT_RADIUS),
                        player -> !player.isSpectator());

        for (Player player : list) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, CONFUSION_DURATION, CONFUSION_AMPLIFIER));
        }

        this.mermaid.level.playSound(null,
                this.mermaid.blockPosition(),
                ModSounds.MERMAID_SONG.get(),
                SoundSource.NEUTRAL, VOLUME, PITCH);

        this.singCooldown = SING_COOLDOWN_TIME;
        super.start();
    }

    @Override
    public void stop() {
        this.singCooldown = 0;
    }

    @Override
    public void tick() {
        if (this.singCooldown > 0) {
            this.singCooldown--;
        }
    }
}


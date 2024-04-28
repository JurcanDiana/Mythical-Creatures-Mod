package net.diana.licentamod.entity.custom;

import net.diana.licentamod.entity.custom.goals.UnicornHealingAuraGoal;
import net.diana.licentamod.entity.custom.goals.UnicornHoardingGoal;
import net.diana.licentamod.entity.custom.goals.UnicornMoveToFlowerGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.diana.licentamod.item.ModItems;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class UnicornEntity extends AbstractHorse implements IAnimatable, NeutralMob {

    private AnimationFactory factory = new AnimationFactory(this);
    private boolean isFlying;
    private boolean recentlyAttacked = false;
    private static final int ATTACKED_COOLDOWN = 100; // 5 seconds
    private long lastAttackedTime = 0;

    public UnicornEntity(EntityType<? extends AbstractHorse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return AbstractHorse.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        //this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        //this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE), false));

        // attacc
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Creeper.class, true));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));

        // a mele
        this.goalSelector.addGoal(2, new UnicornMoveToFlowerGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new UnicornHealingAuraGoal(this));
        this.goalSelector.addGoal(0, new UnicornHoardingGoal(this));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.unicorn.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.unicorn.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController()
                    .setAnimation(new AnimationBuilder()
                    .addAnimation("animation.unicorn.attack", false));
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        if (this.isFlying) {
            event.getController()
                    .setAnimation(new AnimationBuilder()
                    .addAnimation("animation.unicorn.fly", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        animationData.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
        animationData.addAnimationController(new AnimationController(this, "flyingController",
                10, this::flyingPredicate));
    }

    public void startFlying() {
        this.isFlying = true;
        System.out.println("Unicorn starts flying. isFlying: " + this.isFlying);
    }

    public void stopFlying() {
        this.isFlying = false;
        System.out.println("Unicorn stops flying. isFlying: " + this.isFlying);
    }

    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == ModItems.UNICORN_FLY_POTION.get()) {
            this.startFlying();
            System.out.println("Interacted with unicorn. Starting to fly.");
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            recentlyAttacked = true;
            lastAttackedTime = this.level.getGameTime();
            System.out.println("Unicorn was attacked. Setting recentlyAttacked to true.");
        }
        return hurt;
    }

    public boolean isAttacked() {
        if (recentlyAttacked && (this.level.getGameTime() - lastAttackedTime) < ATTACKED_COOLDOWN) {
            return true;
        } else {
            recentlyAttacked = false;
            System.out.println("Checking isAttacked: No longer within cooldown or not attacked.");
            return false;
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        
    }

    @Override
    public void startPersistentAngerTimer() {

    }
}

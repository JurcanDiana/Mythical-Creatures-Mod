package net.diana.licentamod.entity.custom;

import net.diana.licentamod.block.ModBlocks;
import net.diana.licentamod.entity.custom.goals.MermaidDeliverItemGoal;
import net.diana.licentamod.entity.custom.goals.MermaidJumpGoal;
import net.diana.licentamod.entity.custom.goals.MermaidSingGoal;
import net.diana.licentamod.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MermaidEntity extends AbstractFish implements IAnimatable {

    private boolean readyToDeliverItems = false;

    private Player lastInteractingPlayer = null;
    private AnimationFactory factory = new AnimationFactory(this);

    public MermaidEntity(EntityType<? extends AbstractFish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return AbstractFish.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new BreathAirGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0, 40));
        this.goalSelector.addGoal(2, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(2, new MermaidJumpGoal(this, 100));
        this.goalSelector.addGoal(1, new MermaidSingGoal(this));
        this.goalSelector.addGoal(0, new MermaidDeliverItemGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == ModItems.SLICE_CAKE.get()) {
            if (!this.level.isClientSide) {
                this.readyToDeliverItems = true;
                this.lastInteractingPlayer = player;
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    public Player getLastInteractingPlayer() {
        return lastInteractingPlayer;
    }

    public boolean isReadyToDeliverItems() {
        return this.readyToDeliverItems;
    }

    public void setReadyToDeliverItems(boolean readyToDeliverItems) {
        this.readyToDeliverItems = readyToDeliverItems;
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }
}

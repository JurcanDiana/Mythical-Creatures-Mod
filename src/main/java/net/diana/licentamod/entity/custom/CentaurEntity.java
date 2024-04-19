package net.diana.licentamod.entity.custom;

import net.diana.licentamod.entity.custom.goals.CentaurDeliverItemGoal;
import net.diana.licentamod.entity.custom.goals.CentaurGatherHerbsGoal;
import net.diana.licentamod.entity.custom.goals.CentaurUseWeaponGoal;
import net.diana.licentamod.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CentaurEntity extends AbstractHorse implements IAnimatable {

    private boolean readyToDeliverItems = false;

    private Player lastInteractingPlayer = null;

    private AnimationFactory factory = new AnimationFactory(this);

    public CentaurEntity(EntityType<? extends AbstractHorse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return AbstractHorse.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .build();
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));

        this.goalSelector.addGoal(0, new CentaurUseWeaponGoal(this));
        this.goalSelector.addGoal(1, new CentaurGatherHerbsGoal(this));
        this.goalSelector.addGoal(2, new CentaurDeliverItemGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == ModItems.CENTAUR_CHARM.get()) {
            if (!this.level.isClientSide) {
                this.readyToDeliverItems = true;
                this.lastInteractingPlayer = player;
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
        return super.mobInteract(player, hand);
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
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

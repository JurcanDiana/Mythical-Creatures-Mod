package net.diana.licentamod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class MermaidCakeBlock extends Block {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 6);

    public MermaidCakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BITES, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && player.canEat(false)) {
            return eatCake(world, pos, state, player);
        }
        return InteractionResult.CONSUME;
    }

    private InteractionResult eatCake(Level world, BlockPos pos, BlockState state, Player player) {
        int bites = state.getValue(BITES);
        if (bites < 6) {
            player.getFoodData().eat(2, 0.1F);
            world.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            world.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 1.0F, 1.0F);
        } else {
            world.removeBlock(pos, false);
        }
        return InteractionResult.SUCCESS;
    }
}

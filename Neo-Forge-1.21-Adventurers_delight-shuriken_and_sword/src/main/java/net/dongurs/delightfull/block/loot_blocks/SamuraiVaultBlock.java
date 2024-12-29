package net.dongurs.delightfull.block.loot_blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SamuraiVaultBlock extends BaseEntityBlock {

    //Shape
    public static final MapCodec<SamuraiVaultBlock> CODEC = simpleCodec(SamuraiVaultBlock::new);
    //Properties
    public static final Property<VaultState> STATE = null;
    //Direction
    public static final DirectionProperty FACING = null;

    //Construcor
    protected SamuraiVaultBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(((BlockState)((BlockState)((BlockState)(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(STATE,VaultState.INACTIVE))))));
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, blockPos, player, interactionHand, hitResult);
    }

    @Override
    protected MapCodec<SamuraiVaultBlock>codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}

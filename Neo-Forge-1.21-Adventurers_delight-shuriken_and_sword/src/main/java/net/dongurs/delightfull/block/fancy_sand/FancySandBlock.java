package net.dongurs.delightfull.block.fancy_sand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;


public class FancySandBlock extends Block {


    public FancySandBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.SAND));
    }

    // Override the canSurvive method to allow placement only on sand blocks
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        // Get the block below the current position

        BlockPos blockBelow = pos.below();
        BlockState blockBelowState = worldIn.getBlockState(blockBelow);


        // Check if the block below is sand (regular sand or red sand)
        return blockBelowState.is(Blocks.SAND) || blockBelowState.is(Blocks.RED_SAND);
    }
}




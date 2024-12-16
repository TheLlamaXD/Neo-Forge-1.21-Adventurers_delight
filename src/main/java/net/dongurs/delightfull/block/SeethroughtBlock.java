package net.dongurs.delightfull.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.SoundType.BAMBOO_WOOD;

public class SeethroughtBlock extends Block {



    public SeethroughtBlock(){
        super(BlockBehaviour.Properties.of().sound(BAMBOO_WOOD).strength(0.5f,0.5f));
    }


    @Override
    public int getLightBlock(BlockState state, BlockGetter getter, BlockPos pos){
        return 5;

    }
}

package net.dongurs.delightfull.block;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.SoundType.*;

public class ModBlocks {


    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AdventurersDelight.MOD_ID);








    public static final DeferredBlock<Block> JADE_BLOCK = registerBlock("jade_block",
            ()-> new Block(BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> JADE_BLOCK_STAIRS = registerBlock("jade_block_stairs",
            ()-> new StairBlock(ModBlocks.JADE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));


    public static final DeferredBlock<Block> JADE_BLOCK_SLAB = registerBlock("jade_block_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));






    public static final DeferredBlock<Block> PAPER_WALL_BLOCK = registerBlock("paper_wall_block",
            ()-> new Block(BlockBehaviour.Properties.of().sound(BAMBOO_WOOD).strength(1f,0.5f).requiresCorrectToolForDrops()));


    public static final DeferredBlock<Block> THIN_PAPER_WALL_BLOCK = registerBlock("thin_paper_wall_block",
            ()-> new IronBarsBlock(BlockBehaviour.Properties.of().sound(BAMBOO_WOOD).strength(1f,0.5f).requiresCorrectToolForDrops()));










    public static final DeferredBlock<Block> JADE_POLISHED_BLOCK = registerBlock("jade_polished_block",
            ()-> new Block(BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));


    public static final DeferredBlock<Block> JADE_POLISHED_BLOCK_STAIRS = registerBlock("jade_polished_block_stairs",
            ()-> new StairBlock(ModBlocks.JADE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> JADE_POLISHED_BLOCK_SLAB = registerBlock("jade_polished_block_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));






    public static final DeferredBlock<Block> JADE_TILES_BLOCK = registerBlock("jade_tiles_block",
            ()-> new Block(BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> JADE_TILES_BLOCK_STAIRS = registerBlock("jade_tiles_block_stairs",
            ()-> new StairBlock(ModBlocks.JADE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> JADE_TILES_BLOCK_SLAB = registerBlock("jade_tiles_block_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.of().sound(NETHER_BRICKS).strength(4f,4f).requiresCorrectToolForDrops()));











    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties()));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);

    }
}

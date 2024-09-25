package net.dongurs.delightfull.item;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdventurersDelight.MOD_ID);


    public static final Supplier<CreativeModeTab> ADVENTURERS_DELIGHT_TAB = CREATIVE_MODE_TABS.register("adventurers_delight_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.delightfull.adventurers_delight_tab"))
                    .icon(()-> new ItemStack(ModItems.JADE_GEM.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.JADE_BLOCK);
                        pOutput.accept(ModBlocks.JADE_BLOCK_STAIRS);
                        pOutput.accept(ModBlocks.JADE_BLOCK_SLAB);
                        pOutput.accept(ModBlocks.JADE_TILES_BLOCK);
                        pOutput.accept(ModBlocks.JADE_TILES_BLOCK_STAIRS);
                        pOutput.accept(ModBlocks.JADE_TILES_BLOCK_SLAB);
                        pOutput.accept(ModBlocks.JADE_POLISHED_BLOCK);
                        pOutput.accept(ModBlocks.JADE_POLISHED_BLOCK_STAIRS);
                        pOutput.accept(ModBlocks.JADE_POLISHED_BLOCK_SLAB);
                        pOutput.accept(ModBlocks.PAPER_WALL_BLOCK);
                        pOutput.accept(ModBlocks.THIN_PAPER_WALL_BLOCK);
                        pOutput.accept(ModItems.JADE_GEM);
                        pOutput.accept(ModItems.SPIRIT_SPAWN_EGG);
                    })

                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}



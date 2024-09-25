package net.dongurs.delightfull.item;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.math.IEEE754rUtils;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AdventurersDelight.MOD_ID);


    public static final DeferredItem<Item> JADE_GEM = ITEMS.registerSimpleItem("jade_gem");


    public static final DeferredItem<Item> SPIRIT_SPAWN_EGG = ITEMS.register("spirit_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SPIRIT,0x333d3e,0xc3fdff,
            new Item.Properties()));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

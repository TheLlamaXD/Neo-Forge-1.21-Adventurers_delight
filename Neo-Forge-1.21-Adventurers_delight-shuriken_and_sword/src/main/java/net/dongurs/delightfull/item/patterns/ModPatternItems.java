package net.dongurs.delightfull.item.patterns;

import net.dongurs.delightfull.AdventurersDelight;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class ModPatternItems {
    public static final DeferredItem<Item> SACREFICE_PATTERN_ITEM;


    static {
        SACREFICE_PATTERN_ITEM = registerItem("sacrefice_banner_pattern",
                ()-> new BannerPatternItem(TagRegistry.SACREFICE_BANNER_PATTERN,new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    }




    public static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> item) {
        return AdventurersDelight.MOD_PATTERN_ITEMS.register(name, item);
    }

    public static void patterns()
    {}
}

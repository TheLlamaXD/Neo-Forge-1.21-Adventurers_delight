package net.dongurs.delightfull.item;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.entity.ModEntities;
import net.dongurs.delightfull.item.custom.ModToolTiers;
import net.dongurs.delightfull.item.shuriken.ThrowableShurikenItem;
import net.dongurs.delightfull.item.sword.JadeSword;
import net.dongurs.delightfull.item.sword.SwordItemWithAttackReach;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AdventurersDelight.MOD_ID);


    public static final DeferredItem<Item> JADE_GEM = ITEMS.registerSimpleItem("jade_gem");

    public static final DeferredItem<Item> SHADE_HORN = ITEMS.registerSimpleItem("shade_horn");

    public static final DeferredItem<Item> SHURIKEN_THROWABLE = ITEMS.register("shuriken_throwable", ThrowableShurikenItem::new);










    public static final DeferredItem<Item> SAMURAI_SMITHING_TEMPLATE = ITEMS.register("samurai_smithing_template",
            ()-> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(AdventurersDelight.MOD_ID,"delightfull")));








    public static final DeferredItem<Item> JADE_SWORD = ITEMS.register("jade_sword",
            ()-> new SwordItemWithAttackReach(ModToolTiers.JADE,new Item.Properties().attributes(SwordItemWithAttackReach.createAttributes(ModToolTiers.JADE,
                    3f,-2.2f,1))));





    //public static final DeferredItem<Item> JADE_SWORD = ITEMS.register("jade_sword", JadeSword::new);






    public static final DeferredItem<Item> SPIRIT_SPAWN_EGG = ITEMS.register("spirit_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SPIRIT,0x333d3e,0xc3fdff,
            new Item.Properties()));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

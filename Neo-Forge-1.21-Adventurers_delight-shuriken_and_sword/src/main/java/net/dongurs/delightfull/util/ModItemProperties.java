package net.dongurs.delightfull.util;


import net.dongurs.delightfull.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;


public class ModItemProperties {

    public static void addCustomItemProperties(){
        makeShield(ModItems.JADE_SWORD.get());
    }

    public static void makeShield(Item item){
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("blocking"), (stack, level, player, number) -> player != null && player.isUsingItem() && player.getUseItem() == stack ? 1.0F : 0.0F);
    }


}

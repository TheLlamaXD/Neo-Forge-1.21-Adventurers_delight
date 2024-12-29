package net.dongurs.delightfull.event;


import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.item.ModItems;
import net.dongurs.delightfull.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = AdventurersDelight.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {



    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event){
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, ModItems.SHADE_HORN.get(), ModPotions.HAUNTED_POTION);
        builder.addMix(ModPotions.HAUNTED_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_HAUNTED_POTION);

    }




}

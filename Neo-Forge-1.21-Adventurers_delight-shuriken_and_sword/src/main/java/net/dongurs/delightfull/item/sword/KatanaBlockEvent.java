package net.dongurs.delightfull.item.sword;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;


@EventBusSubscriber(modid = AdventurersDelight.MOD_ID,bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class KatanaBlockEvent {

    public static float getReduction(){
        return KatanaSword.getDamagePercentReduction();
    }


    @SubscribeEvent
    public static void onKatanaBlockEvent(LivingShieldBlockEvent shieldBlockEvent){


        if (shieldBlockEvent.getEntity().getMainHandItem().getItem()== ModItems.JADE_SWORD.get()
                || shieldBlockEvent.getEntity().getOffhandItem().getItem()== ModItems.JADE_SWORD.get()){
            if (shieldBlockEvent.getEntity().getTicksUsingItem() > 20){

            }
            if (getReduction() == 100){
                shieldBlockEvent.setBlockedDamage(shieldBlockEvent.getOriginalBlockedDamage());
            }else  {
                shieldBlockEvent.setBlockedDamage(shieldBlockEvent.getOriginalBlockedDamage()*0.35f);
            }
        }
    }

}

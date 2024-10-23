package net.dongurs.delightfull.event;


import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.entity.ModEntities;
import net.dongurs.delightfull.entity.client.ModModelLayers;
import net.dongurs.delightfull.entity.client.SamuraiSpiritModel;
import net.dongurs.delightfull.entity.custom.SamuraiSpiritEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = AdventurersDelight.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {


   @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.SPIRIT, SamuraiSpiritModel::createBodyLayer);
    }


    @SubscribeEvent
    public static void registerAtrributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.SPIRIT.get(), SamuraiSpiritEntity.createAttributes().build());
    }

}

package net.dongurs.delightfull.component;


import net.dongurs.delightfull.AdventurersDelight;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponentTypes {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(AdventurersDelight.MOD_ID);





    public static void register(IEventBus eventBus){
        DATA_COMPONENT_TYPES.register(eventBus);
    }

}

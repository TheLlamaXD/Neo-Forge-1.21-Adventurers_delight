package net.dongurs.delightfull.potion;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.effects.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, AdventurersDelight.MOD_ID);




    public static final Holder<Potion> HAUNTED_POTION = POTIONS.register("haunted_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HAUNTED_EFFECT,800,0)));

    public static final Holder<Potion> STRONG_HAUNTED_POTION = POTIONS.register("strong_haunted_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HAUNTED_EFFECT,500,1)));



    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);

    }

}

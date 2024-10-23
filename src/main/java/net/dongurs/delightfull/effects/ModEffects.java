package net.dongurs.delightfull.effects;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.procedures.HauntedEffectExpiresProcedure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.registries.DeferredRegister;



@EventBusSubscriber
public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AdventurersDelight.MOD_ID);


    public static final Holder<MobEffect> HAUNTED_EFFECT = MOB_EFFECTS.register("haunted",
            ()-> new HauntedMobEffect());

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null) {
            expireEffects(event.getEntity(), effectInstance);
        }
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null) {
            expireEffects(event.getEntity(), effectInstance);
        }
    }

    private static void expireEffects(Entity entity, MobEffectInstance effectInstance) {
        if (effectInstance.getEffect().is(HAUNTED_EFFECT)) {
            HauntedEffectExpiresProcedure.execute(entity);
        }
    }


}

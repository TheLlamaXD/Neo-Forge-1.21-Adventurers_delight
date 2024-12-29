package net.dongurs.delightfull.effects;

import net.dongurs.delightfull.AdventurersDelight;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;




public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AdventurersDelight.MOD_ID);


    public static final Holder<MobEffect> HAUNTED_EFFECT = MOB_EFFECTS.register("haunted",
            ()-> new HauntedMobEffect(MobEffectCategory.HARMFUL,0x313c50)
                    .addAttributeModifier(Attributes.ATTACK_SPEED,
                    ResourceLocation.fromNamespaceAndPath(AdventurersDelight.MOD_ID,"haunted"),-0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }








}

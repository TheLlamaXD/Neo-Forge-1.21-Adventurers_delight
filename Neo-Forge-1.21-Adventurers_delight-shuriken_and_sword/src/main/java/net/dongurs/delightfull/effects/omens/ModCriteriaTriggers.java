package net.dongurs.delightfull.effects.omens;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModCriteriaTriggers {

    public static final PlayerTrigger SPIRITUAL_OMEN;

    public ModCriteriaTriggers(){
    }

    public static <T extends CriterionTrigger<?>> T register(String name, T trigger) {
        return (T) Registry.register(BuiltInRegistries.TRIGGER_TYPES, name, trigger);
    }

    static {
        SPIRITUAL_OMEN = (PlayerTrigger)register("spiritual_omen_trigger", new PlayerTrigger());
    }



}

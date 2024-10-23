package net.dongurs.delightfull.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.EntityTypeTags;

public class HauntedEffectExpiresProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        if (entity.getType().is(EntityTypeTags.UNDEAD)) {
            if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
                _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((entity.getPersistentData().getDouble("baseStreangth")));
            if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
                _livingEntity4.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue((entity.getPersistentData().getDouble("baseDefense")));
        } else {
            if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
                _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((entity.getPersistentData().getDouble("baseAttack")));
        }
    }
}

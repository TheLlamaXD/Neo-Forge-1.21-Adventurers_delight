package net.dongurs.delightfull.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;

public class HauntedEffect extends MobEffect {

    public HauntedEffect(MobEffectCategory category, int color) {
        super(category, color);
        // Apply the attribute modifier only when the entity is a Zombie in applyEffectTick.
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath("adventurersdelight", "haunted"),
                1.5f,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // Check if the affected entity is a Zombie
        if (entity instanceof Zombie) {
            // If the entity is a Zombie, apply the effects, like increasing attack damage
            super.applyEffectTick(entity, amplifier); // This will apply the effect.
        }
        return false;
    }

}

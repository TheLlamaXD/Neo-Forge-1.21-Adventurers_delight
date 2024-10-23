package net.dongurs.delightfull.effects;

import net.dongurs.delightfull.procedures.HauntedActiveTickConditionProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;




public class HauntedMobEffect extends MobEffect {
    public HauntedMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        HauntedActiveTickConditionProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
    }


}


package net.dongurs.delightfull.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class HauntedActiveTickConditionProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (entity.getType().is(EntityTypeTags.UNDEAD)) {
            if (!world.isClientSide()) {
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.trial_spawner.detect_player")), SoundSource.NEUTRAL, 1, (float) 0.1);
                    } else {
                        _level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.trial_spawner.detect_player")), SoundSource.NEUTRAL, 1, (float) 0.1, false);
                    }
                }
            }
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, x, (y + 0.5), z, 20, 0.2, 0.5, 0.2, 0.05);
            entity.getPersistentData().putDouble("baseStreangth",
                    (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0));
            entity.getPersistentData().putDouble("baseDefense", (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity6.getAttribute(Attributes.ARMOR).getBaseValue() : 0));
            if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
                _livingEntity9.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue((entity.getPersistentData().getDouble("baseDefense") + 1.5));
            if (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
                _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((entity.getPersistentData().getDouble("baseStreangth") + 5));
        } else {
            if (!world.isClientSide()) {
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.beacon.deactivate")), SoundSource.NEUTRAL, 1, (float) 0.1);
                    } else {
                        _level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.beacon.deactivate")), SoundSource.NEUTRAL, 1, (float) 0.1, false);
                    }
                }
            }
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS, x, (y + 0.5), z, 20, 0.2, 0.5, 0.2, 0.05);
            entity.getPersistentData().putDouble("baseAttack",
                    (entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0));
            if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
                _livingEntity18.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((entity.getPersistentData().getDouble("baseAttack") - 3));
        }
    }
}


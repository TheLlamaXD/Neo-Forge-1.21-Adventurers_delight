
package net.dongurs.delightfull.entity.custom;

import net.dongurs.delightfull.entity.ModEntities;
import net.dongurs.delightfull.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;


import javax.annotation.Nullable;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ShurikenProjectileEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.SHURIKEN_THROWABLE.get());
    private int knockback = 0;

    public ShurikenProjectileEntity(EntityType<? extends ShurikenProjectileEntity> type, Level world) {
        super(type, world);
    }

    public ShurikenProjectileEntity(EntityType<? extends ShurikenProjectileEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
    }

    public ShurikenProjectileEntity(EntityType<? extends ShurikenProjectileEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.SHURIKEN_THROWABLE.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }


    public boolean isInGround(ShurikenProjectileEntity entity){
        if (entity.inGround){
             return true;
        }else {
            return false;
        }
    }


    @Override
    protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
        if (knockback > 0.0) {
            double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
            if (vec3.lengthSqr() > 0.0) {
                livingEntity.push(vec3.x, 0.1, vec3.z);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);

        if (!this.level().isClientSide) {
            // Play the trident landing sound once when hitting a block
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.TRIDENT_HIT_GROUND, // Trident landing sound
                    SoundSource.PLAYERS, 1.0F, 0.75F);

        }
    }

    public static ShurikenProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return shoot(world, entity, source, 1f, 4, 1);
    }

    public static ShurikenProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return shoot(world, entity, source, pullingPower * 1f, 4, 1);
    }

    public static ShurikenProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        ShurikenProjectileEntity entityarrow = new ShurikenProjectileEntity(ModEntities.SHURIKEN_PROJECTILE_ENTITY.get(), entity, world, null);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.trident.throw")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static ShurikenProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        ShurikenProjectileEntity entityarrow = new ShurikenProjectileEntity(ModEntities.SHURIKEN_PROJECTILE_ENTITY.get(), entity, entity.level(), null);
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(4);
        entityarrow.setKnockback(1);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.trident.throw")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}

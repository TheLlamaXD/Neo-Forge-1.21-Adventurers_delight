package net.dongurs.delightfull.entity.custom;

import net.dongurs.delightfull.entity.ModEntities;
import net.dongurs.delightfull.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


public class ShurikenProjectileEntity extends AbstractArrow implements ItemSupplier {
    public final AnimationState flyState = new AnimationState();
    private int flyAnimationTimeout = 0;




    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.SHURIKEN_THROWABLE.get());
    private int knockback = 0;

    public ShurikenProjectileEntity(EntityType<? extends ShurikenProjectileEntity> type, Level world) {
        super(type, world);
        this.pickup = Pickup.ALLOWED; // Enable pickup by players
    }

    public ShurikenProjectileEntity(EntityType<? extends ShurikenProjectileEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
        this.pickup = Pickup.ALLOWED; // Enable pickup by players
    }



    private void setupAnimationStates() {

        if (this.flyAnimationTimeout <= 0) {
            this.flyAnimationTimeout = 10;
            this.flyState.start(this.tickCount);
        } else {
            --this.flyAnimationTimeout;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.JADE_SWORD.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
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

    public boolean inGround() {
        return this.inGround;
    }

    protected void setPickupItemStack(ItemStack pickupItemStack) {
        super.setPickupItemStack(pickupItemStack);

    }


    //private boolean hasPlayedLandingSound = false; // Track if landing sound has been played



    @Override
    public void tick() {
        super.tick();


        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
        if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte)0);
            this.setPickupItemStack(new ItemStack(Items.ARROW));
        }

    }

    /*
    public void tick() {
        super.tick();

        // If the projectile is in the ground
        if (this.inGround) {
            this.setDeltaMovement(Vec3.ZERO); // Freeze movement while in ground

            // Detect nearby players to allow pickup
            if (!this.level().isClientSide) { // Ensure this is only done server-side
                this.pickupOnCollision();
            }
        } else {
            // Apply gravity if not in the ground
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.05, 0.0)); // Gravity-like behavior
        }
    }

     */

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




    /*
    private void pickupOnCollision() {
        // Get the closest player within a 2 block radius
        LivingEntity closestPlayer = this.level().getNearestPlayer(this, 0.5);
        if (closestPlayer instanceof Player player) {
            // Check if player can pick up the item
            if (player.isAlive() && !player.getInventory().add(new ItemStack(net.minecraft.world.item.Items.ARROW))) {
                return; // If the player cannot pick up an arrow, don't remove the projectile
            }

            // Play pickup sound
            this.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_PICKUP, // Predefined sound for item pickup
                    SoundSource.PLAYERS, 1.0F, 1.0F);

            // Remove the shuriken from the world after being picked up
            this.discard();
        }
    }

     */

    /*
    @Override
    public void playerTouch(Player player) {
        if (this.pickup == Pickup.ALLOWED && !this.level().isClientSide && (this.inGround || this.isNoPhysics())) {
            if (player.getInventory().add(this.getItem())) { // Add item to player inventory
                this.discard(); // Remove entity after being picked up

                // Play pickup sound
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_PICKUP, // Use predefined pickup sound
                        SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

     */

    public static ShurikenProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return shoot(world, entity, source, pullingPower * 1f, 4, 0);
    }

    public static ShurikenProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        ShurikenProjectileEntity entityarrow = new ShurikenProjectileEntity(ModEntities.SHURIKEN.get(), entity, world, null);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        entityarrow.pickup = Pickup.ALLOWED; // Ensure pickup is allowed
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.trident.throw")),
                SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }





}

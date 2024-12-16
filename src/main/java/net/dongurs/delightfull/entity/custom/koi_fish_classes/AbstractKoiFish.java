//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.dongurs.delightfull.entity.custom.koi_fish_classes;

import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;

import net.dongurs.delightfull.entity.client.koi.KoiVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class AbstractKoiFish extends WaterAnimal implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET;
    static final TargetingConditions SWIM_WITH_PLAYER_TARGETING;
    public static final int COOLDOWN_TICKS = 500;
    public int cooldownState = 0;



    public AbstractKoiFish(EntityType<? extends AbstractKoiFish> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FishMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    public int getMaxSpawnClusterSize() {
        return 8;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
    }

    public boolean fromBucket() {
        return (Boolean)this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }




    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(5, new KoiSwimWithPlayerGoal(this,4));
        GoalSelector var10000 = this.goalSelector;
        Predicate var10009 = EntitySelector.NO_SPECTATORS;
        Objects.requireNonNull(var10009);
        var10000.addGoal(2, new AvoidEntityGoal(this, Player.class, 8.0F, 1.6, 1.4, var10009::test));
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
    }

    static {

        SWIM_WITH_PLAYER_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();

    }

    static class KoiSwimWithPlayerGoal extends Goal{

        private final AbstractKoiFish koiFish;
        private final double speedModifier;
        @Nullable
        private Player player;

        KoiSwimWithPlayerGoal(AbstractKoiFish koiFish, double speedModifier){
            this.koiFish = koiFish;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            this.player = this.koiFish.level().getNearestPlayer(AbstractKoiFish.SWIM_WITH_PLAYER_TARGETING, this.koiFish);
            return this.player == null ? false : this.player.isSwimming() && this.koiFish.getTarget() != this.player;
        }

        public boolean canContinueToUse() {
            return this.player != null && this.player.isSwimming() && this.koiFish.distanceToSqr(this.player) < 256.0;
        }

        public void start() {
            this.player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100), this.koiFish);
        }

        public void stop() {
            this.player = null;
            this.koiFish.getNavigation().stop();
        }

        public void tick() {
            this.koiFish.getLookControl().setLookAt(this.player, (float)(this.koiFish.getMaxHeadYRot() + 20), (float)this.koiFish.getMaxHeadXRot());

            // Handle movement based on distance to player
            if (this.koiFish.distanceToSqr(this.player) < 6.25) {
                this.koiFish.getNavigation().stop();
            } else {
                this.koiFish.getNavigation().moveTo(this.player, this.speedModifier);
            }

            // Apply the effect with a cooldown
            if (this.player.isSwimming() && koiFish.cooldownState <= 0) {
                if (this.player.level().random.nextInt(6) == 0) {
                    this.player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100), this.koiFish);
                    koiFish.cooldownState = COOLDOWN_TICKS; // Reset the cooldown
                }
            }

            // Decrease the cooldown ticks
            if (koiFish.cooldownState > 0) {
                koiFish.cooldownState--;
            }
        }



    }


    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }

    }

    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.makeSound(this.getFlopSound());
        }

        super.aiStep();
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        return (InteractionResult)Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
    }

    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    protected boolean canRandomSwim() {
        return true;
    }

    protected abstract SoundEvent getFlopSound();

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
    }

    static {
        FROM_BUCKET = SynchedEntityData.defineId(AbstractKoiFish.class, EntityDataSerializers.BOOLEAN);
    }

    static class FishMoveControl extends MoveControl {
        private final AbstractKoiFish fish;

        FishMoveControl(AbstractKoiFish fish) {
            super(fish);
            this.fish = fish;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
                float f = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), f));
                double d0 = this.wantedX - this.fish.getX();
                double d1 = this.wantedY - this.fish.getY();
                double d2 = this.wantedZ - this.fish.getZ();
                if (d1 != 0.0) {
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, (double)this.fish.getSpeed() * (d1 / d3) * 0.1, 0.0));
                }

                if (d0 != 0.0 || d2 != 0.0) {
                    float f1 = (float)(Mth.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F;
                    this.fish.setYRot(this.rotlerp(this.fish.getYRot(), f1, 90.0F));
                    this.fish.yBodyRot = this.fish.getYRot();
                }
            } else {
                this.fish.setSpeed(0.0F);
            }

        }
    }

    static class FishSwimGoal extends RandomSwimmingGoal {
        private final AbstractKoiFish fish;

        public FishSwimGoal(AbstractKoiFish fish) {
            super(fish, 1.0, 40);
            this.fish = fish;
        }

        public boolean canUse() {
            return this.fish.canRandomSwim() && super.canUse();
        }
    }

}

package net.dongurs.delightfull.entity.custom;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidType;


public class KoiEntity extends WaterAnimal {

    public static AnimationState idleAnimationState = new AnimationState();
    public static AnimationState wobbleAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int wobbleAnimationTimeout = 0;

    protected boolean canRandomSwim() {
        return true;
    }







    public KoiEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 1;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Level world = this.level();
        Entity entity = this;
        return false;
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new WaterBoundPathNavigation(this,world);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Level world = this.level();
        Entity entity = this;
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this,1,2));
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


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED,1)
                .add(Attributes.FOLLOW_RANGE,8);

    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.wobbleAnimationTimeout <= 0) {
            this.wobbleAnimationTimeout = 20;
            this.wobbleAnimationState.start(this.tickCount);
        } else {
            --this.wobbleAnimationTimeout;
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }


}

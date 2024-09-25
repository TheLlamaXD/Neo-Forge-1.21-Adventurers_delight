package net.dongurs.delightfull.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;
import java.util.*;


public class SamuraiSpiritEntity extends FlyingMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int flyingAnimationTimeout = 0;
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID;
    @Nullable
    private BlockPos boundOrigin;


    public SamuraiSpiritEntity(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SamuraiSpiritMoveControl(this);  // Custom movement control
        this.noPhysics = true;
        this.xpReward = 4;
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        super.move(type, pos);
        this.checkInsideBlocks();
    }



    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0,   new FloatGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, false));
        this.goalSelector.addGoal(10,  new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(9,   new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(2,   new SamuraiSpiritRandomMoveGoal());
        this.goalSelector.addGoal(1,   new SamuraiSpiritChargeAttackGoal());
        this.goalSelector.addGoal(1,   new ApplyStrengthToNearbyZombiesGoal(this,20));
       // this.goalSelector.addGoal(6, new ApplyWeaknessToNearbyPlayerGoal(this ,20));




    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FLYING_SPEED, 0.6)
                .add(Attributes.FOLLOW_RANGE,32);

    }


    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte)0);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(compound.getInt("BoundX"), compound.getInt("BoundY"), compound.getInt("BoundZ"));
        }


    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.boundOrigin != null) {
            compound.putInt("BoundX", this.boundOrigin.getX());
            compound.putInt("BoundY", this.boundOrigin.getY());
            compound.putInt("BoundZ", this.boundOrigin.getZ());
        }

    }

    private BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public void setBoundOrigin(@Nullable BlockPos boundOrigin) {
        this.boundOrigin = boundOrigin;
    }


    private void setSamuraiSpiritFlag(int mask, boolean value) {
        int i = (Byte)this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            i |= mask;
        } else {
            i &= ~mask;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
    }

    private boolean getSamuraiSpiritFlag(int mask) {
        int i = (Byte)this.entityData.get(DATA_FLAGS_ID);
        return (i & mask) != 0;
    }



    public boolean isCharging() {
        return this.getSamuraiSpiritFlag(1);
    }

    public void setIsCharging(boolean charging) {
        this.setSamuraiSpiritFlag(1, charging);
    }






    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.flyingAnimationTimeout <= 0) {
            this.flyingAnimationTimeout = 30;
            this.flyingAnimationState.start(this.tickCount);
        } else {
            --this.flyingAnimationTimeout;
        }
    }


    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.setNoGravity(true);
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }


    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.VEX_HURT;
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(SamuraiSpiritEntity.class, EntityDataSerializers.BYTE);
    }









    class SamuraiSpiritMoveControl extends MoveControl {
        public SamuraiSpiritMoveControl(SamuraiSpiritEntity samuraiSpiritEntity) {
            super(samuraiSpiritEntity);
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - SamuraiSpiritEntity.this.getX(), this.wantedY - SamuraiSpiritEntity.this.getY(), this.wantedZ - SamuraiSpiritEntity.this.getZ());
                double d0 = vec3.length();
                if (d0 < SamuraiSpiritEntity.this.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    SamuraiSpiritEntity.this.setDeltaMovement(SamuraiSpiritEntity.this.getDeltaMovement().scale(0.5));
                } else {
                    SamuraiSpiritEntity.this.setDeltaMovement(SamuraiSpiritEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d0)));
                    if (SamuraiSpiritEntity.this.getTarget() == null) {
                        Vec3 vec31 = SamuraiSpiritEntity.this.getDeltaMovement();
                        SamuraiSpiritEntity.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * 57.295776F);
                        SamuraiSpiritEntity.this.yBodyRot = SamuraiSpiritEntity.this.getYRot();
                    } else {
                        double d2 = SamuraiSpiritEntity.this.getTarget().getX() - SamuraiSpiritEntity.this.getX();
                        double d1 = SamuraiSpiritEntity.this.getTarget().getZ() - SamuraiSpiritEntity.this.getZ();
                        SamuraiSpiritEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * 57.295776F);
                        SamuraiSpiritEntity.this.yBodyRot = SamuraiSpiritEntity.this.getYRot();
                    }
                }
            }

        }
    }





    class SamuraiSpiritRandomMoveGoal extends Goal{
        public SamuraiSpiritRandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !SamuraiSpiritEntity.this.getMoveControl().hasWanted() && SamuraiSpiritEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }



        public void tick() {
            BlockPos blockpos = SamuraiSpiritEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = SamuraiSpiritEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(SamuraiSpiritEntity.this.random.nextInt(15) - 7, SamuraiSpiritEntity.this.random.nextInt(11) - 5, SamuraiSpiritEntity.this.random.nextInt(15) - 7);
                if (SamuraiSpiritEntity.this.level().isEmptyBlock(blockpos1)) {
                    SamuraiSpiritEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5, (double)blockpos1.getY() + 0.5, (double)blockpos1.getZ() + 0.5, 0.25);
                    if (SamuraiSpiritEntity.this.getTarget() == null) {
                        SamuraiSpiritEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5, (double)blockpos1.getY() + 0.5, (double)blockpos1.getZ() + 0.5, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }

    public class ApplyStrengthToNearbyZombiesGoal extends Goal {
        private final Mob mob;
        private final double detectionRange;
        private static final Random random = new Random();
        private int cooldownTicks;

        // Map to keep track of particles for each zombie
        private final Map<Zombie, ParticleTracker> particleTrackers = new HashMap<>();

        public ApplyStrengthToNearbyZombiesGoal(Mob mob, double detectionRange) {
            this.mob = mob;
            this.detectionRange = detectionRange;
            this.cooldownTicks = 0;
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (cooldownTicks > 0) {
                cooldownTicks--;
                return; // Exit if cooldown is active
            }

            // Detect nearby entities within the specified range
            List<LivingEntity> nearbyEntities = mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(detectionRange));

            // Iterate through the list and find Zombies
            for (LivingEntity entity : nearbyEntities) {
                if (entity instanceof Zombie) {
                    Zombie zombie = (Zombie) entity;

                    // Apply Strength effect to the zombie
                    MobEffectInstance strengthEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1); // 10 seconds, level 1
                    zombie.addEffect(strengthEffect);
                    mob.playSound(SoundEvents.PLAYER_LEVELUP, 0.8F, 1.0F);

                    // Ensure the code runs on the server side to spawn particles
                    if (!zombie.level().isClientSide()) {
                        ServerLevel serverLevel = (ServerLevel) zombie.level();

                        // Get or create the particle tracker for this zombie
                        ParticleTracker tracker = particleTrackers.computeIfAbsent(zombie, k -> new ParticleTracker());

                        // Update particle positions and spawn them
                        tracker.updateParticles(zombie, serverLevel);

                        // Set a random cooldown between 100 and 300 ticks (5 to 15 seconds)
                        cooldownTicks = 100 + random.nextInt(200);
                        break; // Only boost one zombie per tick cycle
                    }
                }
            }
        }

        private static class ParticleTracker {
            private static final int PARTICLE_COUNT = 10;

            public void updateParticles(Zombie zombie, ServerLevel serverLevel) {
                double x = zombie.getX();
                double y = zombie.getY() + zombie.getBbHeight() / 2.0;
                double z = zombie.getZ();

                for (int i = 0; i < PARTICLE_COUNT; i++) {
                    double offsetX = random.nextGaussian() * 0.2;
                    double offsetY = random.nextGaussian() * 0.2;
                    double offsetZ = random.nextGaussian() * 0.2;

                    serverLevel.sendParticles(
                            ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, // The type of particle
                            x + offsetX,                 // X position of the particle
                            y + offsetY,                 // Y position of the particle
                            z + offsetZ,                 // Z position of the particle
                            1,                            // Number of particles per spawn call
                            0, 0, 0,                     // X, Y, Z speed (0 means stationary)
                            0.1                          // Particle speed
                    );
                }
            }
        }
    }

    public class ApplyWeaknessToNearbyPlayerGoal extends Goal {
        private final Mob mob;
        private final double detectionRange;
        private static final Random random = new Random();
        private int cooldownTicks;

        // Map to keep track of particles for each zombie
        private final Map<Zombie, ParticleTracker> particleTrackers = new HashMap<>();

        public ApplyWeaknessToNearbyPlayerGoal(Mob mob, double detectionRange) {
            this.mob = mob;
            this.detectionRange = detectionRange;
            this.cooldownTicks = 0;
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (cooldownTicks > 0) {
                cooldownTicks--;
                return; // Exit if cooldown is active
            }

            // Detect nearby entities within the specified range
            List<LivingEntity> nearbyEntities = mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(detectionRange));

            // Iterate through the list and find Zombies
            for (LivingEntity entity : nearbyEntities) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    // Apply Strength effect to the zombie
                    MobEffectInstance strengthEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1); // 10 seconds, level 1
                    player.addEffect(strengthEffect);
                    mob.playSound(SoundEvents.PLAYER_LEVELUP, 0.8F, 1.0F);

                    // Ensure the code runs on the server side to spawn particles
                    if (!player.level().isClientSide()) {
                        ServerLevel serverLevel = (ServerLevel) player.level();

                        // Get or create the particle tracker for this zombie


                        // Update particle positions and spawn them

                        // Set a random cooldown between 100 and 300 ticks (5 to 15 seconds)
                        cooldownTicks = 100 + random.nextInt(200);
                        break; // Only boost one zombie per tick cycle
                    }
                }
            }
        }

        private static class ParticleTracker {
            private static final int PARTICLE_COUNT = 10;

            public void updateParticles(Player player, ServerLevel serverLevel) {
                double x = player.getX();
                double y = player.getY() + player.getBbHeight() / 2.0;
                double z = player.getZ();

                for (int i = 0; i < PARTICLE_COUNT; i++) {
                    double offsetX = random.nextGaussian() * 0.2;
                    double offsetY = random.nextGaussian() * 0.2;
                    double offsetZ = random.nextGaussian() * 0.2;

                    serverLevel.sendParticles(
                            ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, // The type of particle
                            x + offsetX,                 // X position of the particle
                            y + offsetY,                 // Y position of the particle
                            z + offsetZ,                 // Z position of the particle
                            1,                            // Number of particles per spawn call
                            0, 0, 0,                     // X, Y, Z speed (0 means stationary)
                            0.1                          // Particle speed
                    );
                }
            }
        }
    }


    class SamuraiSpiritChargeAttackGoal extends Goal {
        public SamuraiSpiritChargeAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity livingentity = SamuraiSpiritEntity.this.getTarget();
            return livingentity != null && livingentity.isAlive() && !SamuraiSpiritEntity.this.
                    getMoveControl()
                    .hasWanted() && SamuraiSpiritEntity.this.random.nextInt(reducedTickDelay(1)) == 0 && SamuraiSpiritEntity
                    .this.distanceToSqr(livingentity) > 5.0;
        }

        public boolean canContinueToUse() {
            return SamuraiSpiritEntity.this.getMoveControl().hasWanted() && SamuraiSpiritEntity.this.isCharging() && SamuraiSpiritEntity.this.getTarget() != null && SamuraiSpiritEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = SamuraiSpiritEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                SamuraiSpiritEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
            }

            SamuraiSpiritEntity.this.setIsCharging(true);
            SamuraiSpiritEntity.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
        }

        public void stop() {
            SamuraiSpiritEntity.this.setIsCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = SamuraiSpiritEntity.this.getTarget();
            if (livingentity != null) {
                if (SamuraiSpiritEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    SamuraiSpiritEntity.this.doHurtTarget(livingentity);
                    SamuraiSpiritEntity.this.setIsCharging(false);
                } else {
                    double d0 = SamuraiSpiritEntity.this.distanceToSqr(livingentity);
                    if (d0 < 9.0) {
                        Vec3 vec3 = livingentity.getEyePosition();
                        SamuraiSpiritEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0);
                    }
                }
            }

        }

    }







}

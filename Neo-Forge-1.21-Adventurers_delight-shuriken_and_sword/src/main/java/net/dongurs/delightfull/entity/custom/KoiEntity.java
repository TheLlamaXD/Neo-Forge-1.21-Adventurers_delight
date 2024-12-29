package net.dongurs.delightfull.entity.custom;

import com.google.errorprone.annotations.Var;
import net.dongurs.delightfull.entity.client.koi.KoiVariant;
import net.dongurs.delightfull.entity.custom.koi_fish_classes.AbstractSchoolingKoi;
import net.dongurs.delightfull.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class KoiEntity extends AbstractSchoolingKoi {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(KoiEntity.class, EntityDataSerializers.INT);


    public KoiEntity(EntityType<? extends KoiEntity> entityType, Level level) {
        super(entityType, level);
    }

    public int getMaxSchoolSize() {
        return 5;
    }

    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.KOI_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SALMON_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    //Variant




    public void saveToBucketTag(ItemStack stack) {
        super.saveToBucketTag(stack);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, (compoundTag) -> {
            compoundTag.putInt("BucketVariantTag", this.getTypeVariant());
        });
    }

    public void loadFromBucketTag(CompoundTag tag) {
        super.loadFromBucketTag(tag);
        if (tag.contains("BucketVariantTag", 3)) {
            this.setPackedVariant(tag.getInt("BucketVariantTag"));
        }

    }



    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT,0);
    }

    private int getTypeVariant(){
        return this.entityData.get(VARIANT);
    }

    public KoiVariant getVariant(){
        return KoiVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(KoiVariant variant){
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    private void setPackedVariant(int packedVariant) {
        this.entityData.set(VARIANT, packedVariant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.fromBucket());
        compound.putInt("Variant",this.getTypeVariant());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
        this.entityData.set(VARIANT, compound.getInt("Variant"));

    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                                  @Nullable SpawnGroupData spawnGroupData) {

        KoiVariant variant = Util.getRandom(KoiVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);


    }
}
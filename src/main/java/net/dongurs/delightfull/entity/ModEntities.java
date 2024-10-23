package net.dongurs.delightfull.entity;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.entity.custom.SamuraiSpiritEntity;
import net.dongurs.delightfull.entity.custom.ShurikenProjectileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AdventurersDelight.MOD_ID);


    public static final Supplier<EntityType<SamuraiSpiritEntity>> SPIRIT = ENTITY_TYPES.register("spirit", ()-> EntityType.Builder.
            of(SamuraiSpiritEntity:: new, MobCategory.MONSTER).sized(0.7f,0.7f).build("spirit"));

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, AdventurersDelight.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<ShurikenProjectileEntity>> SHURIKEN = register("shuriken",
            EntityType.Builder.<ShurikenProjectileEntity>of(ShurikenProjectileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));



    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
    }





    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}



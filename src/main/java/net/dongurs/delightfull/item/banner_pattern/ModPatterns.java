package net.dongurs.delightfull.item.banner_pattern;


import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModPatterns {

    public static final ResourceKey<BannerPattern> SPIRIT = create("spirit");



    public ModPatterns() {
    }



    private static ResourceKey<BannerPattern> create(String name) {
        return ResourceKey.create(Registries.BANNER_PATTERN, ResourceLocation.withDefaultNamespace(name));
    }

    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        register(context, SPIRIT);

    }

    public static void register(BootstrapContext<BannerPattern> context, ResourceKey<BannerPattern> resourceKey) {
        context.register(resourceKey, new BannerPattern(resourceKey.location(), "block.minecraft.banner." + resourceKey.location().toShortLanguageKey()));
    }

}
package net.dongurs.delightfull.item.patterns;

import net.dongurs.delightfull.AdventurersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;


public class TagRegistry {



    public static final TagKey<BannerPattern> SACREFICE_BANNER_PATTERN = bannerPatternTags("pattern_item/sacrefice");


    private static  TagKey<BannerPattern> bannerPatternTags(String name) {
        return TagKey.create(Registries.BANNER_PATTERN, ResourceLocation.fromNamespaceAndPath(AdventurersDelight.MOD_ID, name));
    }





}








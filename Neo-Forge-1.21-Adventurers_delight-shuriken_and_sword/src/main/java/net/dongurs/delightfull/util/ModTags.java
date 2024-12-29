package net.dongurs.delightfull.util;

import net.dongurs.delightfull.AdventurersDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {


    public static class Blocks{
        public static final TagKey<Block> INCORRECT_FOR_JADE_GEM_TOOL = createTag("incorrect_for_jade_gem_tool");
        public static final TagKey<Block> NEEDS_JADE_GEM_TOOL = createTag("needs_jade_gem_tool");

        public static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AdventurersDelight.MOD_ID,name));
        }
    }







}

package net.dongurs.delightfull.item.custom;

import net.dongurs.delightfull.item.ModItems;
import net.dongurs.delightfull.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;


public class ModToolTiers {


    public static final Tier JADE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_JADE_GEM_TOOL,1500,4f,1.5f,20,
            ()-> Ingredient.of(ModItems.JADE_GEM.get()));

}

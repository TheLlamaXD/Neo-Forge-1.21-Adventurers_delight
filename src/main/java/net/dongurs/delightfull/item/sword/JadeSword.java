package net.dongurs.delightfull.item.sword;




import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;


public class JadeSword extends SwordItem {

    public JadeSword() {
        super(TOOL_TIER, new Item.Properties());
    }


    private static final Tier TOOL_TIER = new Tier() {


        @Override
        public int getUses() {
            return 1500;
        }

        @Override
        public float getSpeed() {
            return 4f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 1;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 24;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of();
        }



    };

}

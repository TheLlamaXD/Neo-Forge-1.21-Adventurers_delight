package net.dongurs.delightfull.item.sword;




import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;

import java.util.List;


public class JadeSword extends SwordItem {
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("tooltip.delightfull.jade_sword"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    /*
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        // Add "+1 Attack Reach" in green text to the tooltip
        tooltip.add(new TextComponent("+1 Attack Reach").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)))); // Green color
    }

     */



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



    public JadeSword() {
        super(TOOL_TIER, new Item.Properties()



                //dont look at this :(

                /*
                .component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(ResourceLocation.withDefaultNamespace("delightfull"),
                                1.5f, AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(ResourceLocation.withDefaultNamespace("delightfull"),
                                5,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND).
                add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(ResourceLocation.withDefaultNamespace("delightfull"),
                                1.8,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND).build())

                 */



                );
    }



}

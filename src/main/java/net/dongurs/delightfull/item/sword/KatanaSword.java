package net.dongurs.delightfull.item.sword;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class KatanaSword extends TieredItem {


    public KatanaSword(Tier tier, Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public static final int EFFECTIVE_BLOCK_DELAY = 5;
    public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;


    public String getDescriptionId(ItemStack stack) {
        DyeColor dyecolor = (DyeColor)stack.get(DataComponents.BASE_COLOR);
        return dyecolor != null ? this.getDescriptionId() + "." + dyecolor.getName() : super.getDescriptionId(stack);
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BannerItem.appendHoverTextFromBannerBlockEntityTag(stack, tooltipComponents);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemTags.PLANKS) || super.isValidRepairItem(toRepair, repair);
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.OFFHAND;
    }

    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility);
    }
}

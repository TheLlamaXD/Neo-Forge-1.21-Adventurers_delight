package net.dongurs.delightfull.item.sword;

import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class KatanaSword extends TieredItem{
    public static float damagePercentReduction;


    public KatanaSword(Tier tier, Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }


    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public static float getDamagePercentReduction(){
        return damagePercentReduction;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player player){
            int useDurationInTicks = player.getTicksUsingItem();
            if (useDurationInTicks <= 20){
                damagePercentReduction = 100;
            }else {
                damagePercentReduction = 0;
            }
        }
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }


    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility);
    }
}

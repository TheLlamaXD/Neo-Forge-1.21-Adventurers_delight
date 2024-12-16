package net.dongurs.delightfull.item.koi_bucket;


import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class KoiBucketItem extends MobBucketItem {
    public KoiBucketItem(EntityType<?> type, Fluid content, SoundEvent emptySound, Properties properties) {
        super(type, content, emptySound, properties);
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.delightfull.bucket.variant"));


       if (stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag().getDouble("BucketVariantTag")==0){
            tooltipComponents.add(Component.translatable("tooltip.delightfull.bucket.spoted"));
        }else if (stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag().getDouble("BucketVariantTag")==1) {
            tooltipComponents.add(Component.translatable("tooltip.delightfull.bucket.albino"));
        }else if (stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag().getDouble("BucketVariantTag")==2) {
            tooltipComponents.add(Component.translatable("tooltip.delightfull.bucket.dark"));
        }else if (stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag().getDouble("BucketVariantTag")==3){
            tooltipComponents.add(Component.translatable("tooltip.delightfull.bucket.dark_fade"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

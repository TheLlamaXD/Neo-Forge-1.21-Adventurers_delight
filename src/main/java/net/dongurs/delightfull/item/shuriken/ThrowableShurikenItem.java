
package net.dongurs.delightfull.item.shuriken;

import net.dongurs.delightfull.entity.custom.ShurikenProjectileEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;


    public class ThrowableShurikenItem extends Item {
        public ThrowableShurikenItem() {
            super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)
                    .attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
        }

        @Override
        public UseAnim getUseAnimation(ItemStack itemstack) {
            return UseAnim.SPEAR;
        }

        @Override
        public int getUseDuration(ItemStack itemstack, LivingEntity livingEntity) {
            return 72000;
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
            InteractionResultHolder<ItemStack> ar = InteractionResultHolder.fail(entity.getItemInHand(hand));
            if (entity.getAbilities().instabuild || findAmmo(entity) != ItemStack.EMPTY) {
                ar = InteractionResultHolder.success(entity.getItemInHand(hand));
                entity.startUsingItem(hand);
            }
            return ar;
        }

        @Override
        public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int time) {
            if (!world.isClientSide() && entity instanceof ServerPlayer player) {
                float pullingPower = BowItem.getPowerForTime(this.getUseDuration(itemstack, player) - time);
                if (pullingPower < 0.1)
                    return;
                ItemStack stack = findAmmo(player);
                if (player.getAbilities().instabuild || stack != ItemStack.EMPTY) {
                    ShurikenProjectileEntity projectile = ShurikenProjectileEntity.shoot(world, entity, world.getRandom(), pullingPower);
                    if (player.getAbilities().instabuild) {
                        projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    } else {
                        if (stack.isDamageableItem()) {
                            if (world instanceof ServerLevel serverLevel)
                                stack.hurtAndBreak(1, serverLevel, player, _stkprov -> {
                                });
                        } else {
                            stack.shrink(1);
                        }
                    }
                }
            }
        }

        private ItemStack findAmmo(Player player) {
            ItemStack stack = ProjectileWeaponItem.getHeldProjectile(player, e -> e.getItem() == ShurikenProjectileEntity.PROJECTILE_ITEM.getItem());
            if (stack == ItemStack.EMPTY) {
                for (int i = 0; i < player.getInventory().items.size(); i++) {
                    ItemStack teststack = player.getInventory().items.get(i);
                    if (teststack != null && teststack.getItem() == ShurikenProjectileEntity.PROJECTILE_ITEM.getItem()) {
                        stack = teststack;
                        break;
                    }
                }
            }
            return stack;
        }

    }


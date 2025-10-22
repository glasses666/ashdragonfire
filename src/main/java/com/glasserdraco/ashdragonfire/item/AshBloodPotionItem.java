package com.glasserdraco.ashdragonfire.item;

import com.glasserdraco.ashdragonfire.conversion.ConversionManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.ChatFormatting;

import java.util.List;

/**
 * 灰烬之血药剂物品类
 * 
 * 用于净化人类士兵，使其转变为龙族阵营的"誓火者"。
 * 
 * 使用方法：
 * 1. 手持药剂右键点击人类士兵实体
 * 2. 触发净化仪式
 * 3. 100%成功率
 * 4. 人类士兵转变为誓火者
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class AshBloodPotionItem extends Item {
    
    public AshBloodPotionItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, 
                                                  LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        
        // 检查目标是否是人类实体
        if (!isHumanEntity(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.ash_blood_potion.not_human")
                    .withStyle(ChatFormatting.RED),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查是否已被转换
        if (ConversionManager.isConverted(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.ash_blood_potion.already_converted")
                    .withStyle(ChatFormatting.YELLOW),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查冷却
        if (ConversionManager.isOnCooldown(player)) {
            int remaining = ConversionManager.getRemainingCooldown(player);
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.conversion.cooldown", remaining)
                    .withStyle(ChatFormatting.YELLOW),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 尝试净化（100%成功）
        boolean success = ConversionManager.purifyHuman(player, target);
        
        if (success) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.ash_blood_potion.success")
                    .withStyle(ChatFormatting.GREEN),
                true
            );
            
            // 消耗物品
            stack.shrink(1);
            
            return InteractionResult.SUCCESS;
        }
        
        return InteractionResult.FAIL;
    }
    
    private boolean isHumanEntity(LivingEntity entity) {
        // TODO: 实现人类实体检测逻辑
        String entityType = entity.getType().toString();
        return entityType.contains("soldier") || entityType.contains("human") ||
               entity.getPersistentData().getString("Faction").equals("IRON_FEDERATION");
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.ashdragonfire.ash_blood_potion.tooltip.1")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.ashdragonfire.ash_blood_potion.tooltip.2")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.ashdragonfire.ash_blood_potion.tooltip.success_rate")
            .withStyle(ChatFormatting.GREEN));
    }
}


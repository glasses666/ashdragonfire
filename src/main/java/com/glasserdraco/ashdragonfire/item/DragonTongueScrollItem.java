package com.glasserdraco.ashdragonfire.item;

import com.glasserdraco.ashdragonfire.conversion.ConversionManager;
import com.glasserdraco.ashdragonfire.faction.FactionManager;
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
 * 龙语卷轴物品类
 * 
 * 用于和平说服人类士兵加入龙族阵营。
 * 
 * 使用方法：
 * 1. 手持卷轴右键点击人类士兵
 * 2. 触发说服对话
 * 3. 成功率取决于声望（40-80%）
 * 4. 成功后士兵成为龙族盟友
 * 5. 失败后士兵逃跑并呼叫增援
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class DragonTongueScrollItem extends Item {
    
    public DragonTongueScrollItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, 
                                                  LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        
        // 检查目标是否是人类
        if (!isHumanEntity(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_tongue_scroll.not_human")
                    .withStyle(ChatFormatting.RED),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查是否已被转换
        if (ConversionManager.isConverted(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_tongue_scroll.already_converted")
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
        
        // 获取玩家声望
        int reputation = FactionManager.getReputationLevel(player);
        
        // 尝试说服
        boolean success = ConversionManager.persuadeHuman(player, target, reputation);
        
        if (success) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_tongue_scroll.success")
                    .withStyle(ChatFormatting.GREEN),
                true
            );
            
            // 消耗物品
            stack.shrink(1);
            
            return InteractionResult.SUCCESS;
        } else {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_tongue_scroll.failed")
                    .withStyle(ChatFormatting.YELLOW),
                true
            );
            
            // 失败也消耗物品
            stack.shrink(1);
            
            return InteractionResult.FAIL;
        }
    }
    
    private boolean isHumanEntity(LivingEntity entity) {
        String entityType = entity.getType().toString();
        return entityType.contains("soldier") || entityType.contains("human") ||
               entity.getPersistentData().getString("Faction").equals("IRON_FEDERATION");
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_tongue_scroll.tooltip.1")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_tongue_scroll.tooltip.2")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_tongue_scroll.tooltip.success_rate")
            .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_tongue_scroll.tooltip.peaceful")
            .withStyle(ChatFormatting.GREEN));
    }
}


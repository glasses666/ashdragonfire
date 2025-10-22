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
 * 神经支配者物品类
 * 
 * 人类阵营的终极武器，用于强制支配龙族。
 * 
 * 使用方法：
 * 1. 手持神经支配者右键点击龙族实体
 * 2. 发射征服弹
 * 3. 100%成功率
 * 4. 龙族被洗脑成为机械化巨龙
 * 5. 耐久消耗取决于声望
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class NeuralDominatorItem extends Item {
    
    public NeuralDominatorItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, 
                                                  LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        
        // 检查目标是否是龙族
        if (!isDragonEntity(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.neural_dominator.not_dragon")
                    .withStyle(ChatFormatting.RED),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查是否已被转换
        if (ConversionManager.isConverted(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.neural_dominator.already_converted")
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
        
        // 尝试支配（100%成功）
        boolean success = ConversionManager.dominateDragon(player, target);
        
        if (success) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.neural_dominator.success")
                    .withStyle(ChatFormatting.AQUA),
                true
            );
            
            // 消耗耐久（声望越高消耗越少）
            // TODO: 根据声望调整耐久消耗
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            
            return InteractionResult.SUCCESS;
        }
        
        return InteractionResult.FAIL;
    }
    
    private boolean isDragonEntity(LivingEntity entity) {
        String entityType = entity.getType().toString();
        return entityType.contains("dragon") || 
               entity.getPersistentData().getString("Faction").equals("DRAKYN_DOMINION");
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.ashdragonfire.neural_dominator.tooltip.1")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.ashdragonfire.neural_dominator.tooltip.2")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.ashdragonfire.neural_dominator.tooltip.warning")
            .withStyle(ChatFormatting.RED));
        
        if (stack.isDamageableItem()) {
            int remaining = stack.getMaxDamage() - stack.getDamageValue();
            tooltip.add(Component.translatable("item.ashdragonfire.neural_dominator.tooltip.uses", remaining)
                .withStyle(ChatFormatting.AQUA));
        }
    }
    
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}


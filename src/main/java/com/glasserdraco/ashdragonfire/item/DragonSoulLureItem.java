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
 * 龙魂引物品类
 * 
 * 用于与龙族实体建立灵魂链接，将其驯服为玩家的伙伴。
 * 
 * 使用方法：
 * 1. 手持龙魂引右键点击野生龙族实体
 * 2. 触发灵魂链接仪式
 * 3. 成功率取决于玩家声望（50-90%）
 * 4. 成功后龙族成为忠诚伙伴
 * 5. 失败后龙族进入狂暴状态
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class DragonSoulLureItem extends Item {
    
    public DragonSoulLureItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, 
                                                  LivingEntity target, InteractionHand hand) {
        // 只在服务器端处理
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        
        // 检查目标是否是龙族实体
        if (!isDragonEntity(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_soul_lure.not_dragon")
                    .withStyle(ChatFormatting.RED),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查龙族是否已被转换
        if (ConversionManager.isConverted(target)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_soul_lure.already_converted")
                    .withStyle(ChatFormatting.YELLOW),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查冷却时间
        if (ConversionManager.isOnCooldown(player)) {
            int remaining = ConversionManager.getRemainingCooldown(player);
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.conversion.cooldown", remaining)
                    .withStyle(ChatFormatting.YELLOW),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 检查数量限制
        if (ConversionManager.hasReachedConversionLimit(player)) {
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.conversion.limit_reached")
                    .withStyle(ChatFormatting.RED),
                true
            );
            return InteractionResult.FAIL;
        }
        
        // 获取玩家声望等级
        int reputation = FactionManager.getReputationLevel(player);
        
        // 尝试驯服
        boolean success = ConversionManager.attemptTameDragon(player, target, reputation);
        
        if (success) {
            // 成功消息
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_soul_lure.success")
                    .withStyle(ChatFormatting.GREEN),
                true
            );
            
            // 消耗耐久
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            
            return InteractionResult.SUCCESS;
        } else {
            // 失败消息
            player.displayClientMessage(
                Component.translatable("item.ashdragonfire.dragon_soul_lure.failed")
                    .withStyle(ChatFormatting.RED),
                true
            );
            
            // 失败也消耗耐久
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            
            return InteractionResult.FAIL;
        }
    }
    
    /**
     * 检查实体是否是龙族
     */
    private boolean isDragonEntity(LivingEntity entity) {
        // TODO: 实现龙族实体检测逻辑
        // 检查实体类型或NBT标签
        String entityType = entity.getType().toString();
        return entityType.contains("dragon") || 
               entity.getPersistentData().getString("Faction").equals("DRAKYN_DOMINION");
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_soul_lure.tooltip.1")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_soul_lure.tooltip.2")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_soul_lure.tooltip.success_rate")
            .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("item.ashdragonfire.dragon_soul_lure.tooltip.cooldown")
            .withStyle(ChatFormatting.BLUE));
        
        // 显示耐久度
        if (stack.isDamageableItem()) {
            int remaining = stack.getMaxDamage() - stack.getDamageValue();
            tooltip.add(Component.translatable("item.ashdragonfire.dragon_soul_lure.tooltip.uses", remaining)
                .withStyle(ChatFormatting.AQUA));
        }
    }
    
    @Override
    public boolean isFoil(ItemStack stack) {
        // 史诗物品发光效果
        return true;
    }
}


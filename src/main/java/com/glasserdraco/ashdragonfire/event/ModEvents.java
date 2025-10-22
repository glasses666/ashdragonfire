package com.glasserdraco.ashdragonfire.event;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 模组事件处理类
 * 
 * 监听和处理游戏中的各种事件
 * 使用 @Mod.EventBusSubscriber 自动注册到事件总线
 * 
 * 主要事件:
 * - 玩家加入服务器: 初始化阵营数据
 * - 玩家死亡: 处理声望变化
 * - 实体死亡: 触发战争事件
 * - 世界加载: 初始化领地系统
 */
@Mod.EventBusSubscriber(modid = AshDragonfireMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    
    /**
     * 玩家登录事件
     * 当玩家加入服务器时触发
     * 
     * 功能:
     * 1. 检查玩家是否已选择阵营
     * 2. 如果未选择,显示阵营选择界面
     * 3. 同步玩家数据到客户端
     * 
     * @param event 玩家登录事件
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AshDragonfireMod.LOGGER.info("玩家 {} 加入服务器", player.getName().getString());
            
            // TODO: 检查玩家阵营数据
            // FactionData factionData = FactionData.get(player);
            // if (factionData.getFaction() == Faction.NONE) {
            //     // 发送阵营选择界面数据包
            //     PacketHandler.sendToPlayer(new OpenFactionSelectionPacket(), player);
            // }
            
            // TODO: 同步玩家数据
            // PacketHandler.sendToPlayer(new SyncPlayerDataPacket(factionData), player);
        }
    }
    
    /**
     * 玩家退出事件
     * 当玩家离开服务器时触发
     * 
     * 功能:
     * 1. 保存玩家数据
     * 2. 清理临时数据
     * 
     * @param event 玩家退出事件
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AshDragonfireMod.LOGGER.info("玩家 {} 离开服务器", player.getName().getString());
            
            // TODO: 保存玩家数据
            // PlayerDataManager.save(player);
        }
    }
    
    /**
     * 实体死亡事件
     * 当任何生物死亡时触发
     * 
     * 功能:
     * 1. 如果是玩家击杀,计算声望奖励
     * 2. 如果是阵营单位死亡,触发领地事件
     * 3. 如果是Boss死亡,触发世界事件
     * 
     * @param event 实体死亡事件
     */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        // 检查是否为玩家击杀
        if (event.getSource().getEntity() instanceof ServerPlayer killer) {
            // 检查被击杀者是否为玩家
            if (event.getEntity() instanceof ServerPlayer victim) {
                AshDragonfireMod.LOGGER.info("玩家 {} 击杀了玩家 {}", 
                    killer.getName().getString(), 
                    victim.getName().getString());
                
                // TODO: 处理阵营PVP
                // FactionData killerData = FactionData.get(killer);
                // FactionData victimData = FactionData.get(victim);
                // 
                // if (killerData.getFaction() != victimData.getFaction()) {
                //     // 敌对阵营击杀,增加声望
                //     killerData.addReputation(Config.REPUTATION_PER_KILL.get());
                //     victimData.removeReputation(Config.REPUTATION_PER_KILL.get() / 2);
                // }
            }
            
            // TODO: 检查是否击杀了阵营单位或Boss
            // if (event.getEntity() instanceof FactionMobEntity mob) {
            //     // 处理阵营单位击杀
            // } else if (event.getEntity() instanceof BossEntity boss) {
            //     // 处理Boss击杀,触发世界事件
            //     WarEventManager.onBossKilled(killer, boss);
            // }
        }
    }
    
    /**
     * 玩家重生事件
     * 当玩家死亡后重生时触发
     * 
     * 功能:
     * 1. 恢复阵营特定的重生位置
     * 2. 重置能量/魔力值
     * 
     * @param event 玩家重生事件
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AshDragonfireMod.LOGGER.info("玩家 {} 重生", player.getName().getString());
            
            // TODO: 恢复阵营特定属性
            // FactionData factionData = FactionData.get(player);
            // factionData.resetEnergy();
        }
    }
    
    /**
     * 玩家克隆事件
     * 当玩家从末地返回或死亡后保留数据时触发
     * 
     * 功能:
     * 1. 复制阵营数据
     * 2. 复制声望和技能数据
     * 
     * @param event 玩家克隆事件
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // 玩家死亡后的数据复制
            // TODO: 复制持久化数据
            // FactionData.copy(event.getOriginal(), event.getEntity());
        }
    }
}


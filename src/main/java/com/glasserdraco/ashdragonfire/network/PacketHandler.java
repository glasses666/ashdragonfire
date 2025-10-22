package com.glasserdraco.ashdragonfire.network;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * 网络包处理器
 * 
 * 使用 Forge 的 SimpleChannel 系统处理客户端-服务器通信
 * 所有需要网络同步的操作都通过这个类发送数据包
 * 
 * 主要数据包类型:
 * - 阵营选择: 玩家选择阵营时发送
 * - 技能使用: 玩家使用技能时发送
 * - 数据同步: 服务器向客户端同步数据
 * - 事件触发: 触发世界事件
 * 
 * 协议版本: 1
 */
public class PacketHandler {
    
    private static final String PROTOCOL_VERSION = "1";
    
    /**
     * 网络通道
     * 用于发送和接收数据包
     */
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(AshDragonfireMod.MOD_ID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    
    private static int packetId = 0;
    
    /**
     * 获取下一个数据包ID
     * 
     * @return 数据包ID
     */
    private static int id() {
        return packetId++;
    }
    
    /**
     * 注册所有数据包
     * 在主类的 commonSetup 阶段调用
     */
    public static void register() {
        AshDragonfireMod.LOGGER.info("正在注册网络数据包...");
        
        // ========== 客户端 -> 服务器 数据包 ==========
        
        // TODO: 注册阵营选择数据包
        // INSTANCE.messageBuilder(FactionSelectionPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
        //     .decoder(FactionSelectionPacket::decode)
        //     .encoder(FactionSelectionPacket::encode)
        //     .consumerMainThread(FactionSelectionPacket::handle)
        //     .add();
        
        // TODO: 注册技能使用数据包
        // INSTANCE.messageBuilder(AbilityUsePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
        //     .decoder(AbilityUsePacket::decode)
        //     .encoder(AbilityUsePacket::encode)
        //     .consumerMainThread(AbilityUsePacket::handle)
        //     .add();
        
        // ========== 服务器 -> 客户端 数据包 ==========
        
        // TODO: 注册数据同步数据包
        // INSTANCE.messageBuilder(SyncPlayerDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        //     .decoder(SyncPlayerDataPacket::decode)
        //     .encoder(SyncPlayerDataPacket::encode)
        //     .consumerMainThread(SyncPlayerDataPacket::handle)
        //     .add();
        
        // TODO: 注册事件触发数据包
        // INSTANCE.messageBuilder(EventTriggerPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        //     .decoder(EventTriggerPacket::decode)
        //     .encoder(EventTriggerPacket::encode)
        //     .consumerMainThread(EventTriggerPacket::handle)
        //     .add();
        
        // TODO: 注册粒子效果数据包
        // INSTANCE.messageBuilder(ParticleEffectPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        //     .decoder(ParticleEffectPacket::decode)
        //     .encoder(ParticleEffectPacket::encode)
        //     .consumerMainThread(ParticleEffectPacket::handle)
        //     .add();
        
        AshDragonfireMod.LOGGER.info("网络数据包注册完成!");
    }
    
    /**
     * 发送数据包到服务器
     * 
     * @param message 数据包对象
     */
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    
    /**
     * 发送数据包到指定玩家
     * 
     * @param message 数据包对象
     * @param player 目标玩家
     */
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    
    /**
     * 发送数据包到所有玩家
     * 
     * @param message 数据包对象
     */
    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
    
    /**
     * 发送数据包到指定维度的所有玩家
     * 
     * @param message 数据包对象
     * @param dimension 维度键
     */
    public static <MSG> void sendToDimension(MSG message, net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> dimension) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
    }
    
    /**
     * 发送数据包到指定位置附近的玩家
     * 
     * @param message 数据包对象
     * @param targetPoint 目标点(包含位置和范围)
     */
    public static <MSG> void sendToNearby(MSG message, PacketDistributor.TargetPoint targetPoint) {
        INSTANCE.send(PacketDistributor.NEAR.with(() -> targetPoint), message);
    }
}


package com.glasserdraco.ashdragonfire.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * 转换同步网络包
 * 
 * 用于在服务器和客户端之间同步实体的转换状态：
 * - 转换类型
 * - 主人UUID
 * - 转换时间
 * - 阵营信息
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class ConversionSyncPacket {
    
    private final int entityId;
    private final String conversionType;
    private final UUID ownerUUID;
    private final long conversionTime;
    private final String originalFaction;
    private final String currentFaction;
    
    public ConversionSyncPacket(int entityId, String conversionType, UUID ownerUUID, 
                               long conversionTime, String originalFaction, String currentFaction) {
        this.entityId = entityId;
        this.conversionType = conversionType;
        this.ownerUUID = ownerUUID;
        this.conversionTime = conversionTime;
        this.originalFaction = originalFaction;
        this.currentFaction = currentFaction;
    }
    
    /**
     * 从实体创建同步包
     */
    public static ConversionSyncPacket fromEntity(LivingEntity entity) {
        return new ConversionSyncPacket(
            entity.getId(),
            entity.getPersistentData().getString("ConversionType"),
            entity.getPersistentData().contains("OwnerUUID") ? 
                entity.getPersistentData().getUUID("OwnerUUID") : null,
            entity.getPersistentData().getLong("ConversionTime"),
            entity.getPersistentData().getString("OriginalFaction"),
            entity.getPersistentData().getString("CurrentFaction")
        );
    }
    
    /**
     * 编码到缓冲区
     */
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(conversionType);
        buf.writeBoolean(ownerUUID != null);
        if (ownerUUID != null) {
            buf.writeUUID(ownerUUID);
        }
        buf.writeLong(conversionTime);
        buf.writeUtf(originalFaction);
        buf.writeUtf(currentFaction);
    }
    
    /**
     * 从缓冲区解码
     */
    public static ConversionSyncPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String conversionType = buf.readUtf();
        UUID ownerUUID = buf.readBoolean() ? buf.readUUID() : null;
        long conversionTime = buf.readLong();
        String originalFaction = buf.readUtf();
        String currentFaction = buf.readUtf();
        
        return new ConversionSyncPacket(entityId, conversionType, ownerUUID, 
                                       conversionTime, originalFaction, currentFaction);
    }
    
    /**
     * 处理网络包
     */
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // 在客户端执行
            if (context.getDirection().getReceptionSide().isClient()) {
                handleClient();
            }
        });
        context.setPacketHandled(true);
    }
    
    /**
     * 客户端处理逻辑
     */
    private void handleClient() {
        // TODO: 获取客户端世界和实体
        // Minecraft minecraft = Minecraft.getInstance();
        // if (minecraft.level != null) {
        //     Entity entity = minecraft.level.getEntity(entityId);
        //     if (entity instanceof LivingEntity livingEntity) {
        //         applyToEntity(livingEntity);
        //     }
        // }
    }
    
    /**
     * 应用数据到实体
     */
    public void applyToEntity(LivingEntity entity) {
        if (!conversionType.isEmpty()) {
            entity.getPersistentData().putString("ConversionType", conversionType);
        }
        
        if (ownerUUID != null) {
            entity.getPersistentData().putUUID("OwnerUUID", ownerUUID);
        }
        
        if (conversionTime > 0) {
            entity.getPersistentData().putLong("ConversionTime", conversionTime);
        }
        
        if (!originalFaction.isEmpty()) {
            entity.getPersistentData().putString("OriginalFaction", originalFaction);
        }
        
        if (!currentFaction.isEmpty()) {
            entity.getPersistentData().putString("CurrentFaction", currentFaction);
        }
    }
    
    // Getters
    public int getEntityId() { return entityId; }
    public String getConversionType() { return conversionType; }
    public UUID getOwnerUUID() { return ownerUUID; }
    public long getConversionTime() { return conversionTime; }
    public String getOriginalFaction() { return originalFaction; }
    public String getCurrentFaction() { return currentFaction; }
}


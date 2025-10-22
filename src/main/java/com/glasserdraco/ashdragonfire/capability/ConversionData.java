package com.glasserdraco.ashdragonfire.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

/**
 * 转换数据Capability实现类
 * 
 * 实现IConversionData接口，提供数据存储和NBT序列化功能。
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class ConversionData implements IConversionData {
    
    private String conversionType = "";
    private UUID ownerUUID = null;
    private long conversionTime = 0;
    private String originalFaction = "";
    private String currentFaction = "";
    
    @Override
    public String getConversionType() {
        return conversionType;
    }
    
    @Override
    public void setConversionType(String type) {
        this.conversionType = type != null ? type : "";
    }
    
    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    
    @Override
    public void setOwnerUUID(UUID uuid) {
        this.ownerUUID = uuid;
    }
    
    @Override
    public long getConversionTime() {
        return conversionTime;
    }
    
    @Override
    public void setConversionTime(long time) {
        this.conversionTime = time;
    }
    
    @Override
    public String getOriginalFaction() {
        return originalFaction;
    }
    
    @Override
    public void setOriginalFaction(String faction) {
        this.originalFaction = faction != null ? faction : "";
    }
    
    @Override
    public String getCurrentFaction() {
        return currentFaction;
    }
    
    @Override
    public void setCurrentFaction(String faction) {
        this.currentFaction = faction != null ? faction : "";
    }
    
    @Override
    public boolean isConverted() {
        return !conversionType.isEmpty();
    }
    
    @Override
    public boolean hasOwner() {
        return ownerUUID != null;
    }
    
    @Override
    public void clear() {
        this.conversionType = "";
        this.ownerUUID = null;
        this.conversionTime = 0;
        this.originalFaction = "";
        this.currentFaction = "";
    }
    
    @Override
    public void copyTo(IConversionData other) {
        other.setConversionType(this.conversionType);
        other.setOwnerUUID(this.ownerUUID);
        other.setConversionTime(this.conversionTime);
        other.setOriginalFaction(this.originalFaction);
        other.setCurrentFaction(this.currentFaction);
    }
    
    /**
     * 保存数据到NBT
     * 
     * @return NBT标签
     */
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        
        if (!conversionType.isEmpty()) {
            tag.putString("ConversionType", conversionType);
        }
        
        if (ownerUUID != null) {
            tag.putUUID("OwnerUUID", ownerUUID);
        }
        
        if (conversionTime > 0) {
            tag.putLong("ConversionTime", conversionTime);
        }
        
        if (!originalFaction.isEmpty()) {
            tag.putString("OriginalFaction", originalFaction);
        }
        
        if (!currentFaction.isEmpty()) {
            tag.putString("CurrentFaction", currentFaction);
        }
        
        return tag;
    }
    
    /**
     * 从NBT加载数据
     * 
     * @param tag NBT标签
     */
    public void deserializeNBT(CompoundTag tag) {
        this.conversionType = tag.contains("ConversionType") ? tag.getString("ConversionType") : "";
        this.ownerUUID = tag.contains("OwnerUUID") ? tag.getUUID("OwnerUUID") : null;
        this.conversionTime = tag.contains("ConversionTime") ? tag.getLong("ConversionTime") : 0;
        this.originalFaction = tag.contains("OriginalFaction") ? tag.getString("OriginalFaction") : "";
        this.currentFaction = tag.contains("CurrentFaction") ? tag.getString("CurrentFaction") : "";
    }
}


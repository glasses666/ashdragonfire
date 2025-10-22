package com.glasserdraco.ashdragonfire.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 转换数据Capability提供者
 * 
 * 为实体附加转换数据Capability，并提供NBT序列化功能。
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class ConversionDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    
    public static final Capability<IConversionData> CONVERSION_DATA = 
        CapabilityManager.get(new CapabilityToken<>(){});
    
    private final ConversionData data = new ConversionData();
    private final LazyOptional<IConversionData> optional = LazyOptional.of(() -> data);
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CONVERSION_DATA) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        return data.serializeNBT();
    }
    
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.deserializeNBT(nbt);
    }
    
    /**
     * 使Capability失效
     */
    public void invalidate() {
        optional.invalidate();
    }
}


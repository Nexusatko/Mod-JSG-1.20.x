package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.animation.IButtonsState;
import dev.tauri.jsg.core.common.blockentity.IStateProvider;
import dev.tauri.jsg.core.common.blockentity.ITickable;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.PacketDistributor;

public interface IDHDStateManager extends INBTSerializable<CompoundTag>, IStateProvider, ITickable {
    IButtonsState getButtonsState();

    PacketDistributor.TargetPoint getTargetPoint();
}

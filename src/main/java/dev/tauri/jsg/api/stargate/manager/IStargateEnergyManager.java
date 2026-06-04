package dev.tauri.jsg.api.stargate.manager;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.api.stargate.network.StargatePos;
import dev.tauri.jsg.api.stargate.network.address.StargateAddressDynamic;
import dev.tauri.jsg.core.common.blockentity.ITickable;
import dev.tauri.jsg.core.common.power.JSGEnergyStorage;
import dev.tauri.jsg.core.common.power.general.EnergyRequiredToOperate;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.Map;

public interface IStargateEnergyManager<E extends JSGEnergyStorage> extends INBTSerializable<CompoundTag>, ITickable {
    E getStorage();

    double getSecondsToClose();

    int getTransferredLastTick();

    boolean canOpenWormhole(EnergyRequiredToOperate energyRequiredToDial);

    Map<BlockPos, IEnergyStorage> getEnergyStoragesConnectedToStargate();

    default EnergyRequiredToOperate getEnergyRequiredToDial(Stargate<?> targetGate, StargateAddressDynamic address) {
        return getEnergyRequiredToDial(targetGate.getStargatePos(), address);
    }

    EnergyRequiredToOperate getEnergyRequiredToDial(@Nullable StargatePos targetGatePos, StargateAddressDynamic address);
}

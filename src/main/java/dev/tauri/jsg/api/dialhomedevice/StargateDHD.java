package dev.tauri.jsg.api.dialhomedevice;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.common.dialhomedevice.manager.DHDReactorManager;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDAbstractStateManager;
import dev.tauri.jsg.core.common.blockentity.ILinkableBE;
import dev.tauri.jsg.core.common.blockentity.IPreparable;
import dev.tauri.jsg.core.common.blockentity.ITickable;
import dev.tauri.jsg.core.common.blockentity.IUpgradable;
import dev.tauri.jsg.core.common.symbol.SymbolInterface;
import dev.tauri.jsg.core.common.symbol.SymbolType;
import dev.tauri.jsg.core.common.symbol.pointoforigin.PointOfOrigin;
import dev.tauri.jsg.core.common.util.IUpgrade;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public interface StargateDHD extends ILinkableBE<Stargate<?>>, ITickable, IPreparable, IUpgradable {
    private BlockEntity self() {
        return (BlockEntity) this;
    }

    DHDAbstractStateManager<?, ?> getStateManager();

    DHDReactorManager getReactorManager();

    @Override
    default void onLoad(@NotNull Level level) {
        getStateManager().onLoad(level);
        getReactorManager().onLoad(level);
    }

    @Override
    default void tick(@NotNull Level level) {
        getStateManager().tick(level);
        getReactorManager().tick(level);
    }

    default long getTime() {
        var level = self().getLevel();
        if (level == null) return 0;
        return level.getGameTime();
    }

    Vec3 getBlockPosInFront();

    SymbolType<?> getSymbolType();

    Item getControlCrystal();

    ItemStackHandler getItemStackHandler();

    boolean hasControlCrystal();

    void clearSymbols();

    void activateSymbol(SymbolInterface symbol);

    void pushSymbolButton(SymbolInterface symbol, @Nullable ServerPlayer player, boolean force);

    default Optional<Stargate<?>> getStargate() {
        if (!isLinked()) {
            return Optional.empty();
        }
        return Optional.ofNullable(getLinkedDevice());
    }

    @Nullable
    default PointOfOrigin getPointOfOrigin() {
        return getLinkedDeviceOptional().map((sg) -> sg.getPointOfOrigin(getSymbolType())).orElse(null);
    }

    enum DHDUpgradeEnum implements IUpgrade {
        CHEVRON_UPGRADE
    }
}

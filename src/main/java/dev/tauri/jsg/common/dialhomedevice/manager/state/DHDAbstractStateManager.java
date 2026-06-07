package dev.tauri.jsg.common.dialhomedevice.manager.state;

import dev.tauri.jsg.api.dialhomedevice.manager.IDHDStateManager;
import dev.tauri.jsg.api.registry.JSGStateTypes;
import dev.tauri.jsg.client.renderer.blockentity.dialhomedevice.DHDAbstractRendererState;
import dev.tauri.jsg.common.blockentity.dialhomedevice.DHDAbstractBE;
import dev.tauri.jsg.common.dialhomedevice.animation.DHDButtonsState;
import dev.tauri.jsg.common.dialhomedevice.manager.AbstractDHDManager;
import dev.tauri.jsg.core.common.blockentity.StateProviderInterface;
import dev.tauri.jsg.core.common.entity.State;
import dev.tauri.jsg.core.common.entity.StateType;
import dev.tauri.jsg.core.common.registry.CoreStateTypes;
import dev.tauri.jsg.core.common.state.BiomeOverrideState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public abstract class DHDAbstractStateManager<DHD extends DHDAbstractBE, S extends DHDAbstractRendererState> extends AbstractDHDManager<DHD> implements IDHDStateManager, StateProviderInterface {
    private PacketDistributor.TargetPoint targetPoint;
    protected DHDButtonsState buttonsState;

    public DHDAbstractStateManager(DHD dhd) {
        super(dhd);
        this.buttonsState = generateButtonsState();
    }

    protected abstract DHDButtonsState generateButtonsState();

    @Override
    public DHDButtonsState getButtonsState() {
        return buttonsState;
    }

    @Override
    public State getState(StateType stateType) {
        return stateType.stateSupplier()
                .tryType(CoreStateTypes.BIOME_OVERRIDE_STATE, () -> new BiomeOverrideState(dhd.determineBiomeOverride()))
                .tryType(CoreStateTypes.RENDERER_STATE, this::getRendererStateClient)
                .tryType(JSGStateTypes.BUTTONS_STATE, this::getButtonsState)
                .orElseGet(() -> null);
    }

    @Override
    public State createState(StateType stateType) {
        return stateType.stateSupplier()
                .tryType(CoreStateTypes.BIOME_OVERRIDE_STATE, BiomeOverrideState::new)
                .tryType(CoreStateTypes.RENDERER_STATE, this::createRendererStateClient)
                .tryType(JSGStateTypes.BUTTONS_STATE, this::getButtonsState)
                .orElseThrow(this);
    }

    @SuppressWarnings("unchecked")
    protected S castState(State state) {
        return (S) state;
    }

    @Override
    public void setState(StateType stateType, State state) {
        stateType.stateExecutor()
                .tryType(CoreStateTypes.BIOME_OVERRIDE_STATE, () -> {
                    BiomeOverrideState overrideState = (BiomeOverrideState) state;

                    if (rendererStateClient != null) {
                        getRendererStateClient().biomeOverlay = overrideState.biomeOverride;
                    }
                })
                .tryType(CoreStateTypes.RENDERER_STATE, () -> setRendererStateClient(castState(state)))
                .tryType(JSGStateTypes.BUTTONS_STATE, () -> {
                    buttonsState = (DHDButtonsState) state;
                })
                .run();
    }

    @Override
    public BlockPos getBlockPos() {
        return dhd.getBlockPos();
    }

    // ------------------------------------------------------------------------
    // Rendering

    protected S rendererStateClient = createRendererStateClient();

    public S getRendererStateClient() {
        return rendererStateClient;
    }

    protected abstract S createRendererStateClient();

    protected void setRendererStateClient(S rendererState) {
        this.rendererStateClient = rendererState;
    }

    // ------------------------------------------------------------------------

    @Override
    public PacketDistributor.TargetPoint getTargetPoint() {
        if (dhd.getLevel() == null) return targetPoint;
        if (targetPoint == null) {
            var pos = getBlockPos();
            targetPoint = new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 512, dhd.getLevel().dimension());
        }
        return targetPoint;
    }

    @Override
    public void tick(@NotNull Level level) {
        getButtonsState().tick(level);
        if (level.isClientSide) {
            if (getRendererStateClient() == null) {
                requestState(CoreStateTypes.RENDERER_STATE.get());
                // Each 2s check for the sky
                // TODO(Mine): Fix biome overlays
                //if (level.getGameTime() % 40 == 0 && rendererStateClient != null
                //        && getRendererStateClient().biomeOverride == null) {
                //    rendererStateClient.setBiomeOverlay(BiomeOverlayInstance.getUpdatedBiomeOverlay(level, getBlockPos()));
                //}
            }
        }
    }

    @Override
    public void onLoad(@NotNull Level level) {
        if (level.isClientSide()) {
            requestState(CoreStateTypes.BIOME_OVERRIDE_STATE.get());
            requestState(CoreStateTypes.RENDERER_STATE.get());
            requestState(JSGStateTypes.BUTTONS_STATE.get());
        } else {
            getAndSendState(CoreStateTypes.BIOME_OVERRIDE_STATE.get());
            getAndSendState(JSGStateTypes.BUTTONS_STATE.get());
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        var compound = new CompoundTag();
        compound.put("buttonsState", buttonsState.serializeNBT());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        buttonsState.deserializeNBT(compound.getCompound("buttonsState"));
    }
}

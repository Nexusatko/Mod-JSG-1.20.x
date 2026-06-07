package dev.tauri.jsg.common.dialhomedevice.manager.state;

import dev.tauri.jsg.client.renderer.blockentity.dialhomedevice.DHDPegasusRendererState;
import dev.tauri.jsg.common.blockentity.dialhomedevice.DHDPegasusBE;
import dev.tauri.jsg.common.dialhomedevice.animation.DHDButtonsState;

public class DHDPegasusStateManager extends DHDAbstractStateManager<DHDPegasusBE, DHDPegasusRendererState> {
    public DHDPegasusStateManager(DHDPegasusBE dhdPegasusBE) {
        super(dhdPegasusBE);
    }

    @Override
    protected DHDButtonsState generateButtonsState() {
        return new DHDButtonsState(this, dhd.getSymbolType(), (symbol) -> {
            if (symbol.brb())
                return "pegasus/dhd/dhd_bbb_";
            return "pegasus/dhd/dhd_button_light_";
        });
    }

    @Override
    protected DHDPegasusRendererState createRendererStateClient() {
        return new DHDPegasusRendererState();
    }
}

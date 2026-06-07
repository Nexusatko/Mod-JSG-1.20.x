package dev.tauri.jsg.common.dialhomedevice.manager.state;

import dev.tauri.jsg.client.renderer.blockentity.dialhomedevice.DHDMilkyWayRendererState;
import dev.tauri.jsg.common.blockentity.dialhomedevice.DHDMilkyWayBE;
import dev.tauri.jsg.common.dialhomedevice.animation.DHDButtonsState;

public class DHDMilkyWayStateManager extends DHDAbstractStateManager<DHDMilkyWayBE, DHDMilkyWayRendererState> {
    public DHDMilkyWayStateManager(DHDMilkyWayBE dhdMilkyWayBE) {
        super(dhdMilkyWayBE);
    }

    @Override
    protected DHDButtonsState generateButtonsState() {
        return new DHDButtonsState(this, dhd.getSymbolType(), (symbol) -> {
            if (symbol.brb())
                return "milkyway/dhd/dhd_brb_";
            return "milkyway/dhd/dhd_button_light_";
        });
    }

    @Override
    protected DHDMilkyWayRendererState createRendererStateClient() {
        return new DHDMilkyWayRendererState();
    }
}

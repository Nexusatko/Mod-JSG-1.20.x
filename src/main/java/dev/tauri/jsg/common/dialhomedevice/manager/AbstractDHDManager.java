package dev.tauri.jsg.common.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.StargateDHD;

public class AbstractDHDManager<DHD extends StargateDHD> {
    public final DHD dhd;

    public AbstractDHDManager(DHD dhd) {
        this.dhd = dhd;
    }
}

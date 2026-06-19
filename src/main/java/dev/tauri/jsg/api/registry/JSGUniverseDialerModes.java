package dev.tauri.jsg.api.registry;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.item.linkable.dialer.UniverseDialerMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDManualDialMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDMemoryMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDNearbyMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDStatusMode;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JSGUniverseDialerModes {
    public static final DeferredRegister<UniverseDialerMode> REGISTER = DeferredRegister.create(JSGRegistries.UNIVERSE_DIALER_MODES, JSG.MOD_ID);

    public static final Supplier<UDNearbyMode> NEARBY = REGISTER.register("nearby", UDNearbyMode::new);
    public static final Supplier<UDMemoryMode> MEMORY = REGISTER.register("memory", UDMemoryMode::new);
    public static final Supplier<UDStatusMode> STATUS = REGISTER.register("status", UDStatusMode::new);
    public static final Supplier<UDManualDialMode> MANUAL_DIALING = REGISTER.register("manual_dialing", UDManualDialMode::new);

    public static void init() {
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}

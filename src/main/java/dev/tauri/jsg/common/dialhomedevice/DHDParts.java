package dev.tauri.jsg.common.dialhomedevice;

import net.minecraft.Util;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

public enum DHDParts {
    // TABLE(true), - the main block
    CONTROL_CRYSTALS(true),
    BUTTON_CONSOLE_WITH_BUTTONS(true),
    MAIN_CONTROL_CRYSTAL(true),
    ACTIVATION_BUTTON(false),
    NAQUADAH_TANK(false),
    UPGRADES_COVER(false);

    /**
     * If true - DHD needs to have this part to be operational
     */
    public final boolean mandatory;

    DHDParts(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public static final LinkedList<DHDParts> SEQUENCE = Util.make(new LinkedList<>(), list -> {
        list.addLast(CONTROL_CRYSTALS);
        list.addLast(BUTTON_CONSOLE_WITH_BUTTONS);
        list.addLast(MAIN_CONTROL_CRYSTAL);
        list.addLast(ACTIVATION_BUTTON);
        list.addLast(NAQUADAH_TANK);
        list.addLast(UPGRADES_COVER);
    });

    public static Optional<DHDParts> getNextPart(Predicate<DHDParts> isAssembledPredicate) {
        return SEQUENCE.stream().filter((part) -> !isAssembledPredicate.test(part)).findFirst();
    }
}

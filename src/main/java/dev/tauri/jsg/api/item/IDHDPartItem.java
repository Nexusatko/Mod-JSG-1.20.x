package dev.tauri.jsg.api.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;

import java.util.List;

public interface IDHDPartItem {
    default Item self() {
        return (Item) this;
    }

    boolean isMandatory();

    int getRaycasterButtonID();

    List<? extends IDHDPartItem> getPartsNeededAssembledBeforeAssembly();

    List<? extends IDHDPartItem> getPartsNeededRemovedBeforeAssembly();

    List<? extends IDHDPartItem> getPartsNeededToRemoveBeforeRemoval();

    default SoundEvent getAssembleSound() {
        return SoundEvents.METAL_PLACE;
    }

    default SoundEvent getDisassembleSound() {
        return SoundEvents.ITEM_PICKUP;
    }
}

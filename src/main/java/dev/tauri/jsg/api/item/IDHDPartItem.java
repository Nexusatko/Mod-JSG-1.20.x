package dev.tauri.jsg.api.item;

import net.minecraft.world.item.Item;

import java.util.List;

public interface IDHDPartItem {
    default Item self() {
        return (Item) this;
    }

    boolean isMandatory();

    int getRaycasterButtonID();

    List<? extends IDHDPartItem> getPartsNeededAssembledBeforeAssembly();

    List<? extends IDHDPartItem> getPartsNeededToRemoveBeforeRemoval();
}

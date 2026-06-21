package dev.tauri.jsg.common.blockentity.dialhomedevice;

import dev.tauri.jsg.api.registry.JSGSymbolTypes;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolMilkyWayEnum;
import dev.tauri.jsg.common.dialhomedevice.DHDParts;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDAbstractStateManager;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDMilkyWayStateManager;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsg.common.registry.JSGSoundEvents;
import dev.tauri.jsg.common.registry.tags.JSGBlockTags;
import dev.tauri.jsg.core.common.sound.ISoundEvent;
import dev.tauri.jsg.core.common.symbol.SymbolType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DHDMilkyWayBE extends DHDAbstractBE {
    public DHDMilkyWayBE(BlockPos pos, BlockState state) {
        super(JSGBlockEntities.DHD_MILKYWAY.get(), pos, state);
    }

    @Override
    protected DHDAbstractStateManager<?, ?> createStateManager() {
        return new DHDMilkyWayStateManager(this);
    }

    @Override
    public SymbolType<SymbolMilkyWayEnum> getSymbolType() {
        return JSGSymbolTypes.MILKYWAY.get();
    }

    @Override
    public Item getControlCrystal() {
        return JSGItems.CRYSTAL_CONTROL_MILKYWAY_DHD.get();
    }

    @Override
    public TagKey<Block> getLinkableBlocks() {
        return JSGBlockTags.DHD_MILKYWAY_LINKABLE_BLOCKS;
    }

    @Override
    public ISoundEvent getButtonPressSound() {
        return JSGSoundEvents.DHD_MILKYWAY_PRESS;
    }

    @Override
    public ISoundEvent getBRBPressSound() {
        return JSGSoundEvents.DHD_MILKYWAY_PRESS_BRB;
    }

    @Override
    @NotNull
    public Item getPartItem(DHDParts part) {
        return JSGItems.MILKYWAY_DHD_PARTS.get(part).get();
    }
}

package dev.tauri.jsg.common.blockentity.dialhomedevice;

import dev.tauri.jsg.api.registry.JSGSymbolTypes;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolPegasusEnum;
import dev.tauri.jsg.common.dialhomedevice.DHDParts;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDAbstractStateManager;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDPegasusStateManager;
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

public class DHDPegasusBE extends DHDAbstractBE {
    public DHDPegasusBE(BlockPos pos, BlockState state) {
        super(JSGBlockEntities.DHD_PEGASUS.get(), pos, state);
    }

    @Override
    protected DHDAbstractStateManager<?, ?> createStateManager() {
        return new DHDPegasusStateManager(this);
    }

    @Override
    public SymbolType<SymbolPegasusEnum> getSymbolType() {
        return JSGSymbolTypes.PEGASUS.get();
    }

    @Override
    public Item getControlCrystal() {
        return JSGItems.CRYSTAL_CONTROL_PEGASUS_DHD.get();
    }

    @Override
    public TagKey<Block> getLinkableBlocks() {
        return JSGBlockTags.DHD_PEGASUS_LINKABLE_BLOCKS;
    }

    @Override
    public ISoundEvent getButtonPressSound() {
        return JSGSoundEvents.DHD_PEGASUS_PRESS;
    }

    @Override
    public ISoundEvent getBRBPressSound() {
        return JSGSoundEvents.DHD_PEGASUS_PRESS_BRB;
    }

    @Override
    @NotNull
    public Item getPartItem(DHDParts part) {
        return JSGItems.PEGASUS_DHD_PARTS.get(part).get();
    }
}

package dev.tauri.jsg.client.renderer.blockentity.dialhomedevice;

import dev.tauri.jsg.common.dialhomedevice.DHDParts;
import dev.tauri.jsg.core.common.entity.BiomeOverlayInstance;
import dev.tauri.jsg.core.common.entity.State;
import dev.tauri.jsg.core.common.registry.CoreBiomeOverlays;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DHDAbstractRendererState extends State {
    public DHDAbstractRendererState() {
        super();
    }

    public BiomeOverlayInstance biomeOverlay = CoreBiomeOverlays.NORMAL.get();
    public final List<DHDParts> assembledParts = new ArrayList<>();

    public BiomeOverlayInstance getBiomeOverlay() {
        if (biomeOverlay == null) return CoreBiomeOverlays.NORMAL.get();
        return biomeOverlay;
    }

    public boolean isAssembled(DHDParts part) {
        return assembledParts.contains(part);
    }

    public boolean isAssembled() {
        return Arrays.stream(DHDParts.values()).filter(DHDParts::isMandatory).allMatch(this::isAssembled);
    }

    @Override
    public void toBytes(ByteBuf buff) {
        var buf = new FriendlyByteBuf(buff);
        if (biomeOverlay != null) {
            buf.writeBoolean(true);
            buf.writeResourceLocation(biomeOverlay.getId());
        } else
            buf.writeBoolean(false);
        buf.writeInt(assembledParts.size());
        assembledParts.forEach(p -> buf.writeInt(p.ordinal()));
    }

    @Override
    public void fromBytes(ByteBuf buff) {
        var buf = new FriendlyByteBuf(buff);
        if (buf.readBoolean()) {
            biomeOverlay = BiomeOverlayInstance.byId(buf.readResourceLocation());
        }
        assembledParts.clear();
        var size = buf.readInt();
        for (int i = 0; i < size; i++)
            assembledParts.add(DHDParts.values()[buf.readInt()]);
    }
}
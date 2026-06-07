package dev.tauri.jsg.client.renderer.blockentity.dialhomedevice;

import dev.tauri.jsg.core.common.entity.BiomeOverlayInstance;
import dev.tauri.jsg.core.common.entity.State;
import dev.tauri.jsg.core.common.registry.CoreBiomeOverlays;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;

public abstract class DHDAbstractRendererState extends State {
    public DHDAbstractRendererState() {
        super();
    }

    public BiomeOverlayInstance biomeOverlay = CoreBiomeOverlays.NORMAL.get();

    public BiomeOverlayInstance getBiomeOverlay() {
        if(biomeOverlay == null) return CoreBiomeOverlays.NORMAL.get();
        return biomeOverlay;
    }

    @Override
    public void toBytes(ByteBuf buff) {
        var buf = new FriendlyByteBuf(buff);
        if (biomeOverlay != null) {
            buf.writeBoolean(true);
            buf.writeResourceLocation(biomeOverlay.getId());
        } else
            buf.writeBoolean(false);
    }

    @Override
    public void fromBytes(ByteBuf buff) {
        var buf = new FriendlyByteBuf(buff);
        if (buf.readBoolean()) {
            biomeOverlay = BiomeOverlayInstance.byId(buf.readResourceLocation());
        }
    }
}
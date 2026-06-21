package dev.tauri.jsg.common.packet.packets.stargate;

import dev.tauri.jsg.api.item.IDHDPartItem;
import dev.tauri.jsg.common.blockentity.dialhomedevice.DHDAbstractBE;
import dev.tauri.jsg.common.dialhomedevice.DHDParts;
import dev.tauri.jsg.core.common.packet.packets.PositionedPacket;
import dev.tauri.jsg.core.common.registry.CoreItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class DHDAssemblyClickToServer extends PositionedPacket {
    int buttonId;

    public DHDAssemblyClickToServer() {
    }

    public DHDAssemblyClickToServer(BlockPos pos, int buttonId) {
        super(pos);
        this.buttonId = buttonId;
    }

    public DHDAssemblyClickToServer(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(buttonId);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buf) {
        super.fromBytes(buf);
        buttonId = buf.readInt();
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        if (ctx.getDirection() != NetworkDirection.PLAY_TO_SERVER) return;
        ctx.setPacketHandled(true);
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        Level world = player.level();
        ctx.enqueueWork(() -> {
            DHDAbstractBE dhdTile = (DHDAbstractBE) world.getBlockEntity(pos);
            if (dhdTile == null) return;
            var partId = buttonId - 100;
            if (partId < 0) return;

            var itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            DHDParts itemPart = null;
            var disassemble = itemStack.is(CoreItems.JSG_SCREWDRIVER.get());
            if (!disassemble) {
                if (itemStack.getItem() instanceof IDHDPartItem dhdPartItem) {
                    itemPart = dhdPartItem.getDHDPart();
                }
            }

            if (itemPart == null && !disassemble) return;


            if (partId < 1) {
                // buttons console, crystals
                if (itemPart != null && itemPart != DHDParts.CONTROL_CRYSTALS && itemPart != DHDParts.BUTTON_CONSOLE_WITH_BUTTONS)
                    return;
                if (itemPart == null) {
                    if (dhdTile.isAssembled(DHDParts.BUTTON_CONSOLE_WITH_BUTTONS))
                        itemPart = DHDParts.BUTTON_CONSOLE_WITH_BUTTONS;
                    else
                        itemPart = DHDParts.CONTROL_CRYSTALS;
                }
            } else if (partId < 2) {
                // main control crystal
                if (itemPart != null && itemPart != DHDParts.MAIN_CONTROL_CRYSTAL) return;
                itemPart = DHDParts.MAIN_CONTROL_CRYSTAL;
            } else if (partId < 7) {
                // upgrade crystals
                // TODO: implement this
                return;
            } else if (partId < 8) {
                // naquadah tank
                if (itemPart != null && itemPart != DHDParts.NAQUADAH_TANK) return;
                itemPart = DHDParts.NAQUADAH_TANK;
            } else {
                // upgrades cover
                if (itemPart != null && itemPart != DHDParts.UPGRADES_COVER) return;
                itemPart = DHDParts.UPGRADES_COVER;
            }
            player.swing(InteractionHand.MAIN_HAND);
            dhdTile.handleAssembleRequestFromClient(itemPart, disassemble, player, itemStack);
        });
    }
}

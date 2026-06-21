package dev.tauri.jsg.client.renderer.blockentity.dialhomedevice;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.api.registry.JSGSymbolTypes;
import dev.tauri.jsg.api.stargate.StargatePointOfOriginsDefaults;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolPegasusEnum;
import dev.tauri.jsg.common.dialhomedevice.DHDParts;
import dev.tauri.jsg.common.dialhomedevice.animation.DHDButtonsState;
import dev.tauri.jsg.common.loader.ElementEnum;
import dev.tauri.jsg.common.raycaster.RaycasterPegasusDHD;
import dev.tauri.jsg.common.registry.JSGBlocks;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsg.common.registry.JSGRaycasters;
import dev.tauri.jsg.core.common.raycaster.Raycaster;
import dev.tauri.jsg.core.common.raycaster.util.RayCastedButton;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DHDPegasusRenderer extends DHDAbstractRenderer<DHDPegasusRendererState> {
    public DHDPegasusRenderer(BlockEntityRendererProvider.Context ignored) {
        super(ignored);
    }

    @Override
    public List<RayCastedButton> getRaycasterButtons() {
        if (rendererState == null) return List.of();
        if (!rendererState.isAssembled(DHDParts.BUTTON_CONSOLE_WITH_BUTTONS)) {
            return RaycasterPegasusDHD.BUTTONS.stream()
                    .filter(btn -> btn.buttonId >= 100)
                    .toList();
        }
        if (!rendererState.isAssembled(DHDParts.ACTIVATION_BUTTON)) {
            return RaycasterPegasusDHD.BUTTONS.stream()
                    .filter(btn -> btn.buttonId != tileEntity.getSymbolType().getBRB().getId())
                    .toList();
        }
        return RaycasterPegasusDHD.BUTTONS;
    }

    @Override
    public Raycaster getRaycaster() {
        return JSGRaycasters.PEGASUS_DHD_RAYCASTER.get();
    }

    @Override
    public void renderSymbols(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, DHDButtonsState buttonsState) {
        CompoundTag compound = getNoteBookPage();

        for (SymbolPegasusEnum symbol : SymbolPegasusEnum.values()) {
            if (symbol.brb() && !rendererState.isAssembled(DHDParts.BUTTON_CONSOLE_WITH_BUTTONS))
                continue;
            poseStack.pushPose();
            var btnColor = getColorByAddress(rendererState, compound, JSGSymbolTypes.PEGASUS.get(), symbol);
            var btnTex = buttonsState.get(symbol).getTexture(rendererState.getBiomeOverlay());
            JSGApi.JSG_LOADERS_HOLDER.texture().getTexture(btnTex).bindTexture();
            symbol.getModel(symbol.getSymbolType().getPointOfOriginType(), tileEntity.getPointOfOrigin(), StargatePointOfOriginsDefaults.VARIANT_DHD_LIGHT).render(poseStack, bufferSource, combinedLight, combinedOverlay, buttonsState.get(symbol).isActive(), btnColor.x, btnColor.y, btnColor.z, 1f, true);
            if (symbol.brb())
                ElementEnum.PEGASUS_DHD_BASE.bindTexture(rendererState.getBiomeOverlay());
            symbol.getModel(symbol.getSymbolType().getPointOfOriginType(), tileEntity.getPointOfOrigin(), StargatePointOfOriginsDefaults.VARIANT_DHD).render(poseStack, bufferSource, combinedLight, combinedOverlay, false, btnColor.x, btnColor.y, btnColor.z, 1f, true);
            poseStack.popPose();
        }
    }

    @Override
    public void renderDHD(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        ElementEnum.PEGASUS_DHD_BASE.bindTexture(rendererState.getBiomeOverlay()).render(poseStack, bufferSource, combinedLight, combinedOverlay);
        ElementEnum.PEGASUS_DHD_CRYSTAL_HOLDER.render(poseStack, bufferSource, combinedLight, combinedOverlay);

        /*
        ElementEnum.PEGASUS_DHD_BUTTON_CONSOLE.render(poseStack, bufferSource, combinedLight, combinedOverlay);
        ElementEnum.PEGASUS_DHD_UPGRADE_COVER.render(poseStack, bufferSource, combinedLight, combinedOverlay);
        ElementEnum.PEGASUS_DHD_UPGRADE_CRYSTAL.bindTexture(rendererState.getBiomeOverlay()).render(poseStack, bufferSource, combinedLight, combinedOverlay);

        ElementEnum.PEGASUS_DHD_CRYSTALS.bindTexture(rendererState.getBiomeOverlay()).render(poseStack, bufferSource, combinedLight, combinedOverlay);
        if (!tileEntity.getItemStackHandler().getStackInSlot(0).isEmpty())
            ElementEnum.PEGASUS_DHD_CONTROL_CRYSTAL.render(poseStack, bufferSource, combinedLight, combinedOverlay);

        ElementEnum.PEGASUS_DHD_FLUID_TANK_BASE.bindTexture(rendererState.getBiomeOverlay()).render(poseStack, bufferSource, combinedLight, combinedOverlay);
        RenderSystem.enableBlend();
        TextureAtlasSprite sprite = BlockRenderer.getFluidTexture(new FluidStack(CoreFluids.MOLTEN_NAQUADAH_REFINED.still.get(), 1000), BlockRenderer.FluidTextureType.STILL);
        if (sprite != null) {
            ITexture.bindTextureWithMc(sprite.atlasLocation());
            ElementEnum.PEGASUS_DHD_FLUID_TANK_FLUID.render(poseStack, bufferSource, combinedLight, combinedOverlay, sprite);
        }
        ElementEnum.PEGASUS_DHD_FLUID_TANK_GLASS.bindTexture(rendererState.getBiomeOverlay()).render(poseStack, bufferSource, combinedLight, combinedOverlay);
        RenderSystem.disableBlend();*/
    }

    @Override
    public Block getDHDBlock() {
        return JSGBlocks.DHD_PEGASUS.get();
    }

    @Override
    public @NotNull PartRenderable getPartModelRenderable(DHDParts part) {
        return (poseStack, bufferSource, combinedLight, combinedOverlay, partialTick, r, g, b, alpha, assembled) -> {

        };
    }

    @Override
    public Item getNeededSchematic() {
        return JSGItems.SCHEMATIC_PEGASUS.get();
    }
}

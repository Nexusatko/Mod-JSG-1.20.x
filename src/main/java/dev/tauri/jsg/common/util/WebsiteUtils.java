package dev.tauri.jsg.common.util;

import dev.tauri.jsg.JSG;
import net.minecraft.Util;

public class WebsiteUtils {
    public static void openWebsiteToClient(String url) {
        try {
            Util.getPlatform().openUri(url);
        } catch (Exception e) {
            JSG.logger.error("Couldn't open link", e);
        }
    }
}

package com.autoglow;

import net.fabricmc.api.ModInitializer;

public class AutoGlow implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("AutoGlow carregado!");
        GlowConfig.get();

        GlowConfig.load();

        GlowToggleManager.setEnabled(GlowConfig.get().enable);

        int color = GlowConfig.get().color;

        GlowColorManager.setRGB(
                (color >> 16) & 0xFF,
                (color >> 8) & 0xFF,
                color & 0xFF
        );
    }
}

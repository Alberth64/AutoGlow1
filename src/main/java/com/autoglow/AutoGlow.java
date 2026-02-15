package com.autoglow;

import net.fabricmc.api.ModInitializer;

public class AutoGlow implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("AutoGlow carregado!");
        GlowConfig.load();
        GlowColorCommand.register();
    }
}

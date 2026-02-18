package com.autoglow;

import com.autoglow.command.GlowCommand;
import net.fabricmc.api.ClientModInitializer;

public class AutoGlowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        GlowCommand.register();

    }
}

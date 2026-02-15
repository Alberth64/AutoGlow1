package com.autoglow;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class GlowRenderer {

    public static void register() {
        // Não precisamos mais usar WorldRenderEvents
        // Apenas controlamos o estado de glow no tick
    }

    public static void applyGlow(PlayerEntity player) {
        if (player == null) return;

        player.setGlowing(true);
    }

    public static void removeGlow(PlayerEntity player) {
        if (player == null) return;

        player.setGlowing(false);
    }
}

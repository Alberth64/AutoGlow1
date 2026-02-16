package com.autoglow.mixin;

import com.autoglow.GlowColorManager;
import com.autoglow.GlowConfig;
import com.autoglow.GlowToggleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatMixin {

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void onSendMessage(String message, boolean addToHistory, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        // ===== TOGGLE =====
        if (message.equalsIgnoreCase(".glow on")) {
            GlowToggleManager.setEnabled(true);
            GlowConfig.get().enable = true;
            GlowConfig.save();
            client.player.sendMessage(
                    Text.literal("Glow ativado!").formatted(Formatting.GREEN),
                    false
            );
            ci.cancel();
            return;
        }

        if (message.equalsIgnoreCase(".glow off")) {
            GlowToggleManager.setEnabled(false);
            GlowConfig.get().enable = false;
            GlowConfig.save();
            client.player.sendMessage(
                    Text.literal("Glow desativado!").formatted(Formatting.RED),
                    false
            );
            ci.cancel();
            return;
        }

        // ===== GLOWCOLOR =====
        if (message.startsWith(".glowcolor ")) {

            String[] args = message.split(" ");

            // HEX ou Nome
            if (args.length == 2) {

                String input = args[1];

                // HEX
                if (input.startsWith("#")) {
                    try {
                        GlowColorManager.setHex(input);
                        GlowConfig.get().color = GlowColorManager.getColorValue();
                        GlowConfig.save();
                        client.player.sendMessage(
                                Text.literal("Cor alterada para " + input)
                                        .formatted(Formatting.AQUA),
                                false
                        );
                    } catch (Exception e) {
                        sendError(client);
                    }
                }
                // Nome
                else {
                    boolean success = GlowColorManager.setNamed(input);
                    GlowConfig.get().color = GlowColorManager.getColorValue();
                    GlowConfig.save();
                    if (success) {
                        client.player.sendMessage(
                                Text.literal("Cor alterada para " + input)
                                        .formatted(Formatting.AQUA),
                                false
                        );
                    } else {
                        sendError(client);
                    }
                }
            }

            // RGB
            else if (args.length == 4) {
                try {
                    int r = Integer.parseInt(args[1]);
                    int g = Integer.parseInt(args[2]);
                    int b = Integer.parseInt(args[3]);

                    GlowColorManager.setRGB(r, g, b);
                    GlowConfig.get().color = GlowColorManager.getColorValue();
                    GlowConfig.save();
                    client.player.sendMessage(
                            Text.literal("Cor alterada para RGB (" + r + ", " + g + ", " + b + ")")
                                    .formatted(Formatting.AQUA),
                            false
                    );
                } catch (NumberFormatException e) {
                    sendError(client);
                }
            }

            else {
                sendError(client);
            }

            ci.cancel();
        }
    }

    private void sendError(MinecraftClient client) {
        client.player.sendMessage(
                Text.literal("Uso: .glowcolor <R G B | #HEX | nome>")
                        .formatted(Formatting.RED),
                false
        );
    }
}

package com.autoglow.command;

import com.autoglow.GlowColorManager;
import com.autoglow.GlowConfig;
import com.autoglow.GlowToggleManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class GlowCommand {

    public static void register() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            dispatcher.register(
                    ClientCommandManager.literal("glow")

                            // /glow on
                            .then(ClientCommandManager.literal("on")
                                    .executes(context -> {
                                        GlowToggleManager.setEnabled(true);
                                        GlowConfig.get().enable = true;
                                        GlowConfig.save();
                                        sendMessage("§aGlow ligado!");
                                        return 1;
                                    })
                            )

                            // /glow off
                            .then(ClientCommandManager.literal("off")
                                    .executes(context -> {
                                        GlowToggleManager.setEnabled(false);
                                        GlowConfig.get().enable = false;
                                        GlowConfig.save();
                                        sendMessage("§cGlow desligado!");
                                        return 1;
                                    })
                            )

                            // /glow color <valor>
                            .then(ClientCommandManager.literal("color")
                                    .then(ClientCommandManager.argument("value", StringArgumentType.greedyString())
                                            .executes(context -> {

                                                String input = StringArgumentType.getString(context, "value").trim();
                                                String[] parts = input.split(" ");

                                                // ===== RGB =====
                                                if (parts.length == 3) {
                                                    try {
                                                        int r = Integer.parseInt(parts[0]);
                                                        int g = Integer.parseInt(parts[1]);
                                                        int b = Integer.parseInt(parts[2]);

                                                        GlowColorManager.setRGB(r, g, b);

                                                        GlowConfig.get().color = GlowColorManager.getColorValue();
                                                        GlowConfig.save();

                                                        sendMessage("§dGlow RGB: §f" + r + " " + g + " " + b);
                                                        return 1;
                                                    } catch (Exception ignored) {}
                                                }

                                                // ===== HEX =====
                                                String hex = input.replace("#", "");
                                                if (hex.matches("[0-9a-fA-F]{6}")) {

                                                    GlowColorManager.setHex(hex);

                                                    GlowConfig.get().color = GlowColorManager.getColorValue();
                                                    GlowConfig.save();

                                                    sendMessage("§dGlow HEX: §f#" + hex.toUpperCase());
                                                    return 1;
                                                }

                                                // ===== NAME =====
                                                if (GlowColorManager.setNamed(input)) {

                                                    GlowConfig.get().color = GlowColorManager.getColorValue();
                                                    GlowConfig.save();

                                                    sendMessage("§dGlow Name: §f" + input.toLowerCase());
                                                    return 1;
                                                }

                                                sendMessage("§cCor inválida! Use RGB, HEX ou nome.");
                                                return 0;
                                            })
                                    )
                            )
            );

        });
    }

    private static void sendMessage(String message) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.literal(message), false);
        }
    }
}

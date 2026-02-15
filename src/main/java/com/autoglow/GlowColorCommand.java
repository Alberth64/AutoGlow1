package com.autoglow;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GlowColorCommand {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(
                    CommandManager.literal("glowcolor")
                            .then(CommandManager.argument("color", StringArgumentType.word())
                                    .executes(context -> {

                                        String input = StringArgumentType.getString(context, "color").toUpperCase();

                                        try {
                                            Formatting color = Formatting.valueOf(input);
                                            GlowColorManager.setColor(color);

                                            context.getSource().sendFeedback(
                                                    () -> Text.literal("Glow alterado para ")
                                                            .append(Text.literal(input).formatted(color)),
                                                    false
                                            );

                                        } catch (IllegalArgumentException e) {
                                            context.getSource().sendError(
                                                    Text.literal("Cor inválida.")
                                            );
                                        }

                                        return 1;
                                    })

                            )
            );
        });
    }
}

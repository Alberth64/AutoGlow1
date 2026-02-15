package com.autoglow.mixin;

import com.autoglow.GlowColorManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void interceptCommand(String message, CallbackInfo ci) {

        if (message.startsWith(".glowcolor ")) {

            String colorName = message.substring(11).toUpperCase();

            try {
                Formatting color = Formatting.valueOf(colorName);
                GlowColorManager.setColor(color);

                System.out.println("Glow alterado para " + colorName);

            } catch (IllegalArgumentException e) {
                System.out.println("Cor inválida.");
            }

            ci.cancel(); // impede envio ao servidor
        }
    }
}

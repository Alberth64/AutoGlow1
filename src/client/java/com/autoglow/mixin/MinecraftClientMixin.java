package com.autoglow.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void forceOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {

        MinecraftClient client = (MinecraftClient) (Object) this;

        if (client.player == null) return;

        // Só aplica no próprio jogador
        if (entity == client.player) {
            cir.setReturnValue(true);
        }
    }
}
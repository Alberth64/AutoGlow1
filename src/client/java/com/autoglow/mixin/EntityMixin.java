package com.autoglow.mixin;

import com.autoglow.GlowColorManager;
import com.autoglow.GlowToggleManager;
import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "getTeamColorValue", at = @At("HEAD"), cancellable = true)
    private void forceGlowColor(CallbackInfoReturnable<Integer> cir) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        if ((Object)this == client.player) {

            if (!GlowToggleManager.isEnabled()) return;

            cir.setReturnValue(GlowColorManager.getColorValue());
        }
    }
}

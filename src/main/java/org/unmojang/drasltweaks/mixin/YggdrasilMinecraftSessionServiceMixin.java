package org.unmojang.drasltweaks.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(YggdrasilMinecraftSessionService.class)
public abstract class YggdrasilMinecraftSessionServiceMixin {
    @Inject(method="isAllowedTextureDomain", at=@At("HEAD"), remap=false, cancellable=true)
    private static void invokeYggdrasilServicesKeyInfo(String url, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
package org.unmojang.drasltweaks.mixin;

import com.mojang.authlib.yggdrasil.TextureUrlChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureUrlChecker.class)
public abstract class TextureUrlCheckerMixin {
    @Inject(method="isAllowedTextureDomain", at=@At("HEAD"), remap=false, cancellable=true)
    private static void injectIsAllowedTextureDomain(String url, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
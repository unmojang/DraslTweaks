package org.unmojang.drasltweaks.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.security.PublicKey;

@Mixin(YggdrasilServicesKeyInfo.class)
public interface YggdrasilServicesKeyInfoMixin {
    @Invoker("<init>")
    static YggdrasilServicesKeyInfo invokeYggdrasilServicesKeyInfo(PublicKey publicKey) {
        return null;
    }
}
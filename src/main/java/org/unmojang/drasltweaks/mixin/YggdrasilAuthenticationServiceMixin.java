package org.unmojang.drasltweaks.mixin;

import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.response.Response;
import org.unmojang.drasltweaks.DraslGetInfoResponse;
import org.unmojang.drasltweaks.DraslTweaks;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.ServicesKeyInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;

import java.net.Proxy;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.mojang.authlib.Environment;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(YggdrasilAuthenticationService.class)
public abstract class YggdrasilAuthenticationServiceMixin {

	@Shadow(remap=false) @Mutable
	private ServicesKeyInfo servicesKey;

	@Shadow protected abstract <T extends Response> T makeRequest(URL url, Object input, Class<T> classOfT) throws AuthenticationException;

	@Inject(method="<init>(Ljava/net/Proxy;Ljava/lang/String;Lcom/mojang/authlib/Environment;)V", at=@At("TAIL"), remap=false)
	public void YggdrasilAuthenticationService(Proxy proxy, String clientToken, Environment environment, CallbackInfo ci) {
		try {
			URL url = HttpAuthenticationService.constantURL(environment.getAuthHost());
			DraslGetInfoResponse response = makeRequest(url, null, DraslGetInfoResponse.class);

			String publicKeyString = response.getPublicKey();
			if (publicKeyString == null || publicKeyString == "") {
				DraslTweaks.LOGGER.info("Got empty public key, not patching servicesKey");
			} else {
				final byte[] keyBytes = Base64.getDecoder().decode(response.getPublicKey());

				final X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
				final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				final PublicKey publicKey = keyFactory.generatePublic(spec);

				servicesKey = YggdrasilServicesKeyInfoMixin.invokeYggdrasilServicesKeyInfo(publicKey);
				DraslTweaks.LOGGER.info("servicesKey:" + servicesKey.toString());
			}
		} catch (Exception e) {
			DraslTweaks.LOGGER.error("Failed to patch servicesKey!");
			e.printStackTrace();
		}
	}
}

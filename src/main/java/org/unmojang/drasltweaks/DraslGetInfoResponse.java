package org.unmojang.drasltweaks;

import com.mojang.authlib.yggdrasil.response.Response;

public class DraslGetInfoResponse extends Response {
    private String PublicKey;
    public String getPublicKey() {
        return PublicKey;
    }
}

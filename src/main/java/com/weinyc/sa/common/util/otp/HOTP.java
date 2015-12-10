package com.weinyc.sa.common.util.otp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HOTP extends AbstractOTP {

    public HOTP(String secret, int digits) {
        super(secret, digits);
    }

    public HOTP(String secret) {
        super(secret);
    }

    @Override
    public String at(long count, boolean padding) {
        return this.generateOPT(count, padding);
    }

    public boolean verify(String otp, long counter) {
        return super.verify(otp, this.at(counter));
    }

    public boolean verifyWithRetries(String otp, long initailCount, int retries) {
        if (retries < 0)
            return false;
        for (int i = 0; i < retries; i++) {
            if (this.verify(otp, initailCount + i)) {
                return true;
            }
        }
        return false;
    }

    public String provisioningURI(String name) {
        return this.provisioningURI(name, 0);
    }

    public String provisioningURI(String name, long initailCount) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("secret", this.getSecret());
        params.put("counter", initailCount + "");
        try {
            return this.encodeParams("otpauth://hotp/" + URLEncoder.encode(name, "UTF-8"), params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return this.encodeParams("otpauth://hotp/" + name, params);
        }
    }
}

package com.weinyc.sa.common.util.otp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
public class AbstractOTP {
    private final String secret;
    private int digits = 6;
    private String digest = "HmacSHA1";

    public AbstractOTP(String secret) {
        super();
        this.secret = secret;
    }

    public AbstractOTP(String secret, int digits) {
        super();
        this.secret = secret;
        this.digits = digits;
    }

    public String at(long count) {
        return this.at(count, true);
    }

    public String at(long count, boolean padding) {
        throw new UnsupportedOperationException();
    }

    private byte[] byteSecret() {
        return Base32.decode(this.secret);
    }

    protected String encodeParams(String uri, Map<String, String> params) {
        String par = "?";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null)
                continue;
            par = par + entry.getKey() + "=" + entry.getValue() + "&";
        }
        if(par.charAt(par.length() - 1) == '&'){
            par = par.substring(0, par.length() - 1);
        }
        return uri + par;
    }

    public String generateOPT(long input) {
        return this.generateOPT(input, true);
    }

    public String generateOPT(long input, boolean padded) {
        char[] hmac = hmac(input);
        int offset = hmac[hmac.length - 1] & 0xF;
        int code = (hmac[offset] & 0x7F) << 24 | (hmac[offset + 1] & 0xFF) << 16 | (hmac[offset + 2] & 0xFF) << 8 | (hmac[offset + 3] & 0xFF);
        int pow = (int) Math.pow(10, this.digits);
        int value = code % (pow);
        if (padded) {
            return (value + pow + "").substring(1);
        } else {
            return value + "";
        }
    }

    public String getDigest() {
        return digest;
    }

    public int getDigits() {
        return digits;
    }

    public String getSecret() {
        return secret;
    }

    private char[] hmac(long input) {
        try {
            Mac hmacSha1;
            try {
                hmacSha1 = Mac.getInstance(digest);
            } catch (Exception nsae) {
                hmacSha1 = Mac.getInstance("HMAC-SHA-1");
            }
            SecretKeySpec macKey = new SecretKeySpec(byteSecret(), /* "HmacSHA1" */digest);
            hmacSha1.init(macKey);
            byte[] hash = hmacSha1.doFinal(toByte(input));
            char[] hashChar = new char[hash.length];
            for (int i = 0; i < hash.length; i++) {
                hashChar[i] =   hash[i] < 0 ? (char) (hash[i] + 256) : (char) hash[i];
            }
            return hashChar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String provisioningURI(String name, long initailCount) {
        throw new UnsupportedOperationException();
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    private boolean timeConstantCompare(String a, String b) {
        return a.equals(b);
    }

    private static byte[] toByte(long val) {
        return toByte(val, 8);
    }
    private static byte[] toByte(long val, int padding) {
        List<Byte> bytes = new ArrayList<Byte>();
        while (val != 0) {
            bytes.add((byte) (val & 0xFF));
            val >>= 8;
        }
        while (bytes.size() < padding) {
            bytes.add((byte)0);
        }
        Collections.reverse(bytes);
        byte[] ar = new byte[bytes.size()];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = bytes.get(i);
        }
        return ar;
    }
    protected boolean verify(String input, String generated) {
        return timeConstantCompare(input, generated);
    }

}

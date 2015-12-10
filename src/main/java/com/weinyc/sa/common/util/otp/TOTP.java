package com.weinyc.sa.common.util.otp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class TOTP extends AbstractOTP {

    private int interval = 30;
    private String issuer;
    public TOTP(String secret) {
        super(secret);
    }

    public TOTP(String secret,   int digits, int interval) {
        super(secret, digits);
        this.interval = interval;
    }

    public String at(boolean padding) {
        return this.at(utc(), padding);
    }

    public static long utc(){
        return System.currentTimeMillis() / 1000;
    }
    
    @Override
    public String at(long utc, boolean padding) {
        return this.generateOPT(utc / this.interval, padding);
    }

    public int getInterval() {
        return interval;
    }
 
    public String getIssuer() {
        return issuer;
    }

    public String now() {
        return this.at(true);
    }

    public String now(boolean padding) {
        return this.at(padding);
    }

    @Override
    public String provisioningURI(String name, long o){
        return this.provisioningURI(name);
    }
    
    public String provisioningURI(String name){
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("secret", this.getSecret());
        params.put("period", this.getInterval()+"");
        params.put("issuer", this.getIssuer());
        try {
            return this.encodeParams("otpauth://totp/"+ URLEncoder.encode(name, "UTF-8"), params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return this.encodeParams("otpauth://totp/"+name,  params);
        }
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public boolean verify(String otp) {
        return verify(otp, this.now());
    }

    public boolean verify(String otp, long utc) {
        return verify(otp, this.at(utc));
    }

    public boolean verifyWithDrift(String otp, int drift) {
        return this.verifyWithDrift(otp, drift, utc());
    }
    
    public boolean verifyWithDrift(String otp, int drift, long utc) {
        int start = 0 - drift / 2;
        int end = drift + start;
        for (int i = start; i < end; i++) {
            boolean v = this.verify(otp, utc + i * this.interval);
            if (v) {
                return true;
            }
        }
        return false;
    }
 
    public static void main(String args[]){
        String token = Base32.randomBase32();
        System.out.println(token);
        TOTP top = new TOTP(token);
        System.out.println(top.now());
        System.out.println(top.provisioningURI("hello"));
    }
}

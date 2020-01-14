package com.wonder.bring.config.security;

import java.security.MessageDigest;

/**
 * Created by bomi on 2019-01-11.
 */
public class Encryption {
    public static String encrypt(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digested = md.digest(s.getBytes());
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        }catch (Exception e) {
            return s;
        }
    }
}

package com.lincx.loginproject.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5SHAUtil {
    public static String MD5SHA(String password)throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes());
        String newpassword = new BigInteger(md.digest()).toString(32);
        return newpassword;
    }
}

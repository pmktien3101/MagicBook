/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author MSI PC
 */
public class Encode {
    //hàm mã hóa pass
    public static String toSHA1(String password){
        //lam cho mat khau phuc tap
        String salt = "lqw234r5syu1vg56frx1sdk67lqn9mpnnmzxcsiu";
        String result = null;
        password = password + salt;
        try {
            byte[] dataBytes = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.encodeBase64String(md.digest(dataBytes));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
       
        System.out.println("s: " + Encode.toSHA1("1"));
    
    }
}

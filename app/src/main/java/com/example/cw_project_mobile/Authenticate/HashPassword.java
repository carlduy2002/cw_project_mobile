package com.example.cw_project_mobile.Authenticate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class HashPassword {
    public String hashPassword(String password){
        String hashData = "";
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] inputPwd = password.getBytes();

            byte[] hashPwd = digest.digest(inputPwd);

            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : hashPwd){
                stringBuilder.append(String.format("%02x", b));
            }

            hashData = stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();;
        }

        return hashData;
    }

}


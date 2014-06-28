package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Md5 {
    private static MessageDigest md;

    public static String encrypt(String pass){
        try {
            
            MessageDigest md = MessageDigest.getInstance("MD5");
           
            BigInteger hash = new BigInteger(1, md.digest(pass.getBytes()));  
            
            String encrypt = hash.toString(16);              
            
            return encrypt; 
        } 
        catch (NoSuchAlgorithmException ex) 
        {
            Logger.getLogger(Md5.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
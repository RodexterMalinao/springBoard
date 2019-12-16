package com.bomwebportal.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
 
public class MD5Util {
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] crypt(byte[] sourceBytes, int mode, String desKey) {
        try {
             SecureRandom sr = new SecureRandom();
             DESKeySpec dks = new DESKeySpec(desKey.getBytes());
             SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
             SecretKey securekey = keyFactory.generateSecret(dks);
             Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
             cipher.init(mode, securekey, sr);
             return cipher.doFinal(sourceBytes);
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }
    
    public static byte[] file2bytes(File file) throws FileNotFoundException{
        
        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        } catch (Exception ex) {
        	
        	ex.printStackTrace();
        }
        byte[] bytes = bos.toByteArray();

        return bytes;
    }

}
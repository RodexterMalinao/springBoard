package com.bomwebportal.util;

/*
    JAVA
    
    uENC.java
    
    Class for Crypto
    
    System Utility.
    
    History
    -------
    13-Mar-08       AL              Introduction
    
    Keywords
    --------
    $URL$
    $Rev$
    $Date:   Mar 28 2012 14:26:50  $
    $Author:   1012608  $
*/


import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.text.*;
import java.net.*;
import java.security.MessageDigest;
import javax.crypto.*;
import javax.crypto.spec.*;


public class uENC
{
    public static void main(String rArg[])
    {
        String                      rVer = "$URL$, $Rev$";

        String rKey = "0123456789ABCDEF";
        String rVAL = "Helo, Cipher Test";
        String rRES;
        
        try {
            rRES = encAES(rKey, rVAL);
            System.out.println("encAES="+rRES);
            System.out.println("decAES="+decAES(rKey, rRES));
        }
        catch (Exception rEx) {
             System.out.println(rEx);
             System.out.println("AES-Failed!");
        }
        
        System.out.println(rVer);
        System.exit(0);
    }

	
	
    public static String hexPr(String rVar)
    {
        /*
            Return a String of the Hex rVar.
        */
    
        StringBuffer                rSB;
        char[]                      rCA;
        int                         ri, rsi, rdi;
        int                         rl, rsl, rdl;
        int                         rVal;
        String                      rRes;
    
        rSB = new StringBuffer();
        rCA = rVar.toCharArray();
    
        rl = rCA.length;
        for (ri=0; ri<rl; ri++) {
            rVal = rCA[ri];
            if (rVal < 0x20 || rVal > 0x7e) {
                rSB.append("<");
                if (rVal < 0x1000) rSB.append("0");
                if (rVal < 0x100)  rSB.append("0");
                if (rVal < 0x10)   rSB.append("0");
                rSB.append(Integer.toHexString(rVal));
                rSB.append(">");
            }
            else {
                rSB.append((char) rVal);
            }
        }
    
        rRes = new String(rSB.toString());
        return (rRes);
    }
    
    
    public static String hexPr(byte[] rByte)
    {
        /*
            Return a String of the Hex rByte[].
        */
    
        StringBuffer                rSB;
        int                         ri, rsi, rdi;
        int                         rl, rsl, rdl;
        int                         rVal;
    
        rSB = new StringBuffer();
        rl  = rByte.length;
    
        for (ri=0; ri<rl; ri++) {
            rVal = (int) rByte[ri];
            if (rVal < 0) rVal += 256;
    
            if (rVal < 0x20 || rVal > 0x7e) {
                rSB.append("<");
                if (rVal < 0x10) rSB.append("0");
                rSB.append(Integer.toHexString(rVal));
                rSB.append(">");
            }
            else {
                rSB.append((char) rVal);
            }
        }
    
        return (new String(rSB.toString()));
    }
    
    
    public static String by2hx(byte[] rByte)
    {
        /*
            Return a String of the Hex rByte[].
            All bytes will be converted, regardless printable or not.
        */
    
        StringBuffer                rSB;
        int                         ri, rsi, rdi;
        int                         rl, rsl, rdl;
        int                         rVal;
    
        rSB = new StringBuffer();
        rl  = rByte.length;
    
        for (ri=0; ri<rl; ri++) {
            rVal = (int) rByte[ri];
            if (rVal < 0) rVal += 256;
    
            if (rVal < 0x10) rSB.append("0");
            rSB.append(Integer.toHexString(rVal));
        }
    
        return (new String(rSB.toString()));
    }
    
    
    public static byte[] hx2by(String rStr)
    {
        /*
            Return a byte[] of the String, assuming String contains Hex digits.
            All bytes will be converted, regardless printable or not.
        */
    
        int                         ri, rsi, rdi;
        int                         rl, rsl, rdl;
        int                         rVal;
        byte                        rBy[];
        String                      rHx;
    
        rl  = rStr.length();
        if ((rl % 2) != 0) {
            throw new RuntimeException("Invalid Hex String");
        }
    
        rBy = new byte[rl / 2];
        for (ri=rdi=0; ri<rl; ri+=2) {
        	rHx = rStr.substring(ri, ri+2);
            rVal = Integer.parseInt(rHx, 16);
    
            if (rVal > 127) rVal -= 256;
            rBy[rdi++] = (byte) rVal;
        }
    
        return (rBy);
    }
    
    
    public static String encAES(String rKey, String rStr)
    {
        /*
            Encrypt a String with AES.
            Return encrypted String as hexbytes.
    
            rKey must be 16 characters.
        */
    
        byte                        rBs[];
        SecretKeySpec               rSKS;
        Cipher                      rCip;
        byte                        rBsr[], rBds[];
    
        try {
            rSKS = new SecretKeySpec(rKey.getBytes(), "AES");
            rCip = Cipher.getInstance("AES/ECB/PKCS5Padding");
            rCip.init(Cipher.ENCRYPT_MODE, rSKS);
    
            rBsr = rStr.getBytes();
            rBds = rCip.doFinal(rBsr);
    
            return (by2hx(rBds));
        }
        catch (RuntimeException rEx) {
            throw rEx;
        }
        catch (Exception rEx) {
            throw new RuntimeException(rEx);
        }
    }
    
    
    public static String decAES(String rKey, String rStr)
    {
        /*
            Decrypt a String with AES.
            rStr in the format of Hexbyte (see by2hx)
    
            rKey must be 16 characters.
        */
    
        String                      rRtn;
        SecretKeySpec               rSKS;
        Cipher                      rCip;
        byte                        rBsr[], rBds[];
    
        try {
            rSKS = new SecretKeySpec(rKey.getBytes(), "AES");
            rCip = Cipher.getInstance("AES/ECB/PKCS5Padding");
            rCip.init(Cipher.DECRYPT_MODE, rSKS);
    
            rBsr = hx2by(rStr);
            rBds = rCip.doFinal(rBsr);
    
            rRtn = hexPr(rBds);
            return (rRtn);
        }
        catch (RuntimeException rEx) {
            throw rEx;
        }
        catch (Exception rEx) {
            throw new RuntimeException(rEx);
        }
    }
    
    
    
    public static String enc3DES(String rKey, String rStr)
    {
        /*
            Encrypt a String with Triple DES
            Return encrypted String as hexbytes.
    
            rKey must be 24 characters.
        */
    
        byte                        rBs[];
        SecretKeySpec               rSKS;
        Cipher                      rCip;
        byte                        rBsr[], rBds[];
    
        try {
            rSKS = new SecretKeySpec(rKey.getBytes(), "DESede");
            rCip = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            rCip.init(Cipher.ENCRYPT_MODE, rSKS);
    
            rBsr = rStr.getBytes();
            rBds = rCip.doFinal(rBsr);
    
            return (by2hx(rBds));
        }
        catch (RuntimeException rEx) {
            throw rEx;
        }
        catch (Exception rEx) {
            throw new RuntimeException(rEx);
        }
    }
    
    
    public static String dec3DES(String rKey, String rStr)
    {
        /*
            Decrypt a String with Triple DES.
            rStr in the format of Hexbyte (see by2hx)
    
            rKey must be 24 characters.
        */
    
        String                      rRtn;
        SecretKeySpec               rSKS;
        Cipher                      rCip;
        byte                        rBsr[], rBds[];
    
        try {
            rSKS = new SecretKeySpec(rKey.getBytes(), "DESede");
            rCip = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            rCip.init(Cipher.DECRYPT_MODE, rSKS);
    
            rBsr = hx2by(rStr);
            rBds = rCip.doFinal(rBsr);
    
            rRtn = hexPr(rBds);
            return (rRtn);
        }
        catch (RuntimeException rEx) {
            throw rEx;
        }
        catch (Exception rEx) {
            throw new RuntimeException(rEx);
        }
    }
}

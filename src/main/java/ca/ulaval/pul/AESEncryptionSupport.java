/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ulaval.pul;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptionSupport {
    private String password;
    
    AESEncryptionSupport(String password) {
        this.password = password;
    }
    
    public byte[] encrypt(byte b[]) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, this.setKey());
            b = c.doFinal(b);
        } catch (NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
        } catch (NoSuchPaddingException ex) {
        	ex.printStackTrace();
        } catch (InvalidKeyException ex) {
        	ex.printStackTrace();
        } catch (IllegalBlockSizeException ex) {
        	ex.printStackTrace();
        } catch (BadPaddingException ex) {
        	ex.printStackTrace();
        }
        return b;
    }
    
    public byte[] decrypt(byte[] b) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, this.setKey());
            b = c.doFinal(b);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | 
                IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
        	ex.printStackTrace();
        }
        return b;
    }
    
    private SecretKeySpec setKey(){
        byte [] k = password.getBytes();
        SecretKeySpec key = null;
        try {
            k= MessageDigest.getInstance("SHA-1").digest(k);
            k = Arrays.copyOf(k, 16);
            key = new SecretKeySpec(k,"AES");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return key;
    }
}


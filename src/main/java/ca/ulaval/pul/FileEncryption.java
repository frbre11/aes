package ca.ulaval.pul;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ca.ulaval.pul.AES.InvalidKeyLengthException;
import ca.ulaval.pul.AES.StrongEncryptionNotAvailableException;

public class FileEncryption {
    FileEncryption(){
    }
    
    public void encrypt(String sourceFilename, String encryptedFilename, String password) throws FileNotFoundException{
    	File sourceFile = new File(sourceFilename);
        if(!sourceFile.exists()) {
        	throw new FileNotFoundException();     
        }
    	File encryptedFile = new File(sourceFile.getParent() + "\\" + encryptedFilename);
        try (
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(encryptedFile);
        )
        {
            AES aes = new AES();
            aes.encrypt(256, password, fis, fos);
        } catch (InvalidKeyLengthException e) {
			e.printStackTrace();
		} catch (StrongEncryptionNotAvailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }

    public static void main(String[] args) {
    	if (args.length == 0) {
    		System.err.println("Usage: FileEncription <filename>");
    		System.exit(-1);
    	}
    	String filename = args[0];
        FileEncryption enc = new FileEncryption();
        try {
			enc.encrypt(".\\" + filename, filename + ".encrypted", "Le fond de l'air est frais!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
    }

}

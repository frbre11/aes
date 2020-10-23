package ca.ulaval.pul;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ca.ulaval.pul.AES.InvalidAESStreamException;
import ca.ulaval.pul.AES.InvalidPasswordException;
import ca.ulaval.pul.AES.StrongEncryptionNotAvailableException;

public class FileDecryption {
    FileDecryption() {
    }
    
    public void decrypt(String encryptedFilename, String decryptedFilename, String password) throws FileNotFoundException {
        File encryptedFile = new File(encryptedFilename);
        if(!encryptedFile.exists()) {
        	throw new FileNotFoundException();
        }
        File decryptedFile = new File(encryptedFile.getParent()  + "\\" + decryptedFilename);
        try (
    		FileInputStream fis = new FileInputStream(encryptedFile);
    		FileOutputStream fos = new FileOutputStream(decryptedFile);
        )
        {
            AES aes = new AES();
            aes.decrypt(password, fis, fos);
        } catch (IOException ex) {
        	ex.printStackTrace();
        } catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (InvalidAESStreamException e) {
			e.printStackTrace();
		} catch (StrongEncryptionNotAvailableException e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
    	if (args.length == 0) {
    		System.err.println("Usage: FileDecription <filename>");
    		System.exit(-1);
    	}
    	String filename = args[0];
        FileDecryption enc = new FileDecryption();
        try {
			enc.decrypt(".\\" + filename, filename + ".decrypted", "Le fond de l'air est frais!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }    
}


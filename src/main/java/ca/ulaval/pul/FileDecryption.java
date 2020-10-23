package ca.ulaval.pul;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDecryption {
    
    private File SOURCE;
    private File TARGET;
    private AESEncryptionSupport DEC;
    private final int BUFFER_SIZE = 2000000000;
    
    FileDecryption(String sourceFile,String password)throws FileNotFoundException
    {
        this.SOURCE = new File(sourceFile);
        if(!SOURCE.exists())throw new FileNotFoundException("File not found");
        this.DEC = new AESEncryptionSupport(password);
    }
    
    private void setTarget(String destinationFile) {
        this.TARGET = new File(SOURCE.getParent()  + "\\" + destinationFile);
    }
    
    public void decrypt(String destinationFile) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try{
            this.setTarget(destinationFile);
            fos = new FileOutputStream(TARGET);
            File file = this.SOURCE;
            fis = new FileInputStream(file);
            int remaining = fis.available();
            while(remaining>0){
                byte [] buffer = new byte[Math.min(remaining, BUFFER_SIZE)];
                remaining -= fis.read(buffer);
                byte[] b = DEC.decrypt(buffer);
                fos.write(b);
            }
            fis.close();
            fos.close();
            fis = null;
            fos = null;
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	if (args.length == 0) {
    		System.err.println("Usage: FileDecription <filename>");
    		System.exit(-1);
    	}
        try{
        	String filename = args[0];
            FileDecryption enc = new FileDecryption(".\\" + filename, "Le fond de l'air est frais!");
            enc.decrypt(filename + ".decrypted");
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        }
    }    
}


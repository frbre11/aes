package ca.ulaval.pul;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEncryption {
   
    
    private final int BUFFER_SIZE = 2000000000;
    private File SOURCE;
    private File TARGET;
    private AESEncryptionSupport ENC;
    
    FileEncryption(String sourceFile,String password) throws FileNotFoundException
    {
        this.SOURCE = new File(sourceFile);
        if(!SOURCE.exists())throw new FileNotFoundException("File not found");     
        ENC = new AESEncryptionSupport(password);
    }
    
    private void setTarget(String name){
    	this.TARGET = new File(SOURCE.getParent() + "\\" + name);
    }
    
    public void encrypt(String name){
        try {
            this.setTarget(name);
            FileInputStream fis = new FileInputStream(this.SOURCE);
            FileOutputStream fos = new FileOutputStream(this.TARGET);
            
            int remaining = fis.available();
            while(remaining>0){
                byte [] buffer = new byte[Math.min(remaining, BUFFER_SIZE)];
                remaining -= fis.read(buffer);
                byte[] b = ENC.encrypt(buffer);
                fos.write(b);
            }
            fis.close();
            fos.close();
            fis = null;
            fos = null;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
        	ex.printStackTrace();
        } 
        
    }

    public static void main(String[] args) {
    	if (args.length == 0) {
    		System.err.println("Usage: FileEncription <filename>");
    		System.exit(-1);
    	}
        try {
        	String filename = args[0];
            FileEncryption enc = new FileEncryption(".\\" + filename, "Le fond de l'air est frais!");
            enc.encrypt(filename + ".encrypted"); 
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    
}

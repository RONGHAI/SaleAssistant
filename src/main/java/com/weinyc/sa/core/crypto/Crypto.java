package com.weinyc.sa.core.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Crypto {

    private static final String PBKDF = "PBKDF2WithHmacSHA256";
    private static final int SALT_LEN = 8;
    private static final int KEYLEN_BITS = 128;
    private static final int MAX_FILE_BUF = 1024;
    private static final String AES = "AES/CBC/PKCS5Padding";

   /* PBKDF2WithHmacSHA1
    PBKDF2WithHmacSHA224
    PBKDF2WithHmacSHA256
    PBKDF2WithHmacSHA384
    PBKDF2WithHmacSHA512*/
    
    public static String generateSalt() {
        byte[] salt = new byte[SALT_LEN];
        SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public static String  encrypt(String key, String context) throws NoSuchAlgorithmException, NoSuchPaddingException, DecoderException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidParameterSpecException, InvalidKeyException   {
        SecretKeySpec secret = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] data = cipher.doFinal(context.getBytes());
        
        System.out.println(Hex.encodeHexString(iv).toUpperCase());
        String ds = Hex.encodeHexString(data);
        System.out.println(Base64.encodeBase64String(ds.getBytes()));
        
        return  Hex.encodeHexString(iv).toUpperCase() + Base64.encodeBase64String(data);
    }

    public static String decrypt(String key, String context)  throws NoSuchAlgorithmException, NoSuchPaddingException, DecoderException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException   {
        String iv = context.substring(0, 32);
        context = context.substring(32);
        SecretKeySpec secret = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));
        byte[] data = cipher.doFinal(Base64.decodeBase64(context));
        return new String(data);
    }
    
    
    public static final String derivationKey(String password, String salt, int iterations ) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException{
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), Hex.decodeHex(salt.toCharArray()), iterations, KEYLEN_BITS);
        SecretKey secretKey = factory.generateSecret(spec);
        return Hex.encodeHexString(secretKey.getEncoded()).toUpperCase();
    }
    
    

    public static String encrypt(String key, File input, File output) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
            InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, IOException {
        SecretKeySpec secret = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");

        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        FileInputStream fin = new FileInputStream(input);
        FileOutputStream fout = new FileOutputStream(output);
        fout.write(iv);
        int nread = 0;
        byte[] inbuf = new byte[MAX_FILE_BUF];
        while ((nread = fin.read(inbuf)) > 0) {
            byte[] trimbuf = new byte[nread];
            System.arraycopy(inbuf, 0, trimbuf, 0, nread);
            byte[] tmp = cipher.update(trimbuf);
            if (tmp != null) {
                fout.write(tmp);
            }
        }
        byte[] finalbuf = cipher.doFinal();
        if (finalbuf != null) {
            fout.write(finalbuf);
        }
        fout.flush();
        fin.close();
        fout.close();
        return Hex.encodeHexString(iv);
    }

    public static void decrypt(String key, File input, File output) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
            InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
        
        FileInputStream fin = new FileInputStream(input);
        byte[] iv = new byte[16];
        fin.read(iv);
        SecretKeySpec secret = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
       
        FileOutputStream fout = new FileOutputStream(output);
        int nread = 0;
        byte[] inbuf = new byte[MAX_FILE_BUF];
        CipherInputStream cin = new CipherInputStream(fin, cipher);
        while ((nread = cin.read(inbuf)) > 0) {
            byte[] trimbuf = new byte[nread];
            System.arraycopy(inbuf, 0, trimbuf, 0, nread);
            fout.write(trimbuf);
        }
        fout.flush();
        cin.close();
        fin.close();
        fout.close();

    }

    private static final String ALGORITHM = "SHA1";

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.update(text.getBytes(), 0, text.length());
        return Hex.encodeHexString(md.digest());
    }

    public static String SHA1(InputStream fis) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        return Hex.encodeHexString(md.digest());
    }

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, DecoderException,
            InvalidAlgorithmParameterException, IOException {
        
        Provider[] providers = Security.getProviders();
        System.out.println("");
        for (int i = 0; i != providers.length; i++)
        {
            System.out.println("Name: " + providers[i].getName() + " Version: " + providers[i].getVersion());
        }
        
        String password = "password";
        int iterations = 50000;
        String salt = "3448682478fec19f";// generateSalt();
        
        System.out.println(salt);
        System.out.println(Arrays.toString(Hex.decodeHex(salt.toCharArray())));
        String key = derivationKey(password, salt, iterations);
        System.out.println(key);
        key = "D63BF8D127D210B6364054CEDA905F4C";
        System.out.println(key);
        String e = encrypt(key, "Very, very confidential data");
        System.out.println(e);
        String newv = decrypt(key, e);
        System.out.println(newv);
        
        //63C5047F 0A5F4590 BDEBE500 2B96064D
      //  Hex.decodeHex("63C5047F0A5F4590BDEBE5002B96064D".toCharArray());
        
        //encrypt(key, new File("D:/or.txt"), new File("D:/or-e.txt"));
        
        //decrypt(key, new File("D:/or-e.txt"), new File("D:/or-d.txt"));
    }
}

/*
 * public class Crypto { String mPassword = null; public final static int
 * SALT_LEN = 8; byte [] mInitVec = null; byte [] mSalt = null; Cipher mEcipher
 * = null; Cipher mDecipher = null; private final int KEYLEN_BITS = 128; // see
 * notes below where this is used. private final int ITERATIONS = 65536; private
 * final int MAX_FILE_BUF = 1024;
 *//**
 * create an object with just the passphrase from the user. Don't do anything
 * else yet
 * 
 * @param password
 */
/*
 * public Crypto (String password) { mPassword = password; }
 *//**
 * return the generated salt for this object
 * 
 * @return
 */
/*
 * public byte [] getSalt () { return (mSalt); }
 *//**
 * return the initialization vector created from setupEncryption
 * 
 * @return
 */
/*
 * public byte [] getInitVec () { return (mInitVec); }
 *//**
 * debug/print messages
 * 
 * @param msg
 */
/*
 * private void Db (String msg) { System.out.println ("** Crypt ** " + msg); }
 *//**
 * this must be called after creating the initial Crypto object. It creates a
 * salt of SALT_LEN bytes and generates the salt bytes using secureRandom(). The
 * encryption secret key is created along with the initialization vectory. The
 * member variable mEcipher is created to be used by the class later on when
 * either creating a CipherOutputStream, or encrypting a buffer to be written to
 * disk.
 * 
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeySpecException
 * @throws NoSuchPaddingException
 * @throws InvalidParameterSpecException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws UnsupportedEncodingException
 * @throws InvalidKeyException
 */
/*
 * public void setupEncrypt () throws NoSuchAlgorithmException,
 * InvalidKeySpecException, NoSuchPaddingException,
 * InvalidParameterSpecException, IllegalBlockSizeException,
 * BadPaddingException, UnsupportedEncodingException, InvalidKeyException {
 * SecretKeyFactory factory = null; SecretKey tmp = null;
 * 
 * // crate secureRandom salt and store as member var for later use mSalt = new
 * byte [SALT_LEN]; SecureRandom rnd = new SecureRandom (); rnd.nextBytes
 * (mSalt); Db ("generated salt :" + Hex.encodeHexString (mSalt));
 * 
 * factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
 * 
 * Derive the key, given password and salt.
 * 
 * in order to do 256 bit crypto, you have to muck with the files for Java's
 * "unlimted security" The end user must also install them (not compiled in) so
 * beware. see here:
 * http://www.javamex.com/tutorials/cryptography/unrestricted_policy_files.shtml
 * 
 * KeySpec spec = new PBEKeySpec (mPassword.toCharArray (), mSalt, ITERATIONS,
 * KEYLEN_BITS); tmp = factory.generateSecret (spec); SecretKey secret = new
 * SecretKeySpec (tmp.getEncoded(), "AES");
 * 
 * Create the Encryption cipher object and store as a member variable
 * 
 * mEcipher = Cipher.getInstance (AES); mEcipher.init
 * (Cipher.ENCRYPT_MODE, secret); AlgorithmParameters params =
 * mEcipher.getParameters ();
 * 
 * // get the initialization vectory and store as member var mInitVec =
 * params.getParameterSpec (IvParameterSpec.class).getIV();
 * 
 * Db ("mInitVec is :" + Hex.encodeHexString (mInitVec)); }
 *//**
 * If a file is being decrypted, we need to know the pasword, the salt and
 * the initialization vector (iv). We have the password from initializing the
 * class. pass the iv and salt here which is obtained when encrypting the file
 * initially.
 * 
 * @param initvec
 * @param salt
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeySpecException
 * @throws NoSuchPaddingException
 * @throws InvalidKeyException
 * @throws InvalidAlgorithmParameterException
 * @throws DecoderException
 */
/*
 * public void setupDecrypt (String initvec, String salt) throws
 * NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
 * InvalidKeyException, InvalidAlgorithmParameterException, DecoderException {
 * SecretKeyFactory factory = null; SecretKey tmp = null; SecretKey secret =
 * null;
 * 
 * // since we pass it as a string of input, convert to a actual byte buffer
 * here mSalt = Hex.decodeHex (salt.toCharArray ()); Db ("got salt " +
 * Hex.encodeHexString (mSalt));
 * 
 * // get initialization vector from passed string mInitVec = Hex.decodeHex
 * (initvec.toCharArray ()); Db ("got initvector :" + Hex.encodeHexString
 * (mInitVec));
 * 
 * 
 * Derive the key, given password and salt. // in order to do 256 bit crypto,
 * you have to muck with the files for Java's "unlimted security" // The end
 * user must also install them (not compiled in) so beware. // see here: //
 * http://www.javamex.com/tutorials/cryptography/unrestricted_policy_files.shtml
 * factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); KeySpec spec =
 * new PBEKeySpec(mPassword.toCharArray (), mSalt, ITERATIONS, KEYLEN_BITS);
 * 
 * tmp = factory.generateSecret(spec); secret = new
 * SecretKeySpec(tmp.getEncoded(), "AES");
 * 
 * Decrypt the message, given derived key and initialization vector. mDecipher =
 * Cipher.getInstance(AES);
 * mDecipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(mInitVec)); }
 *//**
 * This is where we write out the actual encrypted data to disk using the
 * Cipher created in setupEncrypt(). Pass two file objects representing the
 * actual input (cleartext) and output file to be encrypted.
 * 
 * there may be a way to write a cleartext header to the encrypted file
 * containing the salt, but I ran into uncertain problems with that.
 * 
 * @param input
 *            - the cleartext file to be encrypted
 * @param output
 *            - the encrypted data file
 * @throws IOException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 */
/*
 * public void WriteEncryptedFile (File input, File output) throws IOException,
 * IllegalBlockSizeException, BadPaddingException { FileInputStream fin;
 * FileOutputStream fout; long totalread = 0; int nread = 0; byte [] inbuf = new
 * byte [MAX_FILE_BUF];
 * 
 * fout = new FileOutputStream (output); fin = new FileInputStream (input);
 * 
 * while ((nread = fin.read (inbuf)) > 0 ) { Db ("read " + nread + " bytes");
 * totalread += nread;
 * 
 * // create a buffer to write with the exact number of bytes read. Otherwise a
 * short read fills inbuf with 0x0 // and results in full blocks of MAX_FILE_BUF
 * being written. byte [] trimbuf = new byte [nread]; for (int i = 0; i < nread;
 * i++) trimbuf[i] = inbuf[i];
 * 
 * // encrypt the buffer using the cipher obtained previosly byte [] tmp =
 * mEcipher.update (trimbuf);
 * 
 * // I don't think this should happen, but just in case.. if (tmp != null)
 * fout.write (tmp); }
 * 
 * // finalize the encryption since we've done it in blocks of MAX_FILE_BUF byte
 * [] finalbuf = mEcipher.doFinal (); if (finalbuf != null) fout.write
 * (finalbuf);
 * 
 * fout.flush(); fin.close(); fout.close(); fout.close ();
 * 
 * Db ("wrote " + totalread + " encrypted bytes"); }
 *//**
 * Read from the encrypted file (input) and turn the cipher back into
 * cleartext. Write the cleartext buffer back out to disk as (output) File.
 * 
 * I left CipherInputStream in here as a test to see if I could mix it with the
 * update() and final() methods of encrypting and still have a correctly
 * decrypted file in the end. Seems to work so left it in.
 * 
 * @param input
 *            - File object representing encrypted data on disk
 * @param output
 *            - File object of cleartext data to write out after decrypting
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws IOException
 */
/*
 * public void ReadEncryptedFile (File input, File output) throws
 * IllegalBlockSizeException, BadPaddingException, IOException { FileInputStream
 * fin; FileOutputStream fout; CipherInputStream cin; long totalread = 0; int
 * nread = 0; byte [] inbuf = new byte [MAX_FILE_BUF];
 * 
 * fout = new FileOutputStream (output); fin = new FileInputStream (input);
 * 
 * // creating a decoding stream from the FileInputStream above using the cipher
 * created from setupDecrypt() cin = new CipherInputStream (fin, mDecipher);
 * 
 * while ((nread = cin.read (inbuf)) > 0 ) { Db ("read " + nread + " bytes");
 * totalread += nread;
 * 
 * // create a buffer to write with the exact number of bytes read. Otherwise a
 * short read fills inbuf with 0x0 byte [] trimbuf = new byte [nread]; for (int
 * i = 0; i < nread; i++) trimbuf[i] = inbuf[i];
 * 
 * // write out the size-adjusted buffer fout.write (trimbuf); }
 * 
 * fout.flush(); cin.close(); fin.close (); fout.close();
 * 
 * Db ("wrote " + totalread + " encrypted bytes"); }
 *//**
 * adding main() for usage demonstration. With member vars, some of the
 * locals would not be needed
 */
/*
 * public static void main(String [] args) {
 * 
 * // create the input.txt file in the current directory before continuing File
 * input = new File ("input.txt"); File eoutput = new File ("encrypted.aes");
 * File doutput = new File ("decrypted.txt"); String iv = null; String salt =
 * null; Crypto en = new Crypto ("mypassword");
 * 
 * 
 * setup encryption cipher using password. print out iv and salt
 * 
 * try { en.setupEncrypt (); iv = Hex.encodeHexString (en.getInitVec
 * ()).toUpperCase (); salt = Hex.encodeHexString (en.getSalt ()).toUpperCase
 * (); } catch (InvalidKeyException e) { e.printStackTrace(); } catch
 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
 * (InvalidKeySpecException e) { e.printStackTrace(); } catch
 * (NoSuchPaddingException e) { e.printStackTrace(); } catch
 * (InvalidParameterSpecException e) { e.printStackTrace(); } catch
 * (IllegalBlockSizeException e) { e.printStackTrace(); } catch
 * (BadPaddingException e) { e.printStackTrace(); } catch
 * (UnsupportedEncodingException e) { e.printStackTrace(); }
 * 
 * 
 * write out encrypted file
 * 
 * try { en.WriteEncryptedFile (input, eoutput); System.out.printf
 * ("File encrypted to " + eoutput.getName () + "\niv:" + iv + "\nsalt:" + salt
 * + "\n\n"); } catch (IllegalBlockSizeException e) { e.printStackTrace(); }
 * catch (BadPaddingException e) { e.printStackTrace(); } catch (IOException e)
 * { e.printStackTrace(); }
 * 
 * 
 * 
 * decrypt file
 * 
 * Crypto dc = new Crypto ("mypassword"); try { dc.setupDecrypt (iv, salt); }
 * catch (InvalidKeyException e) { e.printStackTrace(); } catch
 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
 * (InvalidKeySpecException e) { e.printStackTrace(); } catch
 * (NoSuchPaddingException e) { e.printStackTrace(); } catch
 * (InvalidAlgorithmParameterException e) { e.printStackTrace(); } catch
 * (DecoderException e) { e.printStackTrace(); }
 * 
 * 
 * write out decrypted file
 * 
 * try { dc.ReadEncryptedFile (eoutput, doutput); System.out.println
 * ("decryption finished to " + doutput.getName ()); } catch
 * (IllegalBlockSizeException e) { e.printStackTrace(); } catch
 * (BadPaddingException e) { e.printStackTrace(); } catch (IOException e) {
 * e.printStackTrace(); } }
 * 
 * 
 * }
 */
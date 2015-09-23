/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author LI HAO
 */
public class CryptoHelper {
    // Crytographic constants
    private final String DEFAULT_KEYSTORE_TYPE = "JKS";
    private final String DEFAULT_KEYSTORE_PROVIDER = "SUN";
    private final String DEFAULT_DOMAIN_CONFIG_DIRECTORY = System.getProperty("user.dir").toString() + System.getProperty("file.separator") + "config";
    private final String DEFAULT_KEYSTORE_NAME = "keystore.jks";
    private final char[] DEFAULT_KEYSTORE_PASSWORD = new char[]{'c', 'h', 'a', 'n', 'g', 'e', 'i', 't'};
    private final int DEFAULT_PRIVATEKEY_LENGTH = 633;
    private final String DEFAULT_CERTIFICATE_ALIAS = "s1as";
    private final String DEFAULT_CIPHER_ALGORITHM_NAME = "AES/CBC/PKCS5Padding";
    private final String DEFAULT_CIPHER_NAME = "AES";
    private final int DEFAULT_KEY_LENGTH = 16;
    private final int DEFAULT_IV_LENGTH = 16;
    private final String DEFAULT_CHARSET = "ISO-8859-1";
    private final String DEFAULT_HASH_ALGORITHM_NAME = "MD5";

    private KeyStore keyStore = null;
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;
    private X509Certificate cert = null;

    private byte[] key = null;
    private byte[] iv = null;

    private static CryptoHelper cryptoHelper = null;



    public CryptoHelper()
    {
        doInitializeCryptographicHelper();
    }

    public static CryptoHelper getInstanceOf()
    {
        if (cryptoHelper == null)
        {
            cryptoHelper = new CryptoHelper();
        }

        return cryptoHelper;
    }

    private void doInitializeCryptographicHelper()
    {
        try
        {
            FileInputStream keyStoreStream = new FileInputStream(DEFAULT_DOMAIN_CONFIG_DIRECTORY + System.getProperty("file.separator") + DEFAULT_KEYSTORE_NAME);
            keyStore = KeyStore.getInstance(DEFAULT_KEYSTORE_TYPE, DEFAULT_KEYSTORE_PROVIDER);
            keyStore.load(keyStoreStream, DEFAULT_KEYSTORE_PASSWORD);
            cert = (X509Certificate) keyStore.getCertificate(DEFAULT_CERTIFICATE_ALIAS);
            publicKey = cert.getPublicKey();
            privateKey = (PrivateKey) keyStore.getKey(DEFAULT_CERTIFICATE_ALIAS, DEFAULT_KEYSTORE_PASSWORD);
            byte[] certPrivateKey = privateKey.getEncoded();
            key = new byte[DEFAULT_KEY_LENGTH];
            iv = new byte[DEFAULT_IV_LENGTH];
            for (int i = 0; i < DEFAULT_KEY_LENGTH; i++)
            {
                key[i] = certPrivateKey[i];
            }
            for (int i = 0; i < DEFAULT_IV_LENGTH; i++)
            {
                iv[i] = certPrivateKey[DEFAULT_PRIVATEKEY_LENGTH - DEFAULT_IV_LENGTH - i];
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (KeyStoreException ex)
        {
            ex.printStackTrace();
        }
        catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        catch (NoSuchProviderException ex)
        {
            ex.printStackTrace();
        }
        catch (CertificateException ex)
        {
            ex.printStackTrace();
        }
        catch (UnrecoverableKeyException ex)
        {
            ex.printStackTrace();
        }
    }

    public KeyStore getDefaultKeyStore()
    {
        return keyStore;
    }

    public X509Certificate getDefaultCertificate()
    {
        return cert;
    }

    public PublicKey getDefaultPublicKey()
    {
        return publicKey;
    }

    public PrivateKey getDefaultPrivateKey()
    {
        return privateKey;
    }

    public byte[] getDefaultEncryptionKey()
    {
        return key;
    }

    public byte[] getDefaultEncryptionIV()
    {
        return iv;
    }

    public String doAESEncryptString(String stringToEncrypt, byte[] key, byte[] iv)
    {
        String encryptedString = null;

        try
        {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_NAME);
            
            SecretKeySpec actualKey = new SecretKeySpec(key, DEFAULT_CIPHER_NAME);
            IvParameterSpec actualIV = new IvParameterSpec(iv, 0, DEFAULT_IV_LENGTH);
            cipher.init(Cipher.ENCRYPT_MODE, actualKey, actualIV);

            byte[] encryptedBuffer = cipher.doFinal(stringToEncrypt.getBytes(DEFAULT_CHARSET));
            encryptedString = new String(encryptedBuffer, DEFAULT_CHARSET);
        }
        catch (NoSuchAlgorithmException noSuchAlgoEx)
        {
            System.out.println("********** NoSuchAlgorithmException: " + DEFAULT_CIPHER_ALGORITHM_NAME);
        }
        catch (NoSuchPaddingException noSuchPadEx)
        {
            System.out.println("********** NoSuchPaddingException: " + noSuchPadEx);
        }
        catch (Exception ex)
        {
            System.out.println("********** Exception: " + ex);
        }

        return encryptedString;
    }

    public String doAESDecryptString(String stringToDecrypt, byte[] key, byte[] iv)
    {
        String decryptedString = null;

        try
        {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_NAME);
            SecretKeySpec actualKey = new SecretKeySpec(key, DEFAULT_CIPHER_NAME);
            IvParameterSpec actualIV = new IvParameterSpec(iv, 0, DEFAULT_IV_LENGTH);
            cipher.init(Cipher.DECRYPT_MODE, actualKey, actualIV);
            byte[] decryptedBuffer = cipher.doFinal(stringToDecrypt.getBytes(DEFAULT_CHARSET));
            decryptedString = new String(decryptedBuffer, DEFAULT_CHARSET);
        }
        catch (NoSuchAlgorithmException noSuchAlgoEx)
        {
            System.out.println("********** NoSuchAlgorithmException: " + DEFAULT_CIPHER_ALGORITHM_NAME);
        } catch (NoSuchPaddingException noSuchPadEx)
        {
            System.out.println("********** NoSuchPaddingException: " + noSuchPadEx);
        }
        catch (Exception ex)
        {

            System.out.println("********** Exception: " + ex);
        }

        return decryptedString;
    }

    public String doMD5Hashing(String stringToHash)
    {
        MessageDigest md = null;
        byte[] hashSum = null;
        
        try
        {
            md = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM_NAME);
            hashSum = md.digest(stringToHash.getBytes());
        }
        catch (Exception ex)
        {
            System.out.println("********** Exception: " + ex);
        }

        if (hashSum != null)
        {
            return byteArrayToHexString(hashSum);
        }
        else
        {
            return null;
        }
    }

    private String byteArrayToHexString(byte[] bytes)
    {
        int lo = 0;
        int hi = 0;
        String hexString = "";

        for (int i = 0; i < bytes.length; i++)
        {
            lo = bytes[i];
            lo = lo & 0xff;
            hi = lo >> 4;
            lo = lo & 0xf;
            if (hi == 0)
            {
                hexString += "0";
            }
            else if (hi == 1)
            {
                hexString += "1";
            }
            else if (hi == 2)
            {
                hexString += "2";
            }
            else if (hi == 3)
            {
                hexString += "3";
            }
            else if (hi == 4)
            {
                hexString += "4";
            }
            else if (hi == 5)
            {
                hexString += "5";
            }
            else if (hi == 6)
            {
                hexString += "6";
            }
            else if (hi == 7)
            {
                hexString += "7";
            }
            else if (hi == 8)
            {
                hexString += "8";
            }
            else if (hi == 9)
            {
                hexString += "9";
            }
            else if (hi == 10)
            {
                hexString += "a";
            }
            else if (hi == 11)
            {
                hexString += "b";
            }
            else if (hi == 12)
            {
                hexString += "c";
            }
            else if (hi == 13)
            {
                hexString += "d";
            }
            else if (hi == 14)
            {
                hexString += "e";
            }
            else if (hi == 15)
            {
                hexString += "f";
            }
            
            if (lo == 0)
            {
                hexString += "0";
            }
            else if (lo == 1)
            {
                hexString += "1";
            }
            else if (lo == 2)
            {
                hexString += "2";
            }
            else if (lo == 3)
            {
                hexString += "3";
            }
            else if (lo == 4)
            {
                hexString += "4";
            }
            else if (lo == 5)
            {
                hexString += "5";
            }
            else if (lo == 6)
            {
                hexString += "6";
            }
            else if (lo == 7)
            {
                hexString += "7";
            }
            else if (lo == 8)
            {
                hexString += "8";
            }
            else if (lo == 9)
            {
                hexString += "9";
            }
            else if (lo == 10)
            {
                hexString += "a";
            }
            else if (lo == 11)
            {
                hexString += "b";
            }
            else if (lo == 12)
            {
                hexString += "c";
            }
            else if (lo == 13)
            {
                hexString += "d";
            }
            else if (lo == 14)
            {
                hexString += "e";
            }
            else if (lo == 15)
            {
                hexString += "f";
            }
        }
        
        return hexString;
    }
}

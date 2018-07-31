/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.common.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class RSAUtilTest {

    public RSAUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of generateKeyPair method, of class RSAUtil.
     */
    @Test
    public void testGenerateKeyPair() throws Exception {
        System.out.println("generateKeyPair for china-cmb...");
        KeyPair result1 = RSAUtil.generateKeyPair();

        assertNotNull(result1);
        String publicKey1 = RSAUtil.getKeyAsString(result1.getPublic());
        assertNotNull(publicKey1);
        System.out.println("china-cmb public key: \n" + publicKey1);

        String privateKey1 = RSAUtil.getKeyAsString(result1.getPrivate());
        assertNotNull(privateKey1);
        System.out.println("china-cmb private key: \n" + privateKey1);

        System.out.println("\ngenerateKeyPair china-icbc...");
        KeyPair result2 = RSAUtil.generateKeyPair();

        assertNotNull(result2);
        String publicKey2 = RSAUtil.getKeyAsString(result2.getPublic());
        assertNotNull(publicKey2);
        System.out.println("china-icbc public key: \n" + publicKey2);

        String privateKey2 = RSAUtil.getKeyAsString(result2.getPrivate());
        assertNotNull(privateKey2);
        System.out.println("china-icbc private key: \n" + privateKey2);

        assertNotSame("the two publicKey should not the same", publicKey1, publicKey2);
        assertNotSame("the two privateKey should not the same", privateKey1, privateKey2);
    }

    /**
     * Test of encrypt and decrypt method, of class RSAUtil.
     */
    @Test
    public void testEncryptThenDecrypt() throws Exception {
        System.out.println("generateKeyPair f...");
        KeyPair keyPair = RSAUtil.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String plainCardNumber = "1234567890";
        System.out.println("plainCardNumber=" + plainCardNumber);

        System.out.println("encrypting...");
        String encryptCardNumber = RSAUtil.encrypt(plainCardNumber, publicKey);
        System.out.println("encryptCardNumber=" + encryptCardNumber);

        System.out.println("decrypting...");
        String decryptCardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
        System.out.println("decryptCardNumber=" + decryptCardNumber);

        assertEquals(plainCardNumber, decryptCardNumber);
    }

    /**
     * Test of encrypt and decrypt by reading existing key string.
     */
    @Test
    public void testEncryptThenDecryptByExistingKeys() throws Exception {
        String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdj6JVk5YvHj81t25xE6Q2W9KiMejqz1540+prpYT6a1oSbK+j3O4Y/ICYyS6x5U7YK1ta1H9hIeZM2D75fP3577ShaMin0Qiip53X5zj3uawUvPNn+XWZQTXQD7iiU7hINMMn2qZCP8Fs5Qw0YFebKLiOzoesmqqcI5vjdHz3vQIDAQAB";
        String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ2PolWTli8ePzW3bnETpDZb0qIx6OrPXnjT6mulhPprWhJsr6Pc7hj8gJjJLrHlTtgrW1rUf2Eh5kzYPvl8/fnvtKFoyKfRCKKnndfnOPe5rBS882f5dZlBNdAPuKJTuEg0wyfapkI/wWzlDDRgV5souI7Oh6yaqpwjm+N0fPe9AgMBAAECgYAFuDARYTEjdrqerZDxw9/DJWQpBRzKYUqxoiexTqncGUeEwbNChIOUHp5gbXUnegZ5rGE9k9cn5H4irfrjPZlOiVV9fjUufbvSK+MpkS6WRZdIWxEqlMkHCnCrTinv+zedtJ8oioHWI9B1jdqYl96lj24TUu58Q4Re9lWAogxLdQJBAP/QpT6QZcXWSRjIqVSdIwKx5OjbJlCzy6Wjx7BFCsuEjoJTiSC0QqkKM5OhWzk1pWLT/FDzobKu6rO227zPpKsCQQCdrMz1GKdP7LzN2vGhHoat/HPMnRU44vcYGrj5193/sMZC9/EwtETtD4fWnsZhAARmRLFfk3vJdjUS3wH0e8U3AkEAtHTjzJGiKYAzSldrE/RRWTpSp2zIx489rlgtqovZxJ16xjbVeguPJp26qn7d0iqkw2WVDSoAPAMmL6ZjtfqoRwJAFnOoq63bBTJ8oANxLLQgEV5FTvl/MB6BeNk1Rb5LSkFtudN8cKb6uZbgjNsG/ID4H2fgqe9iTQmb/RfsO1f0eQJBAOO9g1ssiXOrKJ2EAP8I035XZLgyCd6YKNT7sCOCrRVNQyuND8Rru52zySDUZq/1yhJ+dWhFuH2TtacbocIkMbY=";

        PublicKey publicKey = RSAUtil.getPublicKeyFromString(publicKeyString);
        PrivateKey privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);

        String plainCardNumber = "0987654321";
        System.out.println("plainCardNumber=" + plainCardNumber);

        System.out.println("encrypting...");
        String encryptCardNumber = RSAUtil.encrypt(plainCardNumber, publicKey);
        System.out.println("encryptCardNumber=" + encryptCardNumber);

        System.out.println("decrypting...");
        String decryptCardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
        System.out.println("decryptCardNumber=" + decryptCardNumber);

        assertEquals(plainCardNumber, decryptCardNumber);

        // once again
        PublicKey publicKey2 = RSAUtil.getPublicKeyFromString(publicKeyString);
        PrivateKey privateKey2 = RSAUtil.getPrivateKeyFromString(privateKeyString);

        String plainCardNumber2 = "0987654321";
        System.out.println("plainCardNumber2=" + plainCardNumber2);

        System.out.println("encrypting2...");
        String encryptCardNumber2 = RSAUtil.encrypt(plainCardNumber2, publicKey2);
        System.out.println("encryptCardNumber2=" + encryptCardNumber2);

        System.out.println("decrypting2...");
        String decryptCardNumber2 = RSAUtil.decrypt(encryptCardNumber2, privateKey2);
        System.out.println("decryptCardNumber2=" + decryptCardNumber2);

        assertEquals(plainCardNumber2, decryptCardNumber2);

        // encrypt text from app out
        String plainCardNumber3 = "0987654321";
        PrivateKey privateKey3 = RSAUtil.getPrivateKeyFromString(privateKeyString);
        System.out.println("decrypting3...");
        String encryptCardNumber3 = "jA0czhwVKyBUEuMGuCy/Iv3+mZLZGGfllcXz8wjHqpFR3kKRkt1SLykRXLFMzPFfEkOZRka9iVZC1md38n707D8GC1mP2FCokzeP7BsPaohBT+nYDmVDhwYZrY5PI3T8s9PUZkXGSJlVSXcSa1/WdQfQ96dZE6cQn2mPz9m+mwY=";
        String decryptCardNumber3 = RSAUtil.decrypt(encryptCardNumber3, privateKey3);
        System.out.println("decryptCardNumber3=" + decryptCardNumber3);
        assertEquals(plainCardNumber3, decryptCardNumber2);
    }

    /**
     * Test of encrypt and decrypt by reading existing key string.
     */
    @Test
    public void testEncryptThenDecryptByPaymentPartnerKeys() throws Exception {
        String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGew6gRkbyM971Aj0SbogZBZN50hoi0lhqOLB0wXW4Jhc/2CHwPAMLRsdb+Yzu5EkEVhV1Sxp3xtTAVCiXNkfXCKpSvi6Uipa4lkwg2lCzmlxWZOIX2uZnmDwh6g7U3uvzk4ZN4SyuwGEIl4BI0KOssknEL/ZCcMehuPMCjTui/QIDAQAB";
        String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIZ7DqBGRvIz3vUCPRJuiBkFk3nSGiLSWGo4sHTBdbgmFz/YIfA8AwtGx1v5jO7kSQRWFXVLGnfG1MBUKJc2R9cIqlK+LpSKlriWTCDaULOaXFZk4hfa5meYPCHqDtTe6/OThk3hLK7AYQiXgEjQo6yyScQv9kJwx6G48wKNO6L9AgMBAAECgYAwDR5PxGyrND+ROgiKYnY8wpJMb/coxBIVekYKoemnL+0Uwex2g2rvB45KXeOupGE2rG+kZn6Bl8kDI88RILMQLId9pb4Hv1r5BVxzcvoA3xKTpYs3h1TNtWV4AHpq0FXKfytkHn9mxnvysEGpeGbDQ+ZbvqAikd31z4J1kBrGHQJBAMret5XVv2568E75vdkTlBKYCP0mS49tW77aEd+LNZ38IqQgKzCKulqL/qHG8a3LncD2gte5XZ5Ji5hpgfwcQoMCQQCpszmsIKsSN8leQKKHACUVRMfuq+xzkZEcX7JrBO+sgNhNzMwCcZkzzzAy1NDo1cQos3lDYWfEuRolIC4qHYx/AkEAx/gxTmibgfN83Nwf3tIFqy5h7ebJpF9PiBi8dXVrLIdUPiPXbsnQV2CXZjAMGhwTLBnRb4MwXM5x45Jcpn3UsQJAJnXFG1qoCs/z5lWVwCzrOp/FBDWYuDSEg764jAKpYxosa61/iwo9430QemS7GBnihCrwz7GSuAYPg9yAOfFKdwJBAKHn2D5MfUbrQLVjCeYZSwtMiRpfOVzx96NSZ8RG6AwgcQiY7sDV/2fYm3UkbmRgx4sfS/T8wzH5ZVBC/P/PsTs=";

        // once again
        PublicKey publicKey = RSAUtil.getPublicKeyFromString(publicKeyString);
        PrivateKey privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);

        String plainCardNumber = "1234567890";
        System.out.println("plainCardNumber=" + plainCardNumber);

        System.out.println("encrypting using payment partner china-cmb key...");
        String encryptCardNumber = RSAUtil.encrypt(plainCardNumber, publicKey);
        System.out.println("encryptCardNumber=" + encryptCardNumber);

        System.out.println("decrypting by payment partner china-cmb key...");
        String decryptCardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
        System.out.println("decryptCardNumber=" + decryptCardNumber);

        assertEquals(plainCardNumber, decryptCardNumber);

    }

    @Test
    public void testEncryptThenDecryptByPaymentPartnerKeys2() throws Exception {
        String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGew6gRkbyM971Aj0SbogZBZN50hoi0lhqOLB0wXW4Jhc/2CHwPAMLRsdb+Yzu5EkEVhV1Sxp3xtTAVCiXNkfXCKpSvi6Uipa4lkwg2lCzmlxWZOIX2uZnmDwh6g7U3uvzk4ZN4SyuwGEIl4BI0KOssknEL/ZCcMehuPMCjTui/QIDAQAB";
        String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIZ7DqBGRvIz3vUCPRJuiBkFk3nSGiLSWGo4sHTBdbgmFz/YIfA8AwtGx1v5jO7kSQRWFXVLGnfG1MBUKJc2R9cIqlK+LpSKlriWTCDaULOaXFZk4hfa5meYPCHqDtTe6/OThk3hLK7AYQiXgEjQo6yyScQv9kJwx6G48wKNO6L9AgMBAAECgYAwDR5PxGyrND+ROgiKYnY8wpJMb/coxBIVekYKoemnL+0Uwex2g2rvB45KXeOupGE2rG+kZn6Bl8kDI88RILMQLId9pb4Hv1r5BVxzcvoA3xKTpYs3h1TNtWV4AHpq0FXKfytkHn9mxnvysEGpeGbDQ+ZbvqAikd31z4J1kBrGHQJBAMret5XVv2568E75vdkTlBKYCP0mS49tW77aEd+LNZ38IqQgKzCKulqL/qHG8a3LncD2gte5XZ5Ji5hpgfwcQoMCQQCpszmsIKsSN8leQKKHACUVRMfuq+xzkZEcX7JrBO+sgNhNzMwCcZkzzzAy1NDo1cQos3lDYWfEuRolIC4qHYx/AkEAx/gxTmibgfN83Nwf3tIFqy5h7ebJpF9PiBi8dXVrLIdUPiPXbsnQV2CXZjAMGhwTLBnRb4MwXM5x45Jcpn3UsQJAJnXFG1qoCs/z5lWVwCzrOp/FBDWYuDSEg764jAKpYxosa61/iwo9430QemS7GBnihCrwz7GSuAYPg9yAOfFKdwJBAKHn2D5MfUbrQLVjCeYZSwtMiRpfOVzx96NSZ8RG6AwgcQiY7sDV/2fYm3UkbmRgx4sfS/T8wzH5ZVBC/P/PsTs=";

        // once again
        PublicKey publicKey = RSAUtil.getPublicKeyFromString(publicKeyString);
        PrivateKey privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);

        String plainCardNumber = "999 9999 9999 9999";
        System.out.println("plainCardNumber=" + plainCardNumber);

        System.out.println("encrypting using payment partner china-cmb key...");
        String encryptCardNumber = RSAUtil.encrypt(plainCardNumber, publicKey);
        System.out.println("encryptCardNumber=" + encryptCardNumber);

        System.out.println("decrypting by payment partner china-cmb key...");
        String decryptCardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
        System.out.println("decryptCardNumber=" + decryptCardNumber);

        assertEquals(plainCardNumber, decryptCardNumber);

    }


    /**
     * Test of generateKeyPair method, of class RSAUtil.
     */
    @Test
    public void generateAccessKeyPair() throws Exception {
        System.out.println("\ngenerateAccessKeyPair for pprun...");
        KeyPair result1 = RSAUtil.generateKeyPair();

        assertNotNull(result1);
        String publicKey1 = RSAUtil.getKeyAsString(result1.getPublic());
        byte[] plainText = publicKey1.getBytes(CommonUtil.UTF8);
        String md5Result1 = MessageDigestUtil.md5(plainText);
        System.out.println("\npprun public key: \n" + md5Result1);

        System.out.println();

        String privateKey1 = RSAUtil.getKeyAsString(result1.getPrivate());
        plainText = privateKey1.getBytes(CommonUtil.UTF8);
        String md5Result2 = MessageDigestUtil.md5(plainText);
        System.out.println("\npprun private key: \n" + md5Result2);
    }
}

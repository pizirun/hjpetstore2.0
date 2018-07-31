/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.common.util;

import java.util.Locale;
import java.util.Calendar;
import java.util.TimeZone;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * the assert result based on the result from <a href="http://www.tools4noobs.com/online_tools/hash/">Online hash calculator</a>.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class MessageDigestUtilTest {
    @Test
    public void testMd5_noSalt_pprun() throws Exception {
        System.out.println("md5");
        byte[] plainText = "pprun".getBytes(CommonUtil.UTF8);
        String expResult = "f735762134f0c42bcff532f022afed14";
        String result = MessageDigestUtil.md5(plainText);
        assertEquals(expResult, result);
    }

    /**
     * Test of md5 method, of class MessageDigestUtil.
     */
    @Test
    public void testMd5_j2ee() throws Exception {
        System.out.println("md5");
        byte[] plainText = "j2ee".getBytes(CommonUtil.UTF8);
        byte[] salt = "j2ee".getBytes(CommonUtil.UTF8);
        String expResult = "b6dcb0189da62b6b849903dcd57f84be";
        String result = MessageDigestUtil.md5(plainText, salt);
        assertEquals(expResult, result);
    }

    @Test
    public void testMd5_test() throws Exception {
        System.out.println("md5");
        byte[] plainText = "test".getBytes(CommonUtil.UTF8);
        byte[] salt = "test".getBytes(CommonUtil.UTF8);
        String expResult = "05a671c66aefea124cc08b76ea6d30bb";
        String result = MessageDigestUtil.md5(plainText, salt);
        assertEquals(expResult, result);
    }

    @Test
    public void testMd5_dogsp() throws Exception {
        System.out.println("md5");
        byte[] plainText = "dogsp".getBytes(CommonUtil.UTF8);
        byte[] salt = "dogsp".getBytes(CommonUtil.UTF8);
        String expResult = "0962f664081039e1c047417417692711";
        String result = MessageDigestUtil.md5(plainText, salt);
        assertEquals(expResult, result);
    }

    @Test
    public void testMd5_birdsp() throws Exception {
        System.out.println("md5");
        byte[] plainText = "birdsp".getBytes(CommonUtil.UTF8);
        byte[] salt = "birdsp".getBytes(CommonUtil.UTF8);
        String expResult = "6454dd68d2dc615df034aedec8a8334e";
        String result = MessageDigestUtil.md5(plainText, salt);
        assertEquals(expResult, result);
    }

    @Test
    public void testMd5_pprun() throws Exception {
        System.out.println("md5");
        byte[] plainText = "pprun".getBytes(CommonUtil.UTF8);
        byte[] salt = "pprun".getBytes(CommonUtil.UTF8);
        String expResult = "ca27a6128e8c68d15d5ef9e2b7cfa903";
        String result = MessageDigestUtil.md5(plainText, salt);
        assertEquals(expResult, result);
    }

    /**
     * Test of sha512 method, of class MessageDigestUtil.
     */
    @Test
    public void testSha512_pprun() throws Exception {
        System.out.println("sha512");
        byte[] plainText = "pprun".getBytes(CommonUtil.UTF8);
        String expResult = "bc7163dab8eb79a9867b4604b46b0328e9ace555ef5d9526e1fcd748f9864bf85d59e97c044a2d9795736753c2b0d77cd085eb05d854e5849f42f37f85851220";
        String result = MessageDigestUtil.sha512(plainText);
        assertEquals(expResult, result);
    }

    /**
     * Test of sha512 method, of class MessageDigestUtil.
     */
    @Test
    public void testSha512_test() throws Exception {
        System.out.println("sha512");
        byte[] plainText = "test".getBytes(CommonUtil.UTF8);
        String expResult = "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff";
        String result = MessageDigestUtil.sha512(plainText);
        assertEquals(expResult, result);
    }

    /**
     * Test of sha512 method, of class MessageDigestUtil.
     */
    @Test
    public void testSha512_cardNumber() throws Exception {
        System.out.println("sha512");
        byte[] plainText = "999 9999 9999 9999".getBytes(CommonUtil.UTF8);
        String expResult = "4836e18fa1d07a8f69df17012f923b461f0ac56022044e15f3af67715d7999059c1bd04c3619511c2bc543edec5cf98cab78fab91ae84eb7bae867c6ed628017";
        String result = MessageDigestUtil.sha512(plainText);
        assertEquals(expResult, result);
    }

    @Test
    public void testRfc2104HmacSha512() throws Exception {
        System.out.println("rfc2104HmacSha512");
        String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAId0zSX4GGcC/bIPuLvlvLM0JZyb52vD67ymTbj/MWcpsOzFthvuzae7xF593JAc9DmEtvwctfPKK6KwaltRRPAj1LXz53EK1p35q7QVdQJl6K2KsL0i0S5VmW0ruzHTGqQJ/UA8YRz4U3PEDdapX6xPeztRrvjkVC5ouDP/B9chAgMBAAECgYBi0ag0MfmDdSAlfei0U1bFmZ5y0R4UdVsORnjfJHUjILwtD9PaLu4jlw0cLk/xZt4Y9CQWas+m6e/JgVAmDHTwa8MlagKgLPIBygoL0zBR0HUjniBosclFPDZcnnW+CIVtDuwFl8Io8mKzxUT1oT+8QrrwYkP5ldx5cBTq4GJcQQJBAPwOQJ51XG9+a0iJnnOcny4NO23A+6TV3qryEb4K5QeQqRN8dFNvEaiu6aR3qWBcq4x0cHFJuvMn5vLNPiWxh5kCQQCJk3IkRptj9ns4/HyMVfb1XGgOVxuUQZk3t86LRRdZ6nKfHP4oLrYXt71ZZ7IccIp4NUzi0w/Uqm12me1FA2DJAkBJ74RU9ugebF7b8EJid1baE1eTYkBuoa2nR0qdDGUSSwTNinw03Se461XtwIwWkBhiIuW41X/ZQ6MjJcFNHT5BAkAQ2SfPXX5GPbdVgUZXgWefINP1faF5BJeZBT+cOrfjYRzsc4aEsUPHn87mlhZv+TBcDFsAJEz6BG0nay5tlz5RAkBmsOlch17HnIUgCbBzw2bt8O4/MQALoKONdd0ZjhpTuvVheqogeMAEq1u0eMZTPCfRK/QCYzTDsuvpWqJKsjKZ/fnvtKFoyKfRCKKnndfnOPe5rBS882f5dZlBNdAPuKJTuEg0wyfapkI/wWzlDDRgV5souI7Oh6yaqpwjm+N0fPe9AgMBAAECgYAFuDARYTEjdrqerZDxw9/DJWQpBRzKYUqxoiexTqncGUeEwbNChIOUHp5gbXUnegZ5rGE9k9cn5H4irfrjPZlOiVV9fjUufbvSK+MpkS6WRZdIWxEqlMkHCnCrTinv+zedtJ8oioHWI9B1jdqYl96lj24TUu58Q4Re9lWAogxLdQJBAP/QpT6QZcXWSRjIqVSdIwKx5OjbJlCzy6Wjx7BFCsuEjoJTiSC0QqkKM5OhWzk1pWLT/FDzobKu6rO227zPpKsCQQCdrMz1GKdP7LzN2vGhHoat/HPMnRU44vcYGrj5193/sMZC9/EwtETtD4fWnsZhAARmRLFfk3vJdjUS3wH0e8U3AkEAtHTjzJGiKYAzSldrE/RRWTpSp2zIx489rlgtqovZxJ16xjbVeguPJp26qn7d0iqkw2WVDSoAPAMmL6ZjtfqoRwJAFnOoq63bBTJ8oANxLLQgEV5FTvl/MB6BeNk1Rb5LSkFtudN8cKb6uZbgjNsG/ID4H2fgqe9iTQmb/RfsO1f0eQJBAOO9g1ssiXOrKJ2EAP8I035XZLgyCd6YKNT7sCOCrRVNQyuND8Rru52zySDUZq/1yhJ+dWhFuH2TtacbocIkMbY=";
        String plainText = "999 9999 9999 9999";
        String expResult = "WWJ6LqcI0za96M1Y2JGIC2Jthb37DMX/VKXImiZu94TJt9p1KKhYQpNc5pRZK2rtLzdgWqsT3EUbdZ88YcqiEg==";
        String result = MessageDigestUtil.rfc2104HmacSha512(plainText, privateKeyString);
        assertEquals(expResult, result);
    }

    @Test
    public void testKeyEncodeAndDecode() throws Exception {
        System.out.println("testKeyEncodeAndDecode");
        String publicKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAId0zSX4GGcC/bIPuLvlvLM0JZyb52vD67ymTbj/MWcpsOzFthvuzae7xF593JAc9DmEtvwctfPKK6KwaltRRPAj1LXz53EK1p35q7QVdQJl6K2KsL0i0S5VmW0ruzHTGqQJ/UA8YRz4U3PEDdapX6xPeztRrvjkVC5ouDP/B9chAgMBAAECgYBi0ag0MfmDdSAlfei0U1bFmZ5y0R4UdVsORnjfJHUjILwtD9PaLu4jlw0cLk/xZt4Y9CQWas+m6e/JgVAmDHTwa8MlagKgLPIBygoL0zBR0HUjniBosclFPDZcnnW+CIVtDuwFl8Io8mKzxUT1oT+8QrrwYkP5ldx5cBTq4GJcQQJBAPwOQJ51XG9+a0iJnnOcny4NO23A+6TV3qryEb4K5QeQqRN8dFNvEaiu6aR3qWBcq4x0cHFJuvMn5vLNPiWxh5kCQQCJk3IkRptj9ns4/HyMVfb1XGgOVxuUQZk3t86LRRdZ6nKfHP4oLrYXt71ZZ7IccIp4NUzi0w/Uqm12me1FA2DJAkBJ74RU9ugebF7b8EJid1baE1eTYkBuoa2nR0qdDGUSSwTNinw03Se461XtwIwWkBhiIuW41X/ZQ6MjJcFNHT5BAkAQ2SfPXX5GPbdVgUZXgWefINP1faF5BJeZBT+cOrfjYRzsc4aEsUPHn87mlhZv+TBcDFsAJEz6BG0nay5tlz5RAkBmsOlch17HnIUgCbBzw2bt8O4/MQALoKONdd0ZjhpTuvVheqogeMAEq1u0eMZTPCfRK/QCYzTDsuvpWqJKsjKZ";
        String encodedUrl = URLEncoder.encode(publicKeyString, CommonUtil.UTF8);
        System.out.println("encoded URL:");
        System.out.println(encodedUrl);

        String decodedUrl = URLDecoder.decode(encodedUrl, CommonUtil.UTF8);
        assertEquals(publicKeyString, decodedUrl);

        // encode the exp
    }

    @Test
    public void calculateSignature() throws Exception {
        String httpMethod = "GET";
        String date = CalendarUtil.getDateStringWithZone(Calendar.getInstance(), CalendarUtil.ZONE_DATE_FORMAT_WITH_WEEK, TimeZone.getTimeZone(CommonUtil.TIME_ZONE_UTC), Locale.US);
        String secretKey = "59573390586f8694ebe597c607e9c754"; // pprun's
        String path = "/hjpetstore/rest/products/dog";
        String signature = MessageDigestUtil.calculateSignature(httpMethod, date, path, secretKey);
        signature = URLEncoder.encode(signature, CommonUtil.UTF8);
        System.out.println("signature: ");
        System.out.println(signature);
    }

    /**
     * commented out as we will get the warning of using private API
     *
     */
//    @Ignore
//    public void base64() {
//        String userPassword = "pprun" + ":" + "pprunpprun";
//        String encoding  = new String(Base64.encodeBase64(userPassword.getBytes()), Charset.forName(CommonUtil.UTF8));
//        System.out.println("encode: " + encoding);
//
//        String sunEncoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());
//        System.out.println("sun's encode: " + sunEncoding);
//        assertEquals(encoding, sunEncoding);
//
//    }
}

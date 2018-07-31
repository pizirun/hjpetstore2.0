/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.io.File;
import java.nio.charset.Charset;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Only test the getNewFile method because this is the new introduced method, all other methods came from my
 * experience.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class FileUtilsTest {

    private static final Log log = LogFactory.getLog(FileUtilsTest.class);

    public FileUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getNewFile method, of class FileUtils.
     */
    @Test
    public void testGetNewFile() throws IOException {
        System.out.println("createNewFile");
        String path = System.getProperty("java.io.tmpdir");
        log.info("file path: " + path);

        // no extension
        String fileName = "test";
        String extension = null;
        File result1 = FileUtils.getNewFile(path, fileName, extension);
        assertEquals("test", result1.getName());

        // write something in it to let it create
        result1.deleteOnExit(); // we can run this test again
        new FileOutputStream(result1).write("This is a test".getBytes(Charset.forName(CommonUtil.UTF8)));

        // once again
        fileName = "test";
        File result2 = FileUtils.getNewFile(path, fileName, extension);
        assertEquals("test_1", result2.getName());
        // write something in it to let it create
        result2.deleteOnExit(); // we can run this test again
        new FileOutputStream(result2).write("This is a test".getBytes(Charset.forName(CommonUtil.UTF8)));

        // test file with extension
        fileName = "test";
        extension = "txt";
        File result3 = FileUtils.getNewFile(path, fileName, extension);
        assertEquals("test.txt", result3.getName());
        // write something in it to let it create
        result3.deleteOnExit(); // we can run this test again
        new FileOutputStream(result3).write("This is a test".getBytes(Charset.forName(CommonUtil.UTF8)));

        // once again
        fileName = "test";
        extension = "txt";
        File result4 = FileUtils.getNewFile(path, fileName, extension);
        assertEquals("test_1.txt", result4.getName());
        // write something in it to let it create
        result4.deleteOnExit(); // we can run this test again
        new FileOutputStream(result4).write("This is a test".getBytes(Charset.forName(CommonUtil.UTF8)));
    }
}

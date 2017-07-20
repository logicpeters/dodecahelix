package org.ddx.util;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created on 6/20/2017.
 */
public class FileReaderUtilTest {

    @Test
    public void testReadingFile() throws IOException {

        String testFilePath = "datasets/jburkhardt/basic_english_850.txt";

        String fileString = FileReaderUtil.readFileAsString(testFilePath);
        assertEquals("a", fileString.substring(0,1));

        List<String> fileStringList = FileReaderUtil.readFileAsStringList(testFilePath);
        assertEquals(850, fileStringList.size());
    }
}

package org.ddx.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class FileWriterUtilTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testWriteToFile() throws IOException {
        File file = tempFolder.newFile("my_file.txt");
        String content = FileReaderUtil.readFileAsString("datasets/jburkhardt/basic_english_850.txt");
        FileWriterUtil.writeStringToFile(file, content);

        String writtenContent = FileReaderUtil.readFileAsString(file);
        assertEquals(content, writtenContent);
    }
}

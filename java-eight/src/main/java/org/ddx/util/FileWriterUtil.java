package org.ddx.util;

import java.io.*;

/**
 *
 */
public class FileWriterUtil {

    public static void writeStringToFile(File file, String content) throws IOException {
        try (
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            ) {
                bw.write(content);
        }
    }
}

package org.ddx.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses the original java.io API to read a file and copy its content to a string or list of strings.
 * <p>
 * Works with most versions of Java, including Android apps.
 */
public class FileReaderUtil {

    private static final int BYTE_BUFFER_SIZE = 8192;

    /**
     * Reads a file object and returns in string format.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFileAsString(File file) throws IOException {
        try (InputStream in = new FileInputStream(file);) {
            return readFileFromStreamAsString(in);
        }
    }

    /**
     * Reads a file with the given path from the classpath and returns it in string format.
     *
     * @param filePath - absolute or relative (to the classpath)
     * @return
     * @throws IOException
     */
    public static String readFileAsString(String filePath) throws IOException {
        try (InputStream in = FileReaderUtil.class.getClassLoader().getResourceAsStream(filePath);) {
            return readFileFromStreamAsString(in);
        }
    }

    /**
     * Reads a file from a stream as a string.
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFileFromStreamAsString(InputStream in) throws IOException {

        try (
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader bReader = new BufferedReader(isReader);
        ) {
            StringBuilder fileString = new StringBuilder();
            char[] buffer = new char[BYTE_BUFFER_SIZE];
            int read;
            while ((read = bReader.read(buffer, 0, buffer.length)) > 0) {
                fileString.append(buffer, 0, read);
            }
            return fileString.toString();
        }
    }

    /**
     * Reads a file with the given path from the classpath and return it as a list of strings.
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> readFileAsStringList(String filePath) throws IOException {
        try (InputStream in = FileReaderUtil.class.getClassLoader().getResourceAsStream(filePath);) {
            return readFileFromStreamAsStringList(in);
        }
    }

    /**
     * Reads a File object, returning a list of strings.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> readFileAsStringList(File file) throws IOException {
        try (InputStream in = new FileInputStream(file);) {
            return readFileFromStreamAsStringList(in);
        }
    }

    /**
     * Reads a file from an inputstream and returns a list of strings.
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static List<String> readFileFromStreamAsStringList(InputStream in) throws IOException {
        List<String> stringList = new ArrayList<String>();
        try (
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isReader);
        ) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (!"".equals(sCurrentLine.trim())) {
                    stringList.add(sCurrentLine);
                }
            }
        }
        return stringList;
    }

}

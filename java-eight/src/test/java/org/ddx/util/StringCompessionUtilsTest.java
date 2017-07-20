package org.ddx.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *  Tests the compression code against various sample documents.
 */
public class StringCompessionUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(StringCompessionUtilsTest.class);

    private static final String TEST_COMPRESSION_FILE = "datasets/jburkhardt/dawkins.txt";
    private static final String TEST_UTF8_COMPRESSION_FILE = "samples/UTF-8_sample.html";

    @Test
    public void testCompressDecompressToBytes() throws Exception {
        // compress to/from byte array
        String originalContent = FileReaderUtil.readFileAsString(TEST_COMPRESSION_FILE);
        byte[] compressedBytes = StringCompressionUtil.compress(originalContent);
        logger.debug("compressed size: {}", compressedBytes.length);
        String decompressed = StringCompressionUtil.decompress(compressedBytes);
        assertEquals(originalContent, decompressed);

        originalContent = FileReaderUtil.readFileAsString(TEST_UTF8_COMPRESSION_FILE);
        compressedBytes = StringCompressionUtil.compress(originalContent);
        decompressed = StringCompressionUtil.decompress(compressedBytes);
        assertEquals(originalContent, decompressed);
    }

    @Test
    public void testCompressDecompressToString() throws Exception {
        String originalContent = FileReaderUtil.readFileAsString(TEST_COMPRESSION_FILE);
        String compressed = StringCompressionUtil.compressToString(originalContent);
        String decompressed = StringCompressionUtil.decompressString(compressed);
        assertEquals(originalContent, decompressed);
        assertTrue(compressed.length() < decompressed.length());

        originalContent = FileReaderUtil.readFileAsString(TEST_UTF8_COMPRESSION_FILE);
        compressed = StringCompressionUtil.compressToString(originalContent);
        decompressed = StringCompressionUtil.decompressString(compressed);
        assertEquals(originalContent, decompressed);
        assertTrue(compressed.length() < decompressed.length());
    }
}

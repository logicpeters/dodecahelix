package org.ddx.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Basic string encryption using java GZIP compression API.
 *
 */
public class StringCompressionUtil {

    /**
     *  CHARSET used when encoding/decoding the compressed byte[] as a string.
     */
    private static final Charset DECODING_CHARSET = StandardCharsets.ISO_8859_1;

    /**
     *  CHARSET used for compression of and decompression of the byte[]
     */
    private static final Charset COMPRESSION_CHARSET = StandardCharsets.UTF_8;

    private static final int BYTE_BUFFER_SIZE = 8192;

    /**
     *  Compresses text using the GZIP algorithm into an encoded string.
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compressToString(String str) throws IOException {
        byte[] compressed = compress(str);
        return DECODING_CHARSET.decode(ByteBuffer.wrap(compressed)).toString();
    }

    /**
     *  Compresses text using the GZIP algorithm as a byte[].
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("can't compress empty or null string");
        }

        byte[] result = null;
        try(
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
        ) {
            gzip.write(str.getBytes(COMPRESSION_CHARSET));
            gzip.close();
            out.close();

            result = out.toByteArray();
        }
        return result;
    }

    /**
     *
     * Decompresses text that was originally compressed using the GZIP algorithm.
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String decompressString(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }

        byte[] compressedBytes = DECODING_CHARSET.encode(str).array();
        return decompress(compressedBytes);
        //return decompress(str.getBytes(DECODING_CHARSET));
    }

    /**
     * Decompresses text (in byte[] form) that was compressed using the GZIP algorithm.
     *
     * @param compressedBytes
     * @return
     * @throws IOException
     */
    public static String decompress(byte[] compressedBytes) throws IOException {

        String decompressedString = null;
        try (
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressedBytes));
            BufferedReader reader = new BufferedReader(new InputStreamReader(gis, COMPRESSION_CHARSET));
        ) {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[BYTE_BUFFER_SIZE];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            decompressedString = builder.toString();
        }

        return decompressedString;
    }
}

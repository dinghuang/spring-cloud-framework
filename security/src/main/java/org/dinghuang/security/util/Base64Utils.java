package org.dinghuang.security.util;

import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class Base64Utils {

    private Base64Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ENCODING = "UTF-8";

    /**
     * 加密
     *
     * @param text text
     * @return String
     */
    public static String encode(String text) throws Exception {
        byte[] binByte = text.getBytes(ENCODING);
        byte[] output = new byte[1000];
        Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION, true);
        // 要压缩的数据包
        compresser.setInput(binByte);
        // 完成
        compresser.finish();
        // 压缩，返回的是数据包经过缩缩后的大小
        int compressedDataLength = compresser.deflate(output);
        byte[] base64Input = Base64.encodeBase64(Arrays.copyOf(output, compressedDataLength));
        return new String(base64Input, ENCODING);
    }

    /**
     * 解析Token
     *
     * @param text text
     * @return String
     */
    public static String decode(String text) throws Exception {
        byte[] base64Data = text.getBytes(ENCODING);
        byte[] base64Input = Base64.decodeBase64(base64Data);
        byte[] output = new byte[1000];
        Inflater compresser = new Inflater(true);
        // 要压缩的数据包
        compresser.setInput(base64Input);
        // 压缩，返回的是数据包经过缩缩后的大小
        int compressedDataLength = compresser.inflate(output);
        // 完成
        compresser.end();
        return new String(Arrays.copyOf(output, compressedDataLength), ENCODING);
    }
}

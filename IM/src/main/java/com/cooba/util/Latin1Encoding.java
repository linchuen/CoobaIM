package com.cooba.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Latin1Encoding {
    // byte[] -> String (ISO-8859-1)
    public static String byteArrayToLatin1String(byte[] bytes) {
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    // String -> byte[] (ISO-8859-1)
    public static byte[] latin1StringToByteArray(String str) {
        return str.getBytes(StandardCharsets.ISO_8859_1);
    }

    public static void main(String[] args) {
        byte[] originalBytes = { 65, 66, 67, -1, -2, -3 }; // "ABCÿþü"
        String encoded = byteArrayToLatin1String(originalBytes);
        byte[] decoded = latin1StringToByteArray(encoded);

        System.out.println("Encoded String: " + encoded);
        System.out.println("Decoded Bytes: " + Arrays.toString(decoded));
        System.out.println("Matches Original: " + Arrays.equals(originalBytes, decoded));
    }
}


package com.cooba.util;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class LZ4Util {
    private static final LZ4Factory factory = LZ4Factory.fastestInstance();

    public static byte[] compress(String data) {
        byte[] input = data.getBytes();
        LZ4Compressor compressor = factory.fastCompressor();
        int maxCompressedLength = compressor.maxCompressedLength(input.length);
        ByteBuffer outputBuffer = ByteBuffer.allocate(maxCompressedLength + 4);
        outputBuffer.putInt(input.length);
        compressor.compress(ByteBuffer.wrap(input), outputBuffer);

        byte[] compressedData = new byte[outputBuffer.position()];
        outputBuffer.flip();
        outputBuffer.get(compressedData);

        return compressedData; // 直接返回二进制数据
    }

    public static String decompress(byte[] compressedData) {
        ByteBuffer inputBuffer = ByteBuffer.wrap(compressedData);
        int originalLength = inputBuffer.getInt();
        byte[] decompressedData = new byte[originalLength];

        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        decompressor.decompress(inputBuffer, ByteBuffer.wrap(decompressedData));

        return new String(decompressedData);
    }

    public static void main(String[] args) {
        String chat = "Hello";
        System.out.println("Original: " + chat);

        byte[] compressed = compress(chat);
        System.out.println("Compressed (byte[]): " + Arrays.toString(compressed));

        String decompressed = decompress(compressed);
        System.out.println("Decompressed: " + decompressed);
    }
}

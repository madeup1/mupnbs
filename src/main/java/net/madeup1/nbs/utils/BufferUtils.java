package net.madeup1.nbs.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BufferUtils
{
    public static int getUnsignedShort(ByteBuffer buffer)
    {
        return Short.toUnsignedInt(buffer.getShort());
    }

    public static String getString(ByteBuffer buffer)
    {
        byte[] bytes = new byte[buffer.getInt()];
        buffer.get(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void skipString(ByteBuffer buffer)
    {
        int length = buffer.getInt();
        skip(buffer, length);
    }

    public static int getUnsignedByte(ByteBuffer buffer)
    {
        return Byte.toUnsignedInt(buffer.get());
    }

    public static void skip(ByteBuffer buffer, int skip)
    {
        buffer.position(buffer.position() + skip);
    }
}

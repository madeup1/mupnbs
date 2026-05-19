package net.madeup1.nbs.utils;

public class NoteUtils
{
    public static long pack(
            int instrument,
            int key,
            int volume,
            int layer
    )
    {
        return ((long) instrument) |
                ((long) key << 8) |
                ((long) volume << 16) |
                ((long) layer << 24);
    }

    public static int instrument(long packed)
    {
        return (int) (packed & 0xFF);
    }

    public static int key(long packed)
    {
        return (int) ((packed >>> 8) & 0xFF);
    }

    public static int volume(long packed)
    {
        return (int) ((packed >>> 16) & 0xFF);
    }

    public static int layer(long packed)
    {
        return (int) ((packed >>> 24) & 0xFFFF);
    }
}

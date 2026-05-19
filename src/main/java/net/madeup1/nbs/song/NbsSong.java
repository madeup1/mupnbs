package net.madeup1.nbs.song;

public sealed interface NbsSong permits NbsSongImpl
{
    byte version();
    byte instrumentCount();
    int length();
    int layerCount();
    String name();
    String author();
    String originalAuthor();
    String description();
    int tempo();
    byte timeSignature();
    boolean isLoop();
    byte maxLoopCount();
    int loopStart();
    long delay();
}

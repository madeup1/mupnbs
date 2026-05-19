package net.madeup1.nbs.song;

import net.kyori.adventure.audience.Audience;
import net.madeup1.nbs.player.NbsPlayer;

import java.util.ArrayList;

public record NbsSongImpl(
        byte version, byte instrumentCount,
        int length, int layerCount, String name,
        String author, String originalAuthor,
        String description, int tempo, byte timeSignature,
        boolean isLoop, byte maxLoopCount, int loopStart, ArrayList<TickInformation> ticks
) implements NbsSong, Playable
{
    @Override
    public void play(Audience audience)
    {
        NbsPlayer player = new NbsPlayer(audience, ticks, this);

        player.start();
    }

    // turns 12.25 tps into
    @Override
    public long delay()
    {
        return 100_000 / tempo;
    }
}

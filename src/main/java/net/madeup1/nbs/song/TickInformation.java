package net.madeup1.nbs.song;

import net.kyori.adventure.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class TickInformation
{
    private final int start;
    private final ArrayList<Sound> sounds;

    public TickInformation(int start)
    {
        this.start = start;
        this.sounds = new ArrayList<>();
    }

    public void add(Sound sound)
    {
        sounds.add(sound);
    }

    public List<Sound> sounds()
    {
        return sounds;
    }

    public int start()
    {
        return start;
    }
}

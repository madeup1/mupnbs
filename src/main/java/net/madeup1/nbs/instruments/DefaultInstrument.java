package net.madeup1.nbs.instruments;

import net.minestom.server.sound.SoundEvent;

public record DefaultInstrument(SoundEvent event) implements Instrument
{
    @Override
    public byte key()
    {
        return 45;
    }
}

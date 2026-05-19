package net.madeup1.nbs.instruments;

import net.minestom.server.sound.SoundEvent;

public record CustomInstrument(SoundEvent event, byte key) implements Instrument
{
}

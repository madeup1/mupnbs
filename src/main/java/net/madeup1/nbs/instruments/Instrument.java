package net.madeup1.nbs.instruments;

import net.kyori.adventure.sound.Sound;
import net.minestom.server.sound.SoundEvent;

import java.util.List;

public sealed interface Instrument permits CustomInstrument, DefaultInstrument
{
    Instrument HARP = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_HARP);
    Instrument BASS = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_BASS);
    Instrument BASE_DRUM = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_BASEDRUM);
    Instrument SNARE = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_SNARE);
    Instrument HAT = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_HAT);
    Instrument GUITAR = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_GUITAR);
    Instrument FLUTE = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_FLUTE);
    Instrument BELL = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_BELL);
    Instrument CHIME = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_CHIME);
    Instrument XYLOPHONE = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_XYLOPHONE);
    Instrument IRON_XYLOPHONE = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE);
    Instrument COW_BELL = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_COW_BELL);
    Instrument DIDGERIDOO = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_DIDGERIDOO);
    Instrument BIT = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_BIT);
    Instrument BANJO = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_BANJO);
    Instrument PLING = new DefaultInstrument(SoundEvent.BLOCK_NOTE_BLOCK_PLING);

    List<Instrument> DEFAULT = List.of(
            HARP, BASS, BASE_DRUM, SNARE, HAT,
            GUITAR, FLUTE, BELL, CHIME, XYLOPHONE,
            IRON_XYLOPHONE, COW_BELL, DIDGERIDOO,
            BIT, BANJO, PLING
    );

    SoundEvent event();
    byte key();

    default Sound toSound(Sound.Source source, byte volume, byte key)
    {
        return Sound.sound(
                event(),
                source,
                volume / 100f,
                (float) Math.pow(2, (key - key()) / 12f)
        );
    }
}

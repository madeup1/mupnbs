package net.madeup1.nbs;

import it.unimi.dsi.fastutil.longs.*;
import net.kyori.adventure.sound.Sound;
import net.madeup1.nbs.instruments.CustomInstrument;
import net.madeup1.nbs.instruments.Instrument;
import net.madeup1.nbs.instruments.InstrumentInformation;
import net.madeup1.nbs.layer.Layer;
import net.madeup1.nbs.song.NbsSongImpl;
import net.madeup1.nbs.song.Playable;
import net.madeup1.nbs.song.TickInformation;
import net.madeup1.nbs.utils.BufferUtils;
import net.madeup1.nbs.utils.NoteUtils;
import net.minestom.server.sound.SoundEvent;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NbsParser
{
    public static Playable parse(Path path) throws IOException
    {
        var buffer = ByteBuffer.wrap(Files.readAllBytes(path)).order(ByteOrder.LITTLE_ENDIAN);

        buffer.getShort();
        byte version = buffer.get();
        byte instrumentCount = buffer.get();
        int length = BufferUtils.getUnsignedShort(buffer);
        int layerCount = BufferUtils.getUnsignedShort(buffer);
        String name = BufferUtils.getString(buffer);
        String author = BufferUtils.getString(buffer);
        String originalAuthor = BufferUtils.getString(buffer);
        String description = BufferUtils.getString(buffer);
        int tempo = BufferUtils.getUnsignedShort(buffer);

        BufferUtils.skip(buffer, 2);
        byte signature = buffer.get();

        BufferUtils.skip(buffer, 20);
        BufferUtils.skipString(buffer);
        boolean loop = buffer.get() == 1;
        byte maxLoopCount = buffer.get();
        int loopStart = BufferUtils.getUnsignedShort(buffer);

        List<Tick> notes = getNotes(buffer);
        List<Layer> layers = getLayers(buffer, layerCount);
        List<Instrument> instruments = getInstruments(buffer, instrumentCount);

        ArrayList<TickInformation> ticks = new ArrayList<>(notes.size());

        for (Tick tick : notes)
        {
            TickInformation information = new TickInformation(tick.start());

            LongList list = tick.information;
            for (int i = 0; i < list.size(); i++)
            {
                long value = list.getLong(i);

                Instrument instrument = instruments.get(NoteUtils.instrument(value));
                Layer layer = layers.get(NoteUtils.layer(value));

                information.add(instrument.toSound(
                        Sound.Source.RECORD,
                        (byte) (NoteUtils.volume(value) * layer.volume()),
                        (byte) NoteUtils.key(value)
                ));
            }

            ticks.add(information);
        }

        return new NbsSongImpl(
                version, instrumentCount, length, layerCount, name, author,
                originalAuthor, description, tempo, signature, loop,
                maxLoopCount, loopStart, ticks
        );
    }

    private static List<Layer> getLayers(ByteBuffer buffer, int layerCount)
    {
        ArrayList<Layer> layers = new ArrayList<>(layerCount);

        for (int i = 0; i < layerCount; i++)
        {
            String name = BufferUtils.getString(buffer);
            BufferUtils.skip(buffer, 1);
            byte volume = buffer.get();
            BufferUtils.skip(buffer, 1);

            layers.add(new Layer(name, (volume / 100f)));
        }

        return layers;
    }

    private static List<Instrument> getInstruments(ByteBuffer buffer, int instrumentCount)
    {
        int customCount = BufferUtils.getUnsignedByte(buffer);
        ArrayList<Instrument> instruments = new ArrayList<>(customCount + instrumentCount);

        instruments.addAll(Instrument.DEFAULT.subList(0, instrumentCount));

        for (int i = 0; i < customCount; i++)
        {
            String name = BufferUtils.getString(buffer).toLowerCase();
            BufferUtils.skipString(buffer);
            byte key = buffer.get();
            BufferUtils.skip(buffer, 1);


            SoundEvent event = SoundEvent.fromKey(name);
            if (event == null)
                throw new IllegalArgumentException("Invalid sound name! (" + name + ")");

            instruments.add(new CustomInstrument(event, key));
        }

        return instruments;
    }

    private static List<Tick> getNotes(ByteBuffer buffer)
    {
        List<Tick> ticks = new ArrayList<>(128);

        int index = -1;
        while (true)
        {
            int jump = BufferUtils.getUnsignedShort(buffer);
            if (jump == 0) break;

            index += jump;
            LongList information = getInformation(buffer);
            if (!information.isEmpty())
                ticks.add(new Tick(index, information));
        }

        return ticks;
    }

    private static LongList getInformation(ByteBuffer buffer)
    {
        LongList informations = new LongArrayList(4);
        int layer = -1;

        while (true)
        {
            int jump = BufferUtils.getUnsignedShort(buffer);

            if (jump == 0)
                break;

            layer += jump;

            byte instrument = buffer.get();
            byte key = buffer.get();
            byte volume = buffer.get();
            BufferUtils.skip(buffer, 3);

            informations.add(NoteUtils.pack(instrument, key, volume, layer));
        }

        return informations;
    }

    public record Tick(int start, LongList information)
    {

    }
}

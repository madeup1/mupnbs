package net.madeup1.nbs.player;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.madeup1.nbs.song.NbsSong;
import net.madeup1.nbs.song.NbsSongImpl;
import net.madeup1.nbs.song.TickInformation;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NbsPlayer
{
    private static final ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

    public NbsPlayer(Audience audience, ArrayList<TickInformation> ticks, NbsSong song)
    {
        if (ticks.isEmpty())
            throw new IllegalArgumentException("Song with no ticks!");

        long delay = song.delay();

        service.submit(new Runnable()
        {
            int index = 0;
            int tick = 0;
            @Override
            public void run()
            {
                if (index >= ticks.size())
                {
                    if (song.isLoop())
                    {
                        index = 0;
                    }
                    else
                    {
                        return;
                    }
                }

                TickInformation tickInfo = ticks.get(index);
                int start = tickInfo.start();

                try
                {
                    Thread.sleep((start - tick) * delay);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    return;
                }

                tick = start;

                for (Sound sound : tickInfo.sounds())
                {
                    audience.playSound(sound, Sound.Emitter.self());
                }

                index++;
            }

        });
    }

    public void start()
    {

    }
}

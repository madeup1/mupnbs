import net.madeup1.nbs.NbsParser;
import net.madeup1.nbs.song.Playable;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

public class SongTest
{
    @Test
    public void parse() throws IOException
    {
        Playable song = NbsParser.parse(Path.of("songs/nirvana.nbs"));
    }
}

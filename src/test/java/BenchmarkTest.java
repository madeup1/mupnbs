import net.madeup1.nbs.NbsParser;
import org.junit.Test;

import java.nio.file.Path;

public class BenchmarkTest
{
    @Test
    public void benchmarkParse() throws Exception
    {
        Path path = Path.of("songs/calamity.nbs");

        // warmup
        for (int i = 0; i < 10000; i++)
        {
            NbsParser.parse(path);
        }

        int iterations = 1000;

        long start = System.nanoTime();

        for (int i = 0; i < iterations; i++)
        {
            NbsParser.parse(path);
        }

        long end = System.nanoTime();
    }
}

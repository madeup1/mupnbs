# Mup NBS

Mup NBS is a lightweight `.nbs` parser and playback library for Java.

It is designed for low-overhead playback and accurate timing using virtual-thread scheduling with support for fractional TPS playback.

## Features

- OpenNBS `.nbs` parsing
- Audience playback
- Fractional TPS support
- Virtual-thread scheduler
- Custom instrument support

## Usage

```java
Playable song = NbsParser.parse(path);

song.play(audience);
```

## Playback

Mup NBS uses virtual threads for playback scheduling, allowing songs to run at fractional TPS values instead of being locked to whole Minecraft ticks.

This improves timing precision for songs that do not map cleanly to integer tick intervals.

## Supported Format

- `.nbs` (OpenNBS)

## License

MIT
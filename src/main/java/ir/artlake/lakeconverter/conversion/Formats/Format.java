package ir.artlake.lakeconverter.conversion.Formats;

import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.VideoAttributes;

public interface Format {

    String getFileExtension();
    AudioAttributes getAudioAttributes();
    VideoAttributes getVideoAttributes();
}

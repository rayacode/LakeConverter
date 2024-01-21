package ir.artlake.lakeconverter.conversion.Formats;

import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

public class AAC implements Format{
    AudioAttributes audioAttributes;
    VideoAttributes videoAttributes;
    EncodingAttributes encodingAttributes;
    @Override
    public String getFileExtension() {
        return ".aac";
    }

    @Override
    public AudioAttributes getAudioAttributes() {
        return audioAttributes;
    }

    @Override
    public VideoAttributes getVideoAttributes() {
        return videoAttributes;
    }

    @Override
    public EncodingAttributes getEncodingAttributes() {
        return encodingAttributes;
    }


}

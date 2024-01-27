package ir.artlake.lakeconverter.conversion.Formats;

import javafx.beans.value.ObservableValue;
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

    @Override
    public void setDefault() {
        audioAttributes.setCodec("aac");
        audioAttributes.setBitRate(128000);
        audioAttributes.setSamplingRate(44100);
        audioAttributes.setChannels(2);


    }

    @Override
    public String getCurrentConfigForTextButton() {
        return null;
    }


}

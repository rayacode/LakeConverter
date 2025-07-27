package codes.rayacode.lakeconverter.conversion.Formats;

import javafx.beans.value.ObservableValue;
import codes.rayacode.lakeconverter.jave.encode.AudioAttributes;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.encode.VideoAttributes;

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

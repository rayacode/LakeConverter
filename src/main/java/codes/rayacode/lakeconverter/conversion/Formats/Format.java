package codes.rayacode.lakeconverter.conversion.Formats;

import javafx.beans.value.ObservableValue;
import codes.rayacode.lakeconverter.jave.encode.AudioAttributes;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.encode.VideoAttributes;

public interface Format {

    String getFileExtension();
    AudioAttributes getAudioAttributes();
    VideoAttributes getVideoAttributes();
    EncodingAttributes getEncodingAttributes();
    void setDefault();
    String getCurrentConfigForTextButton();


}

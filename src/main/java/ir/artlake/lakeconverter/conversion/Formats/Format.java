package ir.artlake.lakeconverter.conversion.Formats;

import javafx.beans.value.ObservableValue;
import ir.artlake.lakeconverter.jave.encode.AudioAttributes;
import ir.artlake.lakeconverter.jave.encode.EncodingAttributes;
import ir.artlake.lakeconverter.jave.encode.VideoAttributes;

public interface Format {

    String getFileExtension();
    AudioAttributes getAudioAttributes();
    VideoAttributes getVideoAttributes();
    EncodingAttributes getEncodingAttributes();
    void setDefault();
    String getCurrentConfigForTextButton();


}

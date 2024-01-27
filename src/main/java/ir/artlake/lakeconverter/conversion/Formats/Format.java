package ir.artlake.lakeconverter.conversion.Formats;

import javafx.beans.value.ObservableValue;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

public interface Format {

    String getFileExtension();
    AudioAttributes getAudioAttributes();
    VideoAttributes getVideoAttributes();
    EncodingAttributes getEncodingAttributes();
    void setDefault();
    String getCurrentConfigForTextButton();


}

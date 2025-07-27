package codes.rayacode.lakeconverter.conversion.Formats;

import codes.rayacode.lakeconverter.jave.info.VideoSize;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public abstract class VideoFormat implements Format {
    public abstract ObservableList<String> getVideoEncoders();
    public abstract ObservableList<String> getAudioEncoders();
    public abstract ObservableList<Integer> getBitRates();
    public abstract ObservableList<Integer> getFrameRates();
    public abstract ObservableList<VideoSize> getVideoResolutions();
    public abstract Map<String, List<String>> getEncoderPixelFormats();
}

package ir.artlake.lakeconverter;

import javafx.application.Platform;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConvertProgressListener implements EncoderProgressListener {
    private final BiConsumer<Double, Double> progressConsumer;

    public ConvertProgressListener(BiConsumer<Double, Double> progressConsumer) {
        this.progressConsumer = progressConsumer;
    }

    public void progress(int p) {
        double progress = p / 1000.0;
        Platform.runLater(() -> {
            progressConsumer.accept(progress, 1.0);
        });
    }

    public void message(String m) {}
    public void sourceInfo(MultimediaInfo m) {}
}
package ir.artlake.lakeconverter.conversion;

import javafx.application.Platform;
import ir.artlake.lakeconverter.jave.info.MultimediaInfo;
import ir.artlake.lakeconverter.jave.progress.EncoderProgressListener;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConvertProgressListener implements EncoderProgressListener {


    private BiConsumer<Double, Double> progressConsumer;

    public ConvertProgressListener(){

    }
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
    public BiConsumer<Double, Double> getProgressConsumer() {
        return progressConsumer;
    }

    public void setProgressConsumer(BiConsumer<Double, Double> progressConsumer) {
        this.progressConsumer = progressConsumer;
    }
}
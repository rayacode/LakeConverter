package ir.artlake.lakeconverter.fileoperations.concurency;

import javafx.application.Platform;

import java.util.function.BiConsumer;

public class AddFilesProgressListener {
    private final BiConsumer<Double, Double> progressConsumer;

    public AddFilesProgressListener(BiConsumer<Double, Double> progressConsumer){
        this.progressConsumer = progressConsumer;
    }

    public void progress(int p,int total) {
        double progress = (double)p / total;
        System.out.println("Progress: " + progress);
        Platform.runLater(() -> {
            progressConsumer.accept(progress, 1.0);
        });
    }
}

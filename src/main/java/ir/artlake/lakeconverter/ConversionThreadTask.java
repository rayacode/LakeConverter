package ir.artlake.lakeconverter;


import javafx.application.Platform;

import java.util.function.Consumer;

public class ConversionThreadTask implements Runnable {
    private final Converter converter;
    private final ConversionProgressTask task;
    private final Consumer<Boolean> onConversionComplete;

    public ConversionThreadTask(Converter converter, ConversionProgressTask task, Consumer<Boolean> onConversionComplete) {
        this.converter = converter;
        this.task = task;
        this.onConversionComplete = onConversionComplete;
    }

    @Override
    public void run() {
        boolean success = converter.convert();
        Platform.runLater(() -> onConversionComplete.accept(success));
    }
}
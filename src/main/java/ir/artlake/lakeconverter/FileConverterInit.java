package ir.artlake.lakeconverter;

import javafx.application.Platform;

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class FileConverterInit {
    private final ConversionProgressTask task;
    private final ConvertProgressListener listener;
    private final Converter converter;
    private final Consumer<Boolean> onConversionComplete;
    private  Semaphore semaphore;

    public FileConverterInit(Consumer<Boolean> onConversionComplete, String source, String target, Semaphore semaphore) {
        task = new ConversionProgressTask();
        listener = new ConvertProgressListener(task);
        converter = new Converter(listener,source,target);
        this.onConversionComplete = onConversionComplete;
        this.semaphore = semaphore;

    }

    public void startConversion() {
        new Thread(() -> {
            try {
                semaphore.acquire();
                boolean success = converter.convert();
                Platform.runLater(() -> onConversionComplete.accept(success));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // restore interrupt status
            } finally {
                semaphore.release();
            }
        }).start();
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public ConversionProgressTask getTask() {
        return task;
    }

    public ConvertProgressListener getListener() {
        return listener;
    }

    public Converter getConverter() {
        return converter;
    }

    public Consumer<Boolean> getOnConversionComplete() {
        return onConversionComplete;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}

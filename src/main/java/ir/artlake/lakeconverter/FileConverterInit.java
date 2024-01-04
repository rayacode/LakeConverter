package ir.artlake.lakeconverter;

import javafx.application.Platform;

import java.io.File;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class FileConverterInit {
    private final ConversionProgressTask task;
    private final ConvertProgressListener listener;
    private final Converter converter;
    private final Consumer<Boolean> onConversionComplete;
    private boolean convertAgain;
    private boolean initConvert;
    private  Semaphore semaphore;

    public FileConverterInit(Consumer<Boolean> onConversionComplete, String source, String target, Semaphore semaphore) {
        task = new ConversionProgressTask();
        listener = new ConvertProgressListener(task);
        converter = new Converter(listener,source,target);
        this.onConversionComplete = onConversionComplete;
        this.semaphore = semaphore;
        this.convertAgain = true;
        this.initConvert = false;

    }

    public void startConversion() {
        if (convertAgain) {
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
            convertAgain = false;
        }

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

    public boolean isConvertAgain() {
        return convertAgain;
    }

    public void setConvertAgain(boolean convertAgain) {
        this.convertAgain = convertAgain;
    }

    public boolean isInitConvert() {
        return initConvert;
    }

    public void setInitConvert(boolean initConvert) {
        this.initConvert = initConvert;
    }
}

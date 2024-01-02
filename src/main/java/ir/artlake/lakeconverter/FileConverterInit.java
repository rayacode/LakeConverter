package ir.artlake.lakeconverter;

import java.util.function.Consumer;

public class FileConverterInit {
    private final ConversionProgressTask task;
    private final ConvertProgressListener listener;
    private final Converter converter;
    private final Consumer<Boolean> onConversionComplete;
    public FileConverterInit(Consumer<Boolean> onConversionComplete, String source, String target) {
        task = new ConversionProgressTask();
        listener = new ConvertProgressListener(task);
        converter = new Converter(listener,source,target);
        this.onConversionComplete = onConversionComplete;
    }
    public void startConversion() {
        ConversionThreadTask conversionTask = new ConversionThreadTask(converter, task, onConversionComplete);
        Thread thread = new Thread(conversionTask, "ConversionThread" + System.currentTimeMillis());
        thread.start();
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
}

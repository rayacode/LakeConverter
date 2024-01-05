package ir.artlake.lakeconverter;

import java.io.File;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

import static ir.artlake.lakeconverter.Main.executorService;

public class FileConverterInit {
    private final ConversionService service;

    private final Consumer<Boolean> onConversionComplete;
    private Semaphore semaphore;
    private boolean convertAgain;
    private File source;

    public FileConverterInit(Consumer<Boolean> onConversionComplete, String source, String target, Semaphore semaphore) {
        this.semaphore = semaphore;
        service = new ConversionService(source, target, semaphore);
        this.source = new File(source);
        service.setExecutor(executorService);
        this.onConversionComplete = onConversionComplete;
        this.convertAgain = true;
    }

    public void startConversion() {
        if (convertAgain) {
            service.start();
            convertAgain = false;
        }
    }

    public ConversionService getTask() {
        return service;
    }

    public File getSource() {
        return source;
    }
}

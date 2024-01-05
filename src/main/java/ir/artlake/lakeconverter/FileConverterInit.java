package ir.artlake.lakeconverter;

import java.io.File;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

import static ir.artlake.lakeconverter.Main.executorService;

public class FileConverterInit {
    private final ConversionService service;


    private Semaphore semaphore;

    private File source;

    public FileConverterInit(String source, String target, Semaphore semaphore) {
        this.semaphore = semaphore;
        service = new ConversionService(source, target, semaphore);
        this.source = new File(source);
        service.setExecutor(executorService);


    }

    public void startConversion() {
            service.start();
    }
    public void restartConversion(){

        service.cancel();
        service.reset();

        service.start();
    }
    public void deleteOrCancelConvertFileThread(){
        service.cancel();
    }
    public ConversionService getTask() {
        return service;
    }

    public File getSource() {
        return source;
    }
}

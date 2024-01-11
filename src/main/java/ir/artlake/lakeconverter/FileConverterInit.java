package ir.artlake.lakeconverter;

import javafx.concurrent.Worker;

import java.io.File;
import java.util.concurrent.Semaphore;

import static ir.artlake.lakeconverter.Main.executorService;

public class FileConverterInit {
    private final ConversionService service;

    private boolean isConverted;

    private Semaphore semaphore;

    private File source;

    public FileConverterInit(String source, String target, Semaphore semaphore) {
        this.semaphore = semaphore;
        service = new ConversionService(source, target, semaphore);
        this.source = new File(source);
        service.setExecutor(executorService);
        this.isConverted = false;


    }

    public void startConversion() {
            if(service.getState() == Worker.State.READY && isConverted == false) {
                service.start();
                isConverted = true;
            }
    }
    public void restartConversion(){

        service.cancel();
        service.reset();

        service.start();
    }
    public void deleteOrCancelConvertFileThread(){

        service.cancel();
        service.getHeadServiceConvertClass().getEncoder().abortEncoding();




    }
    public ConversionService getTask() {
        return service;
    }

    public File getSource() {
        return source;
    }
}

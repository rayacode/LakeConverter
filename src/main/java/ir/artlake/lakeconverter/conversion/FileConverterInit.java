package ir.artlake.lakeconverter.conversion;

import javafx.concurrent.Worker;

import java.io.File;
import java.util.concurrent.Semaphore;

import static ir.artlake.lakeconverter.Main.executorService;

//import static ir.artlake.lakeconverter.Main.executorService;

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

    public synchronized  void startConversion() {
            if(service.getState() == Worker.State.READY && isConverted == false) {

                    service.start();
                    isConverted = true;

            }
    }
    public synchronized  void restartConversion(){

        //service.cancel();
        service.reset();

        service.start();
    }
    public synchronized void deleteOrCancelConvertFileThread(){
        if (service.getState() == Worker.State.RUNNING || service.getState() == Worker.State.SCHEDULED) {
            service.cancel();
        }







    }
    public ConversionService getTask() {
        return service;
    }

    public File getSource() {
        return source;
    }
}

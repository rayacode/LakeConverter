package ir.artlake.lakeconverter.conversion;

import ir.artlake.lakeconverter.ConvertCellWidget;
import ir.artlake.lakeconverter.conversion.Formats.Format;
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

    private ConvertCellWidget convertCellWidget;

    private String targetFolder;

    public FileConverterInit(String source, String target, Semaphore semaphore, Format format) {
        this.semaphore = semaphore;

        service = new ConversionService(source, target, semaphore, format);
        this.targetFolder = target;
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
    public void changeFormat(Format format){
        if (!service.isSingleFormatChanged()) {
            service.setTargetFormat(format);
            service.setSingleFormatChanged(true);
        }


    }

    public ConversionService getService() {
        return service;
    }

    public File getSource() {
        return source;
    }


    public ConvertCellWidget getConvertCellWidget() {
        return convertCellWidget;
    }

    public void setConvertCellWidget(ConvertCellWidget convertCellWidget) {
        this.convertCellWidget = convertCellWidget;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }
}

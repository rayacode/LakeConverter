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
    Format format;
    private ConvertCellWidget convertCellWidget;



    public FileConverterInit(String source, String target, Semaphore semaphore, Format format) {
        this.semaphore = semaphore;
        this.format = format;
        service = new ConversionService(source, target, semaphore, format);
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
            convertCellWidget.setFormat(format);
            service.setSingleFormatChanged(true);
        }


    }

    public ConversionService getService() {
        return service;
    }

    public File getSource() {
        return source;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
    public ConvertCellWidget getConvertCellWidget() {
        return convertCellWidget;
    }

    public void setConvertCellWidget(ConvertCellWidget convertCellWidget) {
        this.convertCellWidget = convertCellWidget;
    }
}

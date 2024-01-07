package ir.artlake.lakeconverter;


import javafx.concurrent.Worker;

import java.io.File;


import static ir.artlake.lakeconverter.FileService.fileConverterInitMap;

public class QConversionManager {

    /*private final Map<File, FileConverterInit> fileConverterInitMap;

    public QConversionManager(Map<File, FileConverterInit> fileConverterInitMap) {
        this.fileConverterInitMap = fileConverterInitMap;
    }*/

    public void startConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap) {
            fileConverterInit.startConversion();
        }
    }
    public void resetConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap) {
            fileConverterInit.restartConversion();
        }
    }
    public void deleteOrCanselConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap) {
            fileConverterInit.deleteOrCancelConvertFileThread();
        }
    }


    public void restartSingleConversion(FileConverterInit service){

            service.restartConversion();



    }
    public void startSingleConversion(FileConverterInit service){
        service.startConversion();
    }
    public void deleteOrCancelSingleConversion(FileConverterInit service){

                service.deleteOrCancelConvertFileThread();

    }



    public Worker.State getFileThreadState(File file){
        Worker.State state = null;
        for(FileConverterInit service : fileConverterInitMap){
            if(service.getSource() == file)
               state = service.getTask().getState();
        }
        return state;
    }
}

package ir.artlake.lakeconverter;

import javafx.concurrent.Service;
import javafx.concurrent.Worker;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.concurrent.*;

import java.util.concurrent.*;

import java.util.concurrent.*;

public class QConversionManager {

    private final Map<File, FileConverterInit> fileConverterInitMap;

    public QConversionManager(Map<File, FileConverterInit> fileConverterInitMap) {
        this.fileConverterInitMap = fileConverterInitMap;
    }

    public void startConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap.values()) {
            fileConverterInit.startConversion();
        }
    }
    public void resetConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap.values()) {
            fileConverterInit.restartConversion();
        }
    }
    public void deleteOrCanselConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap.values()) {
            fileConverterInit.deleteOrCancelConvertFileThread();
        }
    }


    public void restartSingleConversion(File file){
        FileConverterInit service = fileConverterInitMap.get(file);
        service.restartConversion();
    }
    public void startSingleConversion(File file){
        FileConverterInit service = fileConverterInitMap.get(file);
        if(service.getTask().getState() == Worker.State.READY) {
            service.startConversion();
        }
    }
    public void deleteOrCancelSingleConversion(File file){
        FileConverterInit service = fileConverterInitMap.get(file);

        service.deleteOrCancelConvertFileThread();
    }

    public Worker.State getFileThreadState(File file){
        return fileConverterInitMap.get(file).getTask().getState();
    }
}

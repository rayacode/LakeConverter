package codes.rayacode.lakeconverter.conversion;


import codes.rayacode.lakeconverter.Main;
import codes.rayacode.lakeconverter.controllers.FormatsController;
import codes.rayacode.lakeconverter.controllers.MainController;
import codes.rayacode.lakeconverter.conversion.Formats.Format;
import codes.rayacode.lakeconverter.fileoperations.FileService;
import codes.rayacode.lakeconverter.models.FormatsModel;
import javafx.application.Platform;
import javafx.concurrent.Worker;

import java.io.File;
import java.io.Serial;


import static codes.rayacode.lakeconverter.fileoperations.FileService.fileConverterInitMap;

public class QConversionManager {
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
        Platform.runLater(()->MainController.convertButtonMirror.setDisable(false));
    }


    public void restartSingleConversion(FileConverterInit service){

            service.restartConversion();



    }
    public void changeFormats(Format format){
        for (FileConverterInit fileConverterInit : fileConverterInitMap) {
            if(!fileConverterInit.getService().isSingleFormatChanged()) {
                fileConverterInit.getService().setTargetFormat(format);
            }
        }
    }
    public void changeSingleFormat(FileConverterInit service, Format format){

       service.changeFormat(format);
    }
    public void startSingleConversion(FileConverterInit service){
        service.startConversion();
    }
    public void deleteOrCancelSingleConversion(FileConverterInit service){

                service.deleteOrCancelConvertFileThread();

    }
    public void startMergingConvert(){
        ConversionMergeService conversionMergeService = new ConversionMergeService<>(FileService.mergeList,
                fileConverterInitMap.get(0).getTargetFolder(),
                FileService.semaphore, FileService.format);
        conversionMergeService.start();
    }



    public Worker.State getFileThreadState(File file){
        Worker.State state = null;
        for(FileConverterInit service : fileConverterInitMap){
            if(service.getSource() == file)
               state = service.getService().getState();
        }
        return state;
    }

}

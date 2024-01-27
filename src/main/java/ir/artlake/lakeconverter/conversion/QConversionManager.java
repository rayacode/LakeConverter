package ir.artlake.lakeconverter.conversion;


import ir.artlake.lakeconverter.conversion.Formats.Format;
import javafx.concurrent.Worker;

import java.io.File;
import java.io.Serial;


import static ir.artlake.lakeconverter.fileoperations.FileService.fileConverterInitMap;

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



    public Worker.State getFileThreadState(File file){
        Worker.State state = null;
        for(FileConverterInit service : fileConverterInitMap){
            if(service.getSource() == file)
               state = service.getService().getState();
        }
        return state;
    }

}

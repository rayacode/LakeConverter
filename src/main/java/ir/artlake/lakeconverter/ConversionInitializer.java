package ir.artlake.lakeconverter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class ConversionInitializer {
    private final Semaphore semaphore;

    public ConversionInitializer(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public List<FileConverterInit> initializeConversions(List<File> selectedFiles, File selectedTarget, Consumer<Boolean> onConversionComplete) {
        List<FileConverterInit> fileConverterInitList = new LinkedList<>();
        for (File file : selectedFiles) {
            if(selectedTarget == null){

                selectedTarget = FileUtils.createDirectory(selectedFiles.get(0).getParent() + "\\converted");


                fileConverterInitList.add(new FileConverterInit(onConversionComplete, file.getAbsolutePath(), selectedTarget.getAbsolutePath(), semaphore));
            }else {
                fileConverterInitList.add(new FileConverterInit(onConversionComplete, file.getAbsolutePath(), selectedTarget.getAbsolutePath(), semaphore));
            }

        }
        return fileConverterInitList;
    }
}

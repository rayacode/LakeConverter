package ir.artlake.lakeconverter.conversion;

import ir.artlake.lakeconverter.fileoperations.FileUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConversionInitializer {
    private final Semaphore semaphore;
    private File selectedTarget;

    public ConversionInitializer(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public List<FileConverterInit> initializeConversions(List<File> selectedFiles, File selectedTarget) {
        List<FileConverterInit> fileConverterInitList = new LinkedList<>();
        for (File file : selectedFiles) {
            if(selectedTarget == null || this.selectedTarget != selectedTarget ){
                this.selectedTarget = selectedTarget;
                selectedTarget = FileUtils.createDirectory(file.getParent() + "\\converted");


                fileConverterInitList.add(new FileConverterInit( file.getAbsolutePath(), selectedTarget.getAbsolutePath(), semaphore));
            }else {
                fileConverterInitList.add(new FileConverterInit( file.getAbsolutePath(), selectedTarget.getAbsolutePath(), semaphore));
            }

        }

        return fileConverterInitList;
    }
}

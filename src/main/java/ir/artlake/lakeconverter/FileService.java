package ir.artlake.lakeconverter;

import javafx.concurrent.Worker;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.commons.collections4.map.ListOrderedMap;

import java.io.File;
import java.util.*;
import java.util.concurrent.Semaphore;

public class FileService {
    private static final int MAX_CONCURRENT_CONVERSIONS = 3;
    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_CONVERSIONS);
    private FileSelector fileSelector = new FileSelector();
    private ConversionInitializer conversionInitializer = new ConversionInitializer(semaphore);
    private List<File> selectedFiles = new ArrayList<>();
    private File selectedTarget;
    public static List<FileConverterInit> fileConverterInitMap = new ArrayList<>();
    public static QConversionManager qConversionManager = new QConversionManager();
    private UIUpdater uiUpdater = new UIUpdater();

    public List<File> chooseSourceFiles(Stage stage) {
        List<File> tempSelector = fileSelector.chooseSourceFiles(stage);

        if (!tempSelector.isEmpty()) {
            List<File> tempMutableList = new ArrayList<>(tempSelector);
            selectedFiles = new ArrayList<>();
            selectedFiles.addAll(tempMutableList);
            Set<File> removeDuplicates = Set.copyOf(selectedFiles);
            selectedFiles = new ArrayList<>(removeDuplicates);
        }
        return selectedFiles;
    }

    public File chooseTargetDirectory(Stage stage) {
        selectedTarget = fileSelector.chooseTargetDirectory(stage);
        return selectedTarget;
    }


    public List<FileConverterInit> initializeConversions() {
        List<FileConverterInit> newFileConverterInitList = conversionInitializer.initializeConversions(selectedFiles, selectedTarget);
        fileConverterInitMap.addAll(newFileConverterInitList);
        return newFileConverterInitList;
    }
    public void addButtonListenersToList( Button convertButton){
        for (FileConverterInit fileConverterInit : fileConverterInitMap) {
            fileConverterInit.getTask().stateProperty().addListener((observable, oldState, newState) -> {
                boolean anyRunning = fileConverterInitMap.stream()
                        .anyMatch(init -> init.getTask().getState() == Worker.State.RUNNING);
                boolean anyReady = fileConverterInitMap.stream()
                        .anyMatch(init -> init.getTask().getState() == Worker.State.READY);
                if (anyRunning) {
                    convertButton.setText(ConvertButtonStatuses.CONVERT_ALL);
                } else {
                    convertButton.setText(ConvertButtonStatuses.RESTART_ALL);
                }



            });
        }
    }


}

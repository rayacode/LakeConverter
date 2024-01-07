package ir.artlake.lakeconverter;

import javafx.stage.Stage;

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
    public static Map<File, FileConverterInit> fileConverterInitMap = new HashMap<>();
    public static QConversionManager qConversionManager = new QConversionManager(fileConverterInitMap);
    private UIUpdater uiUpdater = new UIUpdater();

    public List<File> chooseSourceFiles(Stage stage) {
        List<File> tempSelector = fileSelector.chooseSourceFiles(stage);

        if (!tempSelector.isEmpty()) {
            List<File> tempMutableList = new ArrayList<>(tempSelector);
            selectedFiles.addAll(tempMutableList);
            Set<File> removeDuplicates = Set.copyOf(selectedFiles);
            selectedFiles = new ArrayList<>(removeDuplicates);

            List<FileConverterInit> newFileConverterInitList = conversionInitializer.initializeConversions(selectedFiles, selectedTarget);
            for (FileConverterInit init : newFileConverterInitList) {
                fileConverterInitMap.put(init.getSource(), init);
            }
        }
        return selectedFiles;
    }

    public File chooseTargetDirectory(Stage stage) {
        selectedTarget = fileSelector.chooseTargetDirectory(stage);
        return selectedTarget;
    }


    public List<FileConverterInit> initializeConversions() {
        List<FileConverterInit> newFileConverterInitList = conversionInitializer.initializeConversions(selectedFiles, selectedTarget);
        for (FileConverterInit init : newFileConverterInitList) {
            fileConverterInitMap.put(init.getSource(), init);
        }
        return newFileConverterInitList;
    }
}

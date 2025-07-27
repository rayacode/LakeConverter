package codes.rayacode.lakeconverter.fileoperations;

import codes.rayacode.lakeconverter.UIUpdater;
import codes.rayacode.lakeconverter.controllers.ConvertCellWidgetFormatSelector;
import codes.rayacode.lakeconverter.conversion.ConversionInitializer;
import codes.rayacode.lakeconverter.conversion.ConvertButtonStatuses;
import codes.rayacode.lakeconverter.conversion.FileConverterInit;
import codes.rayacode.lakeconverter.conversion.Formats.Format;
import codes.rayacode.lakeconverter.conversion.Formats.MP4;
import codes.rayacode.lakeconverter.conversion.QConversionManager;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import codes.rayacode.lakeconverter.jave.MultimediaObject;

import java.io.File;
import java.util.*;
import java.util.concurrent.Semaphore;

public class FileService {
    private static final int MAX_CONCURRENT_CONVERSIONS = 3;
    public static Semaphore semaphore = new Semaphore(MAX_CONCURRENT_CONVERSIONS);
    private FileSelector fileSelector = new FileSelector();
    private ConversionInitializer conversionInitializer = new ConversionInitializer(semaphore);
    private List<File> selectedFiles = new ArrayList<>();
    public static List<File> everSelectedFiles = new ArrayList<>();
    private File selectedTarget;
    public static List<FileConverterInit> fileConverterInitMap
            = Collections.synchronizedList(new ArrayList<>());

    public static QConversionManager qConversionManager = new QConversionManager();
    private UIUpdater uiUpdater = new UIUpdater();

    public static Format format = new MP4();
    public static Boolean mergeToggle = false;
    public static List<MultimediaObject> mergeList = new ArrayList<>();

    public synchronized  List<File> chooseSourceFiles(Stage stage) {
        List<File> tempSelector = fileSelector.chooseSourceFiles(stage);

        if (tempSelector != null && !tempSelector.isEmpty() && !everSelectedFiles.containsAll(tempSelector) ) {
            System.out.println("im played at fileservice");
            List<File> tempMutableList = new ArrayList<>(tempSelector);
            selectedFiles = new ArrayList<>();
            selectedFiles.addAll(tempMutableList);
            Set<File> removeDuplicates = Set.copyOf(selectedFiles);
            selectedFiles = new ArrayList<>(removeDuplicates);
        }
        return selectedFiles;
    }

    public synchronized  File chooseTargetDirectory(Stage stage) {
        selectedTarget = fileSelector.chooseTargetDirectory(stage);
        return selectedTarget;
    }


    public synchronized  List<FileConverterInit> initializeConversions() {
        /*if(!ConvertCellWidgetFormatSelector.isUsed){
            format.setDefault();
        }*/
        List<FileConverterInit> newFileConverterInitList = conversionInitializer.initializeConversions(selectedFiles, selectedTarget, format);

        fileConverterInitMap.addAll(newFileConverterInitList);
        everSelectedFiles.addAll(selectedFiles);
        selectedFiles = new ArrayList<>();

        return newFileConverterInitList;
    }
    public synchronized  void addButtonListenersToList( Button convertButton){
        for (FileConverterInit fileConverterInit : fileConverterInitMap) {
            Platform.runLater(()->{
            try {
                fileConverterInit.getService().stateProperty().addListener((observable, oldState, newState) -> {
                    boolean anyRunning = fileConverterInitMap.stream()
                            .anyMatch(init -> init.getService().getState() == Worker.State.RUNNING);

                    if (anyRunning) {
                        convertButton.setText(ConvertButtonStatuses.CANCEL_ALL);
                    } else {
                        convertButton.setText(ConvertButtonStatuses.RESTART_ALL);
                    }



                });
            }catch (Exception exception){
                exception.printStackTrace();
            }});
        }
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}

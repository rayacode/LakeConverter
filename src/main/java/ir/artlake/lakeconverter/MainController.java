package ir.artlake.lakeconverter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Semaphore;

public class MainController implements Initializable {


    @FXML
    private Button convertButton;
    @FXML
    private Label messageLabel;
    @FXML
    private Button choosingFiles;
    @FXML
    private Button choosingTarget;
    @FXML
    private ListView convListView;
    @FXML
    private SplitPane splitPane;

    private boolean isSelected;
    private boolean isStarted;
    List<File> selectedFiles;
    private UIUpdater uiUpdater = new UIUpdater();
    private FileService fileService = new FileService();

    @FXML
    protected void onChoosingFileAction() throws Exception {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        selectedFiles = fileService.chooseSourceFiles(stage);
        if(selectedFiles.size() > 0) {
            System.out.println("i'm played");
            List<FileConverterInit> values = new ArrayList<>();
            for (File key : selectedFiles) {
                if (FileService.fileConverterInitMap.containsKey(key)) {
                    values.add(FileService.fileConverterInitMap.get(key));
                }
            }

            uiUpdater.handleFileSelection(selectedFiles, values);
            isSelected = true;
            convertButton.setDisable(false);
        }
        else {
            uiUpdater.showFadeTransition("No file selected!");
        }
    }
    @FXML
    protected void onChoosingTargetAction() {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        fileService.chooseTargetDirectory(stage);
    }

    @FXML
    protected void onConvertAction() {

        if(isSelected){
            FileService.qConversionManager.startConversions();
            for (FileConverterInit fileConverterInit :
                    FileService.qConversionManager.getFileConverterInitMap().values()) {
                fileConverterInit.getTask().stateProperty().addListener((observable, oldState, newState) -> {
                    if (newState == Worker.State.RUNNING) {
                        convertButton.setText("Cancel All");
                    } else if (newState == Worker.State.SUCCEEDED || newState == Worker.State.CANCELLED) {
                        convertButton.setText("Restart All");
                    } /*else if (newState == Worker.State.READY) {
                        convertButton.setText("Start All");
                    }*/
                });
            }
        }


    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        isSelected =false;
        isStarted = false;
        convertButton.setDisable(true);
        uiUpdater.setConvWidgetsContainer(convListView);
        uiUpdater.setMessageLabel(messageLabel);
        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldvalue, newvalue) -> {
            splitPane.setDividerPositions(0.1);
        });
        convertButton.getStyleClass().setAll("btn","btn-danger");
        choosingFiles.getStyleClass().setAll("btn","btn-info");
        choosingTarget.getStyleClass().setAll("btn","btn-info");

    }
}
package ir.artlake.lakeconverter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    private UIUpdater uiUpdater = new UIUpdater();
    private FileService fileService = new FileService();

    @FXML
    protected void onChoosingFileAction() throws Exception {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        List<File> selectedFiles = fileService.chooseSourceFiles(stage);
        if(selectedFiles != null) {
            List<FileConverterInit> newFileConverterInitList = fileService.initializeConversions();
            uiUpdater.handleFileSelection(selectedFiles, newFileConverterInitList);
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
        fileService.startConversions();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
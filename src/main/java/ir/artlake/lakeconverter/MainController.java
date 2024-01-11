package ir.artlake.lakeconverter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;

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

    private UIUpdater uiUpdater = new UIUpdater();

    private FileService fileService = new FileService();


    //private File selectedTarget;
    ////private List<FileConverterInit> fileConverterInitList = new ArrayList<>();



    @FXML
    protected void onChoosingFileAction() throws Exception {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        List<File> selectedFiles = fileService.chooseSourceFiles(stage);
        if(!selectedFiles.isEmpty()) {
            System.out.println("i'm played");
            List<FileConverterInit> newFileConverterInit = fileService.initializeConversions();
            fileService.addButtonListenersToList(convertButton);
            uiUpdater.handleFileSelection(selectedFiles, newFileConverterInit);
            isSelected = true;
            convertButton.setDisable(false);
            convertButton.setText("Convert All");
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
        switch (convertButton.getText()){
            case ConvertButtonStatuses.CONVERT_ALL:
                FileService.qConversionManager.startConversions();
                break;
            case ConvertButtonStatuses.CANCEL_ALL:
                FileService.qConversionManager.deleteOrCanselConversions();
                break;
            case ConvertButtonStatuses.RESTART_ALL:
                FileService.qConversionManager.resetConversions();
                break;

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
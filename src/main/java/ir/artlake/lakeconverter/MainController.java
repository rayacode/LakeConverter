package ir.artlake.lakeconverter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
    private VBox convWidgetsContainer;
    @FXML
    private SplitPane splitPane;

    private static final int MAX_CONCURRENT_CONVERSIONS = 3;
    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_CONVERSIONS);
    private FileSelector fileSelector = new FileSelector();
    private ConversionInitializer conversionInitializer = new ConversionInitializer(semaphore);
    private UIUpdater uiUpdater = new UIUpdater();
    private List<File> selectedFiles = new ArrayList<>();
    private File selectedTarget;
    private List<FileConverterInit> fileConverterInitList = new ArrayList<>();
    private QConversionManager qConversionManager;

    @FXML
    protected void onChoosingFileAction() throws Exception {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        List<File> tempSelector = fileSelector.chooseSourceFiles(stage);

        if(tempSelector != null) {
            List<File> tempMutableList = new ArrayList<>(tempSelector);
            selectedFiles.addAll(tempMutableList);
            Set<File> removeDuplicates = Set.copyOf(selectedFiles);
            selectedFiles = new ArrayList<>(removeDuplicates);

            List<FileConverterInit> newFileConverterInitList = conversionInitializer.initializeConversions(selectedFiles, selectedTarget, this::onConversionComplete);
            fileConverterInitList.addAll(newFileConverterInitList);
            uiUpdater.handleFileSelection(selectedFiles, newFileConverterInitList);
        }
        else {
            uiUpdater.showFadeTransition("No file selected!");
        }
    }

    @FXML
    protected void onChoosingTargetAction() {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        selectedTarget = fileSelector.chooseTargetDirectory(stage);
    }

    @FXML
    protected void onConvertAction() {
        qConversionManager = new QConversionManager(fileConverterInitList);
        qConversionManager.startConversions();
    }

    private void onConversionComplete(boolean success) {
        if (success) {
            uiUpdater.showFadeTransition("The file has been successfully converted!");
        } else {
            uiUpdater.showFadeTransition("An error occurred during conversion.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        uiUpdater.setConvWidgetsContainer(convWidgetsContainer);
        uiUpdater.setMessageLabel(messageLabel);
        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldvalue, newvalue) -> {
            splitPane.setDividerPositions(0.1);
        });
        convertButton.getStyleClass().setAll("btn","btn-danger");
        choosingFiles.getStyleClass().setAll("btn","btn-info");
        choosingTarget.getStyleClass().setAll("btn","btn-info");

    }
}

package ir.artlake.lakeconverter;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ProgressBar progBar;
    @FXML
    private Button convertBtt;
    @FXML
    private Label messageLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private Button choosingFiles;
    @FXML
    private Button choosingTarget;
    @FXML
    private VBox convWidgetsContainer;
    List<File> selectedFiles;
    File selectedTarget;
    ThumbnailGenerator thumbnailGenerator;
    List<FileConverterInit> fileConverterInitList;
    QConversionManager qConversionManager;
    public MainController(){}
    @FXML
    protected void onChoosingFileAction() throws Exception {
        FileChooser sourceChooser = new FileChooser();
        // Get the Stage from any control (like a Button)
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        selectedFiles = sourceChooser.showOpenMultipleDialog(stage);
        System.out.println(selectedFiles.get(0).exists());
        fileConverterInitList = new LinkedList<>();
        for (File file : selectedFiles) {
            fileConverterInitList.add(new FileConverterInit(this::onConversionComplete, file.getAbsolutePath(), selectedTarget.getAbsolutePath()));
        }
        System.out.println(fileConverterInitList);
        handleFileSelection(selectedFiles, fileConverterInitList);



    }
    public void handleFileSelection( List<File> sourceFiles, List<FileConverterInit> fileConverterInit) throws Exception {
        for (int i = 0; i < sourceFiles.size() && i < fileConverterInit.size(); i++) {

            ConvertWidgetBox fileBox = new ConvertWidgetBox(fileConverterInit.get(i), sourceFiles.get(i));
            convWidgetsContainer.getChildren().add(fileBox);
        }
    }
    @FXML
    protected void onChoosingTargetAction(){
        DirectoryChooser targetChooser = new DirectoryChooser();
        // Get the Stage from any control (like a Button)
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        selectedTarget = targetChooser.showDialog(stage);
        if (selectedTarget != null) {
            System.out.println(selectedTarget.getPath());
        }
    }
    @FXML
    protected void onHelloButtonClick() throws Exception {
        qConversionManager
                = new QConversionManager(fileConverterInitList);

        qConversionManager.startConversions();

        /*progBar.progressProperty().bind(fileConverterInit.getTask().progressProperty());
        fileConverterInit.getTask().progressProperty().addListener((obs, oldProgress, newProgress) -> {
            Platform.runLater(() -> {
                progressLabel.setText(String.format("%.0f%%", newProgress.doubleValue() * 100));
            });
        });*/

    }


    private void onConversionComplete(boolean success) {
        if (success) {
            // Show a success message
            showFadeTransition("The file has been successfully converted!");
        } else {
            // Show an error message
            showFadeTransition("An error occurred during conversion.");
        }
    }

    private void showFadeTransition(String message) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        messageLabel.setText(message);
        fadeIn.play();

        fadeIn.setOnFinished(event -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished(event -> {
            messageLabel.setText("");
            progBar.progressProperty().unbind();
            progBar.setProgress(0);
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thumbnailGenerator = new ThumbnailGenerator();

        convertBtt.getStyleClass().setAll("btn","btn-danger");
        progressLabel.getStyleClass().setAll("lbl","lbl-primary");
        choosingFiles.getStyleClass().setAll("btn","btn-info");
        choosingTarget.getStyleClass().setAll("btn","btn-info");
    }
}
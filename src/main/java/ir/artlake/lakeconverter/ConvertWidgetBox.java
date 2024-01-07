package ir.artlake.lakeconverter;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ConvertWidgetBox extends HBox implements Initializable {
    @FXML
    private Label fileNameLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;
    @FXML
    private Label convertStatusLabel;
    @FXML
    private ImageView thumbnailView;
    @FXML
    private Button convertCRButton;

    @FXML
    private Label stateLabel;
    private File file;

    private FileConverterInit fileConverterInit;
    ThumbnailGenerator thumbnailGenerator;

    public ConvertWidgetBox(FileConverterInit fileConverterInit, File file) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConvertWidgetBox.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        this.getChildren().add(root);

        fileNameLabel.setText(file.getName().substring(0, file.getName().lastIndexOf('.')));
        thumbnailGenerator = new ThumbnailGenerator();
        String thumbnailPath = thumbnailGenerator.generateThumbnail(file.getPath());
        //String thumbnailUrl = thumbnailPath.toUri().toURL().toString();
        System.out.println(thumbnailPath);
        thumbnailView.setImage(new Image(thumbnailPath));
        File tempDirectoryPath = new File(System.getProperty("java.io.tmpdir") + "\\lakeConverter\\thumbnails");
        deleteDirectory(tempDirectoryPath);
        this.file = file;
        this.fileConverterInit = fileConverterInit;
        thumbnailView.setFitHeight(100);
        thumbnailView.setFitWidth(100);
        thumbnailView.setPreserveRatio(true);
        thumbnailView.setSmooth(true);
        thumbnailView.setCache(true);
        progressBar.progressProperty().bind(fileConverterInit.getTask().progressProperty());

        fileConverterInit.getTask().progressProperty().addListener((obs, oldProgress, newProgress) -> {
            Platform.runLater(() -> {
                progressLabel.setText(String.format("%.0f%%", newProgress.doubleValue() * 100));


                System.out.println("Progress property for file " + file.getName() + " changed, new progress: " + newProgress);
            });
        });
        fileConverterInit.getTask().stateProperty().addListener((observable, oldState, newState) -> {
            Platform.runLater(() -> {
                switch (newState) {
                    case READY:
                        convertStatusLabel.setText("Ready");
                        convertCRButton.setText("Convert");
                        break;
                    case SUCCEEDED:

                        convertCRButton.setText("Restart");
                        break;
                    case FAILED:
                    case CANCELLED:
                        convertStatusLabel.setText("Cancelled");
                        convertCRButton.setText("Retry");
                        break;
                    default:
                        convertCRButton.setText("Cancel");
                        break;
                }
            });
        });

    }

    public void deleteDirectory(File file) {
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                deleteDirectory(subFile);
            }
            subFile.delete();
        }
    }
    @FXML
    protected void onConvertAction(){
        switch (convertCRButton.getText()){
            case "Convert":
                FileService.qConversionManager.startSingleConversion(fileConverterInit);
                break;
            case "Retry":
            case "Restart":
                FileService.qConversionManager.restartSingleConversion(fileConverterInit);
                break;
            case "Cancel":
                FileService.qConversionManager.deleteOrCancelSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getTask().getState());
                break;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

package ir.artlake.lakeconverter;

import ir.artlake.lakeconverter.FileService;
import ir.artlake.lakeconverter.ThumbnailGenerator;
import ir.artlake.lakeconverter.conversion.FileConverterInit;
import javafx.application.Platform;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConvertWidgetBox extends HBox implements Initializable {
    @FXML
    private Button removeButton;
    @FXML
    private Label formatLabel;
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
    private UIUpdater uiUpdater;

    private FileConverterInit fileConverterInit;
    ThumbnailGenerator thumbnailGenerator;
    public ConvertWidgetBox(){
        super();
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        getClass().
                                getResource("ConvertWidgetBox/ConvertWidgetBox.fxml"));

        ;
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);


        try {
            fxmlLoader.load();
            uiUpdater = new UIUpdater();

        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }
    public void setConvertWidgetBox(FileConverterInit fileConverterInit, File file) throws Exception {

        String fileNameBase = file.getName().substring(0, file.getName().lastIndexOf('.'));
        String fileName = String.format("%-18s", fileNameBase).replace(' ', ' ');
        if (fileName.length() > 18) {
            fileName = fileName.substring(0, 15) + "...";
        }
        fileNameLabel.setText(fileName);

        String format = file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length());
        formatLabel.setText(format);

        thumbnailGenerator = new ThumbnailGenerator();
        String thumbnailPath = thumbnailGenerator.generateThumbnail(file.getPath());
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
                //System.out.println("Progress property for file " + file.getName() + " changed, new progress: " + newProgress);
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
                        convertStatusLabel.setText("SUCCEEDED");
                        convertCRButton.setText("Restart");
                        break;
                    case RUNNING:
                        convertStatusLabel.setText("RUNNING");
                        convertCRButton.setText("Cancel");
                        break;
                    case FAILED:
                        convertStatusLabel.setText("FAILED");
                        convertCRButton.setText("Retry");
                        break;
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
    protected void onConvertAction() {
        switch (convertCRButton.getText()) {
            case "Convert":
                FileService.qConversionManager.startSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getTask().getState());
                break;
            case "Retry":
            case "Restart":
                FileService.qConversionManager.restartSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getTask().getState());
                break;
            case "Cancel":
                FileService.qConversionManager.deleteOrCancelSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getTask().getState());
                break;
        }
    }

    @FXML
    protected synchronized void onRemoveButtonAction(){
        FileService.fileConverterInitMap.remove(this.fileConverterInit);
        uiUpdater.removeFromList(this, this.file);
        fileConverterInit = null;


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image importImageI = new Image(String.valueOf(Main.class.getResource("icons/trash-off.png")));

        // Create an ImageView
        ImageView importImage = new ImageView(importImageI);
        importImage.setFitWidth(15);
        importImage.setFitHeight(15);
        // Create a button and set the graphic
        removeButton.setGraphic(importImage);
    }
}

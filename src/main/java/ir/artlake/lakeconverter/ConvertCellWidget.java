package ir.artlake.lakeconverter;

import ir.artlake.lakeconverter.controllers.ConvertCellWidgetFormatSelector;
import ir.artlake.lakeconverter.conversion.Formats.Format;
import ir.artlake.lakeconverter.fileoperations.FileService;
import ir.artlake.lakeconverter.fileoperations.ThumbnailGenerator;
import ir.artlake.lakeconverter.conversion.FileConverterInit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConvertCellWidget extends HBox implements Initializable {
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


    @FXML
    private Label fileSizeLabel;

    @FXML
    private Label resolutionLabel;

    @FXML
    private Label vidLengthLabel;

    @FXML
    private Label targetFormatLabel;

    @FXML
    private Label targetFileSizeLabel;

    @FXML
    private Label targetResolutionLabel;

    @FXML
    private Label targetVidLengthLabel;
    @FXML
    private Button convertTButton;
    @FXML
    private Button convertToButton;

    private File file;
    private UIUpdater uiUpdater;
    private Label fileCounter;

    private FileConverterInit fileConverterInit;
    private boolean isSingleFormatSelected;
    ThumbnailGenerator thumbnailGenerator;
    private FXMLLoader formatsControllerLoader;
    private ConvertCellWidgetFormatSelector convertCellWidgetFormatSelector;
    private Format format;
    public ConvertCellWidget(){
        super();
        this.isSingleFormatSelected = false;
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        getClass().
                                getResource("ConvertWidgetBox/ConvertCellWidget.fxml"));


        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);



        try {
            fxmlLoader.load();
            uiUpdater = new UIUpdater();

        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }
    public void setConvertCell(FileConverterInit fileConverterInit, File file) throws Exception {

        String fileNameBase = file.getName().substring(0, file.getName().lastIndexOf('.'));
        String fileName = String.format("%-18s", fileNameBase).replace(' ', ' ');
        if (fileName.length() > 18) {
            fileName = fileName.substring(0, 15) + "...";
        }
        fileNameLabel.setText(fileNameBase);

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
        progressBar.progressProperty().bind(fileConverterInit.getService().progressProperty());
        fileConverterInit.getService().progressProperty().addListener((obs, oldProgress, newProgress) -> {
            Platform.runLater(() -> {
                progressLabel.setText(String.format("%.0f%%", newProgress.doubleValue() * 100));
                //System.out.println("Progress property for file " + file.getName() + " changed, new progress: " + newProgress);
            });
        });
        fileConverterInit.getService().stateProperty().addListener((observable, oldState, newState) -> {
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
        MultimediaObject mo = new MultimediaObject(file);
        MultimediaInfo mi= mo.getInfo();
        AudioInfo ai= mi.getAudio();
        VideoInfo vi= mi.getVideo();
        formatLabel.setText(mi.getFormat());
        float size = ((float) file.length()/1024/1024);
        String sizeStr = String.format("%.2f", size); // format to 2 decimal places
        fileSizeLabel.setText("Size: "+sizeStr + " MB");

        resolutionLabel.setText(vi.getSize().getWidth() + " * " + vi.getSize().getWidth());
        vidLengthLabel.setText(String.valueOf
                (String.format("%.2f", (double) mi.getDuration()/60000)) + " Min");
    }

    public void deleteDirectory(File file) {
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                deleteDirectory(subFile);
            }
            subFile.delete();

        }
    }
    Parent root = null;
    @FXML
    protected void onConvertToButtonAction(){
        isSingleFormatSelected = true;
        formatsControllerLoader =
                new FXMLLoader(
                        Main.class.getResource("formats/ConvertCellWidgetFormatSelector.fxml"));


        convertCellWidgetFormatSelector = new ConvertCellWidgetFormatSelector();
                formatsControllerLoader.setController(convertCellWidgetFormatSelector);

        try {
            root = formatsControllerLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //formatsControllerLoader.setRoot(root);
        // Create a new stage
        Stage formatChoosStage = new Stage();
        formatChoosStage.initModality(Modality.APPLICATION_MODAL);
        formatChoosStage.setScene(new Scene(root));
        formatChoosStage.setResizable(false);
        // Add an event filter to hide the stage when user clicks outside
        ScreenUtils.lockEdges(formatChoosStage);
        formatChoosStage.show();

    }
    public Button getConvertToButton(){
        return convertToButton;
    }
    @FXML
    protected void onConvertAction() {
        switch (convertCRButton.getText()) {
            case "Convert":
                FileService.qConversionManager.startSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getService().getState());
                break;
            case "Retry":
            case "Restart":
                FileService.qConversionManager.restartSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getService().getState());
                break;
            case "Cancel":
                FileService.qConversionManager.deleteOrCancelSingleConversion(fileConverterInit);
                System.out.println(fileConverterInit.getService().getState());
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

    public boolean isSingleFormatSelected() {
        return isSingleFormatSelected;
    }

    public void setSingleFormatSelected(boolean singleFormatSelected) {
        isSingleFormatSelected = singleFormatSelected;
    }

    public FileConverterInit getFileConverterInit() {

        return fileConverterInit;
    }
    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
}

package ir.artlake.lakeconverter;


import ir.artlake.lakeconverter.conversion.ConvertButtonStatuses;
import ir.artlake.lakeconverter.conversion.FileConverterInit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoInfo;

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
    @FXML
    private ToolBar convertBar;
    @FXML
    private ToggleButton mergeToggle;
    private boolean isSelected;
    private boolean isStarted;

    private UIUpdater uiUpdater = new UIUpdater();

    private FileService fileService = new FileService();

    @FXML
    protected void onChoosingFileAction() throws Exception {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        List<File> selectedFiles = fileService.chooseSourceFiles(stage);
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            System.out.println("I'm played");
            List<FileConverterInit> newFileConverterInit = fileService.initializeConversions();
            fileService.addButtonListenersToList(convertButton);
            uiUpdater.handleFileSelection(selectedFiles, newFileConverterInit);
            isSelected = true;
            convertButton.setDisable(false);
            convertButton.setText("Convert All");
            for(FileConverterInit fileConverterInit : newFileConverterInit){
                System.out.println("testVideoInfo1");

                File source = new File(fileConverterInit.getSource(),"");
                MultimediaObject mo = new MultimediaObject(source);
                MultimediaInfo mi= mo.getInfo();
                AudioInfo ai= mi.getAudio();
                VideoInfo vi= mi.getVideo();
                System.out.println(ai.toString());
                System.out.println(vi.toString());
            }
        } else {
            uiUpdater.showFadeTransition("No file selected!");
        }
    }

    @FXML
    protected void onChoosingTargetAction() {
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        fileService.chooseTargetDirectory(stage);

    }
    @FXML
    protected void onMergeToggleAction(){
        if(mergeToggle.isSelected()){
            Image toggleImage =
                    new Image(String.valueOf(Main.class.getResource("icons/toggle-right.png")));

            // Create an ImageView
            ImageView toggleImageView = new ImageView(toggleImage);
            toggleImageView.setFitWidth(30);
            toggleImageView.setFitHeight(30);
            // Create a button and set the graphic
            mergeToggle.setGraphic(toggleImageView);
        }else {
            Image toggleImage =
                    new Image(String.valueOf(Main.class.getResource("icons/toggle-left.png")));

            // Create an ImageView
            ImageView toggleImageView = new ImageView(toggleImage);
            toggleImageView.setFitWidth(30);
            toggleImageView.setFitHeight(30);
            // Create a button and set the graphic
            mergeToggle.setGraphic(toggleImageView);
        }

    }
    @FXML
    protected void onConvertAction() {
        switch (convertButton.getText()) {
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
        isSelected = false;
        isStarted = false;
        convertButton.setDisable(true);
        uiUpdater.setConvWidgetsContainer(convListView);
        uiUpdater.setMessageLabel(messageLabel);

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldvalue, newvalue) -> {
            splitPane.setDividerPositions(0.1);
        });


        Image importImageI = new Image(String.valueOf(Main.class.getResource("icons/file-import.png")));

        // Create an ImageView
        ImageView importImage = new ImageView(importImageI);
        importImage.setFitWidth(30);
        importImage.setFitHeight(30);
        // Create a button and set the graphic
        choosingFiles.setGraphic(importImage);
        Image exportImageI = new Image(String.valueOf(Main.class.getResource("icons/folder-symlink.png")));

        // Create an ImageView
        ImageView exportImage = new ImageView(exportImageI);
        exportImage.setFitWidth(30);
        exportImage.setFitHeight(30);
        // Create a button and set the graphic
        choosingTarget.setGraphic(exportImage);

        Image toggleImage =
                new Image(String.valueOf(Main.class.getResource("icons/toggle-left.png")));

        // Create an ImageView
        ImageView toggleImageView = new ImageView(toggleImage);
        toggleImageView.setFitWidth(30);
        toggleImageView.setFitHeight(30);
        // Create a button and set the graphic
        mergeToggle.setGraphic(toggleImageView);


        /*var denseToggle = new ToggleSwitch("Dense");
        denseToggle.selectedProperty().addListener(
                (obs, old, val) -> Styles.toggleStyleClass(convListView, Styles.DENSE)
        );*/
        /*convertButton.getStyleClass().setAll("btn", "btn-info");
        choosingFiles.getStyleClass().setAll("btn", "btn-primary");
        choosingTarget.getStyleClass().setAll("btn", "btn-primary");*/
    }
}

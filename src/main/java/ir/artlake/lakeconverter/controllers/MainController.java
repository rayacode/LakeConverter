package ir.artlake.lakeconverter.controllers;


import ir.artlake.lakeconverter.ConvertCellWidget;
import ir.artlake.lakeconverter.Main;
import ir.artlake.lakeconverter.ScreenUtils;
import ir.artlake.lakeconverter.UIUpdater;
import ir.artlake.lakeconverter.conversion.ConvertButtonStatuses;
import ir.artlake.lakeconverter.conversion.FileConverterInit;
import ir.artlake.lakeconverter.conversion.Formats.MP4;
import ir.artlake.lakeconverter.fileoperations.FileService;
import ir.artlake.lakeconverter.fileoperations.concurency.AddFiles;
import ir.artlake.lakeconverter.models.FormatsModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ir.artlake.lakeconverter.jave.MultimediaObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private Button formatsSettings;
    @FXML
    private ProgressBar fileAddProgress;
    @FXML
    private Button deleteAllButton;
    @FXML
    private Label filesCounter;
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
    @FXML
    private Button convertToButton;
    private boolean isSelected;
    private boolean isStarted;

    private UIUpdater uiUpdater = new UIUpdater();

    private FileService fileService = new FileService();

    @FXML
    protected void onChoosingFileAction() throws Exception {
        convListView.refresh();
        Stage stage = (Stage) choosingFiles.getScene().getWindow();
        List<File> selectedFiles = fileService.chooseSourceFiles(stage);
        AddFiles addFiles = new AddFiles(selectedFiles, fileService,
                convertButton,mergeToggle, isSelected,
                uiUpdater, fileService.getSemaphore());
        fileAddProgress.progressProperty().bind(addFiles.progressProperty());
        addFiles.start();
        System.out.println(addFiles.getState());

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
    protected void onDeleteAllButtonAction(){
        uiUpdater.removeAllList();
        filesCounter.setText("");
        convertButton.setDisable(true);
        convertButton.setVisible(false);
        deleteAllButton.setVisible(false);
        deleteAllButton.setDisable(true);
    }
    public static Button convertButtonMirror;
    @FXML
    protected void onConvertAction() {
        if(!FileService.mergeToggle){
            switch (convertButton.getText()) {
                case ConvertButtonStatuses.CONVERT_ALL:
                    FileService.qConversionManager.startConversions();
                    break;
                case ConvertButtonStatuses.CANCEL_ALL:
                    Platform.runLater(() -> convertButton.setDisable(true));
                    FileService.qConversionManager.deleteOrCanselConversions();
                    break;
                case ConvertButtonStatuses.RESTART_ALL:
                    FileService.qConversionManager.resetConversions();
                    break;
            }
        }else {
            switch (convertButton.getText()) {
                case ConvertButtonStatuses.CONVERT_ALL:
                    FileService.qConversionManager.startMergingConvert();
                    break;
                case ConvertButtonStatuses.CANCEL_ALL:
                    FileService.qConversionManager.deleteOrCanselConversions();
                    break;
                case ConvertButtonStatuses.RESTART_ALL:
                    FileService.qConversionManager.resetConversions();
                    break;
            }
        }
    }

    @FXML
    protected void onConvertToAction(){

        formatChoosStage.showAndWait();
    }
    @FXML
    protected void onFormatsSettings(){
        formatsSettingsFxmlLoader =
                new FXMLLoader(Main.class.getResource("formatSettings/formatSettings.fxml"));
        Parent rootFormatSettings = null;
        try {
            rootFormatSettings = formatsSettingsFxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        formatsSettingsStage = new Stage();
        formatsSettingsStage.initModality(Modality.APPLICATION_MODAL);
        formatsSettingsStage.setScene(new Scene(rootFormatSettings));
        formatsSettingsStage.setResizable(false);
        formatsSettingsStage.setOnCloseRequest(event -> {

        });
        ScreenUtils.lockEdges(formatsSettingsStage);
        formatsSettingsStage.showAndWait();
    }
    @FXML
    protected void mergeOnSwipeRight(){

    }

    @FXML
    protected void mergeOnSwipeLeft(){

    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Stage getFormatChoosStage(){
        return formatChoosStage;
    }

    public Button getConvertToButton(){
       return convertToButton;
    }
    public ListView<ConvertCellWidget> getConvertCellWidgetListView(){
        return convListView;
    }
    public static FXMLLoader formatsFxmlLoader;
    public static FXMLLoader formatsSettingsFxmlLoader;
    Stage formatChoosStage;
    Stage formatsSettingsStage;
    public static Button convertToMirror;
    public static Button formatSettingsMirror;
    public static Label allConvertToLabelMirror;

    @FXML
    private Label allConvertToLabel;
    @FXML
    private Label mergeAllLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        convertButtonMirror = convertButton;
            convertToMirror=convertToButton;
            formatSettingsMirror = formatsSettings;
            allConvertToLabelMirror = allConvertToLabel;
            allConvertToLabel.setVisible(false);
            convertToButton.setVisible(false);
            formatsSettings.setVisible(false);
            FormatsSettingsController.customResolution.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    convertToButton.setText(newValue);
                }
            });
            convertToButton.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    Platform.runLater(()->FormatsSettingsController.resolutionComboMirror.setValue(FileService.format.getVideoAttributes().getSize().get()));
                    }
            });
            mergeToggle.setDisable(true);
            mergeToggle.setVisible(false);
            mergeAllLabel.setVisible(false);
            mergeToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                for(FileConverterInit fileConverterInit : FileService.fileConverterInitMap){
                    FileService.mergeList.add(
                            new MultimediaObject(fileConverterInit.getSource()));
                }
                for(ConvertCellWidget cellWidget : UIUpdater.items){
                    cellWidget.getConvertToButton().setDisable(true);
                    cellWidget.getConvertCRButton().setDisable(true);
                    cellWidget.getRemoveButton().setDisable(true);
                }

                FileService.mergeToggle = true;
            } else {
                FileService.mergeList.clear();
                for(ConvertCellWidget cellWidget : UIUpdater.items){
                    cellWidget.getConvertToButton().setDisable(false);
                    cellWidget.getConvertCRButton().setDisable(false);
                    cellWidget.getRemoveButton().setDisable(false);
                }
                FileService.mergeToggle = false;
            }
        });
        try {
            // Load the FXML file
            formatsFxmlLoader =
                    new FXMLLoader(Main.class.getResource("formats/formats.fxml"));

            Parent rootFormatChoose = formatsFxmlLoader.load();

            // Create a new stage
            formatChoosStage = new Stage();
            formatChoosStage.initModality(Modality.APPLICATION_MODAL);
            formatChoosStage.setScene(new Scene(rootFormatChoose));
            formatChoosStage.setResizable(false);


            // Add an event filter to hide the stage when user clicks outside
            ScreenUtils.lockEdges(formatChoosStage);



            // Show the stage and wait

        } catch (Exception e) {
            e.printStackTrace();
        }
        isSelected = false;
        isStarted = false;
        convertButton.setDisable(true);
        convertButton.setVisible(false);
        deleteAllButton.setVisible(false);
        deleteAllButton.setDisable(true);
        convertButton.textProperty().addListener((observable, oldValue, newValue) -> {
            if("Cancel All".equals(newValue)){
                deleteAllButton.setDisable(true);
            }else{
                deleteAllButton.setDisable(false);
            }
        });
        convertButton.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    deleteAllButton.setDisable(false);
                    deleteAllButton.setVisible(true);
                }
            }
        });
        convListView.setCellFactory(lv -> {
            ListCell<ConvertCellWidget> cell = new ListCell<ConvertCellWidget>() {
                @Override
                protected void updateItem(ConvertCellWidget item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(item); // set the HBox as the graphic of the cell
                    }
                }
            };
            cell.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
                convListView.getSelectionModel().select(cell.getIndex());
            });
            return cell;
        });

        UIUpdater.items.addListener(new ListChangeListener<ConvertCellWidget>() {
            @Override
            public void onChanged(Change<? extends ConvertCellWidget> c) {
                if(UIUpdater.items.size() != 0){
                    filesCounter.setText(UIUpdater.items.size() + " Files");
                }else {
                    filesCounter.setText("");
                    convertButton.setDisable(true);
                    convertButton.setVisible(false);
                    deleteAllButton.setVisible(false);
                    deleteAllButton.setDisable(true);
                }

            }
        });
        uiUpdater.setConvWidgetsContainer(convListView, fileAddProgress);
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
        Image formatSettingsImageI = new Image(String.valueOf(Main.class.getResource("icons/settings.png")));

        // Create an ImageView
        ImageView formatSettingsImage = new ImageView(formatSettingsImageI);
        formatSettingsImage.setFitWidth(30);
        formatSettingsImage.setFitHeight(25);
        // Create a button and set the graphic
        formatsSettings.setGraphic(formatSettingsImage);

        Image toggleImage =
                new Image(String.valueOf(Main.class.getResource("icons/toggle-left.png")));

        // Create an ImageView
        ImageView toggleImageView = new ImageView(toggleImage);
        toggleImageView.setFitWidth(30);
        toggleImageView.setFitHeight(30);
        // Create a button and set the graphic
        mergeToggle.setGraphic(toggleImageView);
        fileAddProgress.setDisable(true);
        fileAddProgress.setVisible(false);

        /*var denseToggle = new ToggleSwitch("Dense");
        denseToggle.selectedProperty().addListener(
                (obs, old, val) -> Styles.toggleStyleClass(convListView, Styles.DENSE)
        );*/
        /*convertButton.getStyleClass().setAll("btn", "btn-info");
        choosingFiles.getStyleClass().setAll("btn", "btn-primary");
        choosingTarget.getStyleClass().setAll("btn", "btn-primary");*/
    }
}

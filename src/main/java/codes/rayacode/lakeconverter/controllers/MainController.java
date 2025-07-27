/*  LakeConverter: A java Gui wrapper with jave for ffmpeg
 *  Copyright (C) 2023 Mohammad Ali Solhjoo mohammadalisolhjoo@live.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package codes.rayacode.lakeconverter.controllers;


import codes.rayacode.lakeconverter.ConvertCellWidget;
import codes.rayacode.lakeconverter.Main;
import codes.rayacode.lakeconverter.ScreenUtils;
import codes.rayacode.lakeconverter.UIUpdater;
import codes.rayacode.lakeconverter.conversion.ConvertButtonStatuses;
import codes.rayacode.lakeconverter.conversion.FileConverterInit;
import codes.rayacode.lakeconverter.conversion.Formats.MP4;
import codes.rayacode.lakeconverter.fileoperations.FileService;
import codes.rayacode.lakeconverter.fileoperations.concurency.AddFiles;
import codes.rayacode.lakeconverter.models.FormatsModel;
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
import codes.rayacode.lakeconverter.jave.MultimediaObject;

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

            
            ImageView toggleImageView = new ImageView(toggleImage);
            toggleImageView.setFitWidth(30);
            toggleImageView.setFitHeight(30);
            
            mergeToggle.setGraphic(toggleImageView);
        }else {
            Image toggleImage =
                    new Image(String.valueOf(Main.class.getResource("icons/toggle-left.png")));

            
            ImageView toggleImageView = new ImageView(toggleImage);
            toggleImageView.setFitWidth(30);
            toggleImageView.setFitHeight(30);
            
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
            
            formatsFxmlLoader =
                    new FXMLLoader(Main.class.getResource("formats/formats.fxml"));

            Parent rootFormatChoose = formatsFxmlLoader.load();

            
            formatChoosStage = new Stage();
            formatChoosStage.initModality(Modality.APPLICATION_MODAL);
            formatChoosStage.setScene(new Scene(rootFormatChoose));
            formatChoosStage.setResizable(false);


            
            ScreenUtils.lockEdges(formatChoosStage);



            

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
                        setGraphic(item); 
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

        
        ImageView importImage = new ImageView(importImageI);
        importImage.setFitWidth(30);
        importImage.setFitHeight(30);
        
        choosingFiles.setGraphic(importImage);
        Image exportImageI = new Image(String.valueOf(Main.class.getResource("icons/folder-symlink.png")));

        
        ImageView exportImage = new ImageView(exportImageI);
        exportImage.setFitWidth(30);
        exportImage.setFitHeight(30);
        
        choosingTarget.setGraphic(exportImage);
        Image formatSettingsImageI = new Image(String.valueOf(Main.class.getResource("icons/settings.png")));

        
        ImageView formatSettingsImage = new ImageView(formatSettingsImageI);
        formatSettingsImage.setFitWidth(30);
        formatSettingsImage.setFitHeight(25);
        
        formatsSettings.setGraphic(formatSettingsImage);

        Image toggleImage =
                new Image(String.valueOf(Main.class.getResource("icons/toggle-left.png")));

        
        ImageView toggleImageView = new ImageView(toggleImage);
        toggleImageView.setFitWidth(30);
        toggleImageView.setFitHeight(30);
        
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
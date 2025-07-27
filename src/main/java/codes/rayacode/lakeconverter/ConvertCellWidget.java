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

package codes.rayacode.lakeconverter;

import codes.rayacode.lakeconverter.controllers.ConvertCellWidgetFormatSelector;
import codes.rayacode.lakeconverter.controllers.FormatsSettingsController;
import codes.rayacode.lakeconverter.controllers.MainController;
import codes.rayacode.lakeconverter.conversion.Formats.Format;
import codes.rayacode.lakeconverter.conversion.Formats.MP4;
import codes.rayacode.lakeconverter.fileoperations.FileService;
import codes.rayacode.lakeconverter.fileoperations.ThumbnailGenerator;
import codes.rayacode.lakeconverter.conversion.FileConverterInit;
import codes.rayacode.lakeconverter.models.FormatsModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import codes.rayacode.lakeconverter.jave.MultimediaObject;
import codes.rayacode.lakeconverter.jave.info.AudioInfo;
import codes.rayacode.lakeconverter.jave.info.MultimediaInfo;
import codes.rayacode.lakeconverter.jave.info.VideoInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
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

        
        FormatsSettingsController.customResolution.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!fileConverterInit.getService().isSingleFormatChanged()){
                    convertToButton.setText(newValue.substring(7));
                }
            }
        });
        if(FormatsSettingsController.isCustomResolutionUsed){
            if(!fileConverterInit.getService().isSingleFormatChanged()) {
                convertToButton.setText(FormatsSettingsController.customResolution.getValue().substring(7));
            }
        }

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
                
            });
        });
        fileConverterInit.getService().stateProperty().addListener((observable, oldState, newState) -> {
            Platform.runLater(() -> {
                if (newState.equals(Worker.State.READY)) {
                    convertStatusLabel.setText("Ready");
                    convertCRButton.setText("Convert");
                } else if (newState.equals(Worker.State.SUCCEEDED)) {
                    convertStatusLabel.setText("SUCCEEDED");
                    convertCRButton.setText("Restart");
                } else if (newState.equals(Worker.State.RUNNING)) {
                    convertStatusLabel.setText("RUNNING");
                    convertCRButton.setText("Cancel");
                } else if (newState.equals(Worker.State.FAILED)) {
                    convertStatusLabel.setText("FAILED");
                    convertCRButton.setText("Retry");
                } else if (newState.equals(Worker.State.CANCELLED)) {
                    convertStatusLabel.setText("Cancelled");
                    convertCRButton.setText("Retry");
                } else {
                    convertCRButton.setText("Cancel");
                }
            });
        });
        MultimediaObject mo = new MultimediaObject(file);
        MultimediaInfo mi= mo.getInfo();
        AudioInfo ai= mi.getAudio();
        VideoInfo vi= mi.getVideo();
        formatLabel.setText(mi.getFormat());
        float size = ((float) file.length()/1024/1024);
        String sizeStr = String.format("%.2f", size); 
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

    public Button getConvertCRButton() {
        return convertCRButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    Parent root = null;
    @FXML
    protected void onConvertToButtonAction(){


        showSplitPane();

        /*isSingleFormatSelected = true;
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
        
        
        Stage formatChoosStage = new Stage();
        formatChoosStage.initModality(Modality.APPLICATION_MODAL);
        formatChoosStage.setScene(new Scene(root));
        formatChoosStage.setResizable(false);
        
        ScreenUtils.lockEdges(formatChoosStage);
        formatChoosStage.show();*/

    }
    private SplitPane motherSplitPane;
    private HBox formatCategBar;
    private Button videoFormatsButton;
    private Button audioFormatsButton;
    private HBox searchBar;
    private TextField formatSearchTField;
    private SplitPane childSplitPane;
    private ListView<String> formatContainers;
    private ListView<String> formatAtrrs;
    private Scene scene;
    private Stage stage;



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
    private Format format;
    private void showSplitPane() {
        stage = new Stage();

        motherSplitPane = new SplitPane();
        motherSplitPane.setId("motherSplitPane");
        motherSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        motherSplitPane.setDividerPositions(0.07);
        motherSplitPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        motherSplitPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        motherSplitPane.setMinHeight(Double.NEGATIVE_INFINITY);
        motherSplitPane.setMinWidth(Double.NEGATIVE_INFINITY);
        motherSplitPane.setPrefHeight(400.0);
        motherSplitPane.setPrefWidth(600.0);

        AnchorPane topAnchorPane = new AnchorPane();
        topAnchorPane.setMinHeight(0.0);
        topAnchorPane.setMinWidth(0.0);
        topAnchorPane.setPrefHeight(100.0);
        topAnchorPane.setPrefWidth(160.0);
        formatCategBar = new HBox();
        formatCategBar.setId("formatCategBar");
        formatCategBar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        formatCategBar.setLayoutX(199.0);
        formatCategBar.setLayoutY(-37.0);
        formatCategBar.setPrefHeight(100.0);
        formatCategBar.setPrefWidth(200.0);
        videoFormatsButton = new Button("Video");
        videoFormatsButton.setId("videoFormatsButton");
        videoFormatsButton.setMnemonicParsing(false);
        
        HBox.setMargin(videoFormatsButton, new Insets(0.0));
        audioFormatsButton = new Button("Audio");
        audioFormatsButton.setId("audioFormatsButton");
        audioFormatsButton.setMnemonicParsing(false);
        
        HBox.setMargin(audioFormatsButton, new Insets(0.0));
        formatCategBar.getChildren().addAll(videoFormatsButton, audioFormatsButton);
        searchBar = new HBox();
        searchBar.setId("searchBar");
        searchBar.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        searchBar.setLayoutX(358.0);
        searchBar.setLayoutY(-37.0);
        searchBar.setPrefHeight(100.0);
        searchBar.setPrefWidth(200.0);
        formatSearchTField = new TextField();
        formatSearchTField.setId("formatSearchTField");
        formatSearchTField.setAlignment(javafx.geometry.Pos.CENTER);
        HBox.setMargin(formatSearchTField, new Insets(0.0));
        searchBar.getChildren().add(formatSearchTField);
        topAnchorPane.getChildren().addAll(formatCategBar, searchBar);
        AnchorPane.setBottomAnchor(formatCategBar, 0.0);
        AnchorPane.setLeftAnchor(formatCategBar, 0.0);
        AnchorPane.setRightAnchor(formatCategBar, 200.0);
        AnchorPane.setTopAnchor(formatCategBar, 0.0);
        AnchorPane.setBottomAnchor(searchBar, 0.0);
        AnchorPane.setLeftAnchor(searchBar, 300.0);
        AnchorPane.setRightAnchor(searchBar, 0.0);
        AnchorPane.setTopAnchor(searchBar, 0.0);

        AnchorPane bottomAnchorPane = new AnchorPane();
        bottomAnchorPane.setMinHeight(0.0);
        bottomAnchorPane.setMinWidth(0.0);
        bottomAnchorPane.setPrefHeight(100.0);
        bottomAnchorPane.setPrefWidth(160.0);
        childSplitPane = new SplitPane();
        childSplitPane.setId("childSplitPane");
        childSplitPane.setDividerPositions(0.2);
        childSplitPane.setLayoutX(199.0);
        childSplitPane.setLayoutY(77.0);
        childSplitPane.setPrefHeight(160.0);
        childSplitPane.setPrefWidth(200.0);
        AnchorPane leftAnchorPane = new AnchorPane();
        leftAnchorPane.setMinHeight(0.0);
        leftAnchorPane.setMinWidth(0.0);
        leftAnchorPane.setPrefHeight(160.0);
        leftAnchorPane.setPrefWidth(100.0);
        formatContainers = new ListView<>();
        formatContainers.setId("formatContainers");
        formatContainers.setLayoutX(-42.0);
        formatContainers.setLayoutY(49.0);
        formatContainers.setPrefHeight(200.0);
        formatContainers.setPrefWidth(200.0);
        formatContainers.getStyleClass().add("listView");
        leftAnchorPane.getChildren().add(formatContainers);
        AnchorPane.setBottomAnchor(formatContainers, 0.0);
        AnchorPane.setLeftAnchor(formatContainers, 0.0);
        AnchorPane.setRightAnchor(formatContainers, 0.0);
        AnchorPane.setTopAnchor(formatContainers, 0.0);
        AnchorPane rightAnchorPane = new AnchorPane();
        rightAnchorPane.setMinHeight(0.0);
        rightAnchorPane.setMinWidth(0.0);
        rightAnchorPane.setPrefHeight(160.0);
        rightAnchorPane.setPrefWidth(100.0);
        formatAtrrs = new ListView<>();
        formatAtrrs.setId("formatAtrrs");
        formatAtrrs.setLayoutX(137.0);
        formatAtrrs.setLayoutY(64.0);
        formatAtrrs.setPrefHeight(200.0);
        formatAtrrs.setPrefWidth(200.0);
        formatAtrrs.getStyleClass().add("listView");
        rightAnchorPane.getChildren().add(formatAtrrs);
        AnchorPane.setBottomAnchor(formatAtrrs, 0.0);
        AnchorPane.setLeftAnchor(formatAtrrs, 0.0);
        AnchorPane.setRightAnchor(formatAtrrs, 0.0);
        AnchorPane.setTopAnchor(formatAtrrs, 0.0);
        childSplitPane.getItems().addAll(leftAnchorPane, rightAnchorPane);
        bottomAnchorPane.getChildren().add(childSplitPane);
        AnchorPane.setBottomAnchor(childSplitPane, 0.0);
        AnchorPane.setLeftAnchor(childSplitPane, 0.0);
        AnchorPane.setRightAnchor(childSplitPane, 0.0);
        AnchorPane.setTopAnchor(childSplitPane, 0.0);

        motherSplitPane.getItems().addAll(topAnchorPane, bottomAnchorPane);

        FormatsModel.singleFormatContainers.clear();
        FormatsModel.singleFormatContainers.add(MP4.formatContainerName.toUpperCase());
        formatContainers.setItems(FormatsModel.singleFormatContainers);
        formatAtrrs.setItems(FormatsModel.singleFormatAttrs);

        formatContainers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null){
                        switch (newValue){
                            case "MP4":
                                FormatsModel.singleFormatAttrs.clear();
                                FormatsModel.singleFormatAttrs.setAll(MP4.getMinimalSettingMap().values());
                                if(format == null || !(format instanceof MP4)){
                                    format = new MP4();
                                    format.setDefault();
                                    fileConverterInit.getService().setTargetFormat(format);
                                    convertToButton.setText(format.getCurrentConfigForTextButton());
                                }
                                break;

                        }
                    }

                }
        );
        formatAtrrs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    MainController mainController =
                            Main.mainControllerFxmlLoader.getController();


                    for (Map.Entry<String, String> entry : MP4.getMinimalSettingMap().entrySet()) {
                        if (Objects.equals(newValue, entry.getValue())) {

                            switch (entry.getKey()){
                                case "8K":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).set8K();

                                    updateFormatAndCloseStage(entry);

                                    break;
                                case "4K":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).set4K();

                                    updateFormatAndCloseStage(entry);

                                    break;
                                case "UHD2160":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).setUHD2160();

                                    updateFormatAndCloseStage(entry);

                                    break;
                                case "1080p":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).set1080p();

                                    updateFormatAndCloseStage(entry);

                                    break;
                                case "720p":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).set720p();

                                    updateFormatAndCloseStage(entry);

                                    break;
                                case "640p":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).set640p();

                                    updateFormatAndCloseStage(entry);

                                    break;
                                case "480p":
                                    ((MP4)fileConverterInit.getService().getTargetFormat()).set480p();

                                    updateFormatAndCloseStage(entry);

                                    break;
                            }
                        }
                    }
                });

        scene = new Scene(motherSplitPane, 600, 400);
        scene.getStylesheets().add(Main.class.getResource("formats/ConvertCellWidgetFormatSelector.css").toExternalForm());
        stage.initStyle(StageStyle.UTILITY);  
        stage.setResizable(false);


        stage.setOnCloseRequest(event -> {
            Platform.runLater(()->FormatsModel.singleFormatAttrs.clear());

        });
        stage.setTitle("SplitPane Example");
        stage.setScene(scene);

        stage.show();
    }
    private void updateFormatAndCloseStage(Map.Entry<String, String> entry) {

        fileConverterInit.getService().setSingleFormatChanged(true);
        Platform.runLater(()->{

            String input = entry.getValue();
            String[] parts = input.split(" ");
            String result = String.format("%s %s", parts[0], parts[1]);
            convertToButton.setText(result);

            FormatsModel.singleFormatAttrs.clear();

            stage.close();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image importImageI = new Image(String.valueOf(Main.class.getResource("icons/trash-off.png")));

        
        ImageView importImage = new ImageView(importImageI);
        importImage.setFitWidth(15);
        importImage.setFitHeight(15);
        
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

}
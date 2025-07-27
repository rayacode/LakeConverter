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

import codes.rayacode.lakeconverter.conversion.Formats.Format;
import codes.rayacode.lakeconverter.conversion.Formats.MP4;
import codes.rayacode.lakeconverter.conversion.Formats.VideoFormat;
import codes.rayacode.lakeconverter.fileoperations.FileService;
import codes.rayacode.lakeconverter.jave.encode.enums.PresetEnum;
import codes.rayacode.lakeconverter.jave.encode.enums.TuneEnum;
import codes.rayacode.lakeconverter.jave.filters.*;
import codes.rayacode.lakeconverter.jave.filters.helpers.ForceOriginalAspectRatio;
import codes.rayacode.lakeconverter.models.FormatsModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import codes.rayacode.lakeconverter.jave.encode.enums.X264_PROFILE;
import codes.rayacode.lakeconverter.jave.info.VideoSize;

import java.net.URL;
import java.util.ResourceBundle;

public class FormatsSettingsController implements Initializable {
    Logger log = LoggerFactory.getLogger("FormatSettingsLogger");
    public static boolean programInit = false;
    @FXML
    private Tab videoTab;
    @FXML
    private ComboBox<String> encoderCombo;
    @FXML
    private ComboBox<VideoSize> resolutionCombo;
    @FXML
    private TextField resoluionWidthField;
    @FXML
    private TextField resolutionHeightField;
    @FXML
    private ComboBox<Integer> bitrateCombo;
    @FXML
    private TextField bitrateField;
    @FXML
    private ComboBox<Integer> crfCombo;
    @FXML
    private TextField crfField;
    @FXML
    private ComboBox<X264_PROFILE> profileCombo;
    @FXML
    private ComboBox<Integer> frameRateCombo;
    @FXML
    private ComboBox<String> audioEncoderCombo;
    @FXML
    private ComboBox<Integer> sampleRateCombo;
    @FXML
    private ComboBox<Integer> channelCombo;
    @FXML
    private ComboBox<Integer> audioBitrateCombo;
    @FXML
    private TextField frameRateField;
    @FXML
    private ComboBox<String> pixelFormatCombo;
    @FXML
    private Label profileLabel;
    @FXML
    private Label crfLabel;
    @FXML
    private Label tuneLabel;
    @FXML
    private ComboBox<TuneEnum> tuneCombo;
    @FXML
    private ComboBox<String> presetCombo;
    public static StringProperty customResolution = new SimpleStringProperty();
    public static ComboBox<VideoSize>resolutionComboMirror;
    public static Boolean isCustomResolutionUsed = false;
    public void initEncoderCombo(){
        if(FileService.format instanceof MP4){
            
            encoderCombo.setItems(MP4.videoEncoders);
            encoderCombo.setValue(FileService.format.getVideoAttributes().getCodec().get());
            encoderCombo.setButtonCell(new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {

                        setText(null);
                    } else {

                        setText(FileService.format.getVideoAttributes().getCodec().get());

                    }
                }
            });
            encoderCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    FileService.format.getVideoAttributes().setCodec(newValue);
                    if(newValue == MP4.AV1){
                        pixelFormatCombo.setItems(MP4.AV1Pixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }

                    }
                    if(newValue == MP4.MPEG4){
                        pixelFormatCombo.setItems(MP4.mpeg4Pixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.LIBX264RGB){
                        pixelFormatCombo.setItems(MP4.LIBX264RGBPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }

                    if(newValue == MP4.LIBX264){
                        pixelFormatCombo.setItems(MP4.LIBX264Pixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                        tuneCombo.setDisable(false);
                        tuneCombo.setVisible(true);
                        crfCombo.setDisable(false);
                        crfCombo.setVisible(true);
                        profileCombo.setDisable(false);
                        profileCombo.setVisible(true);
                        tuneLabel.setVisible(true);
                        profileLabel.setVisible(true);
                        crfLabel.setVisible(true);
                        crfCombo.setItems(MP4.crfValues);
                        crfCombo.setValue(23);
                        FileService.format.getVideoAttributes().setCrf(23);
                        profileCombo.setItems(MP4.profiles);
                        profileCombo.setValue(X264_PROFILE.MAIN);
                        FileService.format.getVideoAttributes().setX264Profile(X264_PROFILE.MAIN);
                    }else {
                        if(FileService.format.getVideoAttributes().getCrf().isPresent()){
                            FileService.format.getVideoAttributes().setCrf(null);
                            FileService.format.getVideoAttributes().setX264Profile(null);
                            FileService.format.getVideoAttributes().setTune(null);
                        }
                        tuneCombo.setDisable(true);
                        tuneCombo.setVisible(false);
                        crfCombo.setDisable(true);
                        crfCombo.setVisible(false);
                        profileCombo.setDisable(true);
                        profileCombo.setVisible(false);
                        tuneLabel.setVisible(false);
                        profileLabel.setVisible(false);
                        crfLabel.setVisible(false);
                    }
                    if(newValue == MP4.H264_AMF){
                        pixelFormatCombo.setItems(MP4.H264_AMFPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.H264_NVENC){
                        pixelFormatCombo.setItems(MP4.H264_NVENCPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.H264_QSV){
                        pixelFormatCombo.setItems(MP4.H264_QSVPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.LIBX265){
                        pixelFormatCombo.setItems(MP4.LIBX265Pixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.HEVC_AMF){
                        pixelFormatCombo.setItems(MP4.HEVC_AMFPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.HEVC_NVENC){
                        pixelFormatCombo.setItems(MP4.HEVC_NVENCPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.HEVC_QSV){
                        pixelFormatCombo.setItems(MP4.HEVC_QSVPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.LIBVPX_VP9){
                        pixelFormatCombo.setItems(MP4.LIBVPX_VP9Pixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }
                    if(newValue == MP4.CFHD){
                        pixelFormatCombo.setItems(MP4.CFHDPixels);
                        if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                            FileService.format.getVideoAttributes().setPixelFormat(null);
                        }
                    }

                }
            });
        }
    }
    public void initResolutionCombo(){
        resoluionWidthField.setEditable(false);
        resolutionHeightField.setEditable(false);
        if(FileService.format instanceof MP4){
            FXCollections.sort(MP4.videoResolutions, (v1, v2) -> {
                int res1 = v1.getWidth() * v1.getHeight();
                int res2 = v2.getWidth() * v2.getHeight();
                return Integer.compare(res2, res1);
            });
            resolutionCombo.setItems(MP4.videoResolutions);



            resolutionCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VideoSize>() {
                @Override
                public void changed(ObservableValue<? extends VideoSize> observable, VideoSize oldValue, VideoSize newValue) {
                    if(newValue != null){
                        FileService.format.getVideoAttributes().setSize(newValue);
                        resoluionWidthField.setText(String.valueOf(newValue.getWidth()));
                        resolutionHeightField.setText(String.valueOf(newValue.getHeight()));
                        if(FormatsSettingsController.programInit) {
                            isCustomResolutionUsed = true;
                            customResolution.set("Custom: " + newValue.getWidth() + " * " + newValue.getHeight());
                        }
                    }
                }
            });
            resolutionCombo.setCellFactory(new Callback<ListView<VideoSize>, ListCell<VideoSize>>() {
                @Override
                public ListCell<VideoSize> call(ListView<VideoSize> param) {
                    return new ListCell<VideoSize>() {
                        @Override
                        protected void updateItem(VideoSize item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                            } else {

                                setText(item.getWidth() + " * " + item.getHeight());
                            }
                        }
                    };
                }
            });
            resolutionCombo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    log.info("ProgramInit Got true");
                    FormatsSettingsController.programInit = true;
                }
            });
            resolutionCombo.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    log.info("ProgramInit Got true");
                    FormatsSettingsController.programInit = true;
                }
            });
            if(FileService.format.getVideoAttributes().getSize().isPresent()) {
                resolutionCombo.setValue(FileService.format.getVideoAttributes().getSize().get());
            }
            resolutionCombo.setButtonCell(new ListCell<VideoSize>() {
                @Override
                protected void updateItem(VideoSize item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {

                        setText(null);
                    } else {

                        setText(String.format(item.getWidth() + " * " + item.getHeight()));
                    }
                }
            });

            resoluionWidthField.textProperty().addListener((obs, oldVal, newVal) -> {

                Platform.runLater(() -> {
                    if (resoluionWidthField.getText().length() > 0) {
                        resoluionWidthField.positionCaret(resoluionWidthField.getText().length());
                    }
                });

            });
            resolutionHeightField.textProperty().addListener((obs, oldVal, newVal) -> {

                Platform.runLater(() -> {
                    if (resolutionHeightField.getText().length() > 0) {
                        resolutionHeightField.positionCaret(resolutionHeightField.getText().length());
                    }
                });

            });

        }
    }
    public void initBitrateCombo(){
        if(FileService.format instanceof MP4){
            bitrateField.setVisible(false);
            if(FileService.format.getVideoAttributes().getBitRate().isPresent()) {
                bitrateCombo.setValue(FileService.format.getVideoAttributes().getBitRate().get() / 1000);
            }
            bitrateCombo.setItems(MP4.bitRates);
            bitrateCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                    FileService.format.getVideoAttributes().setBitRate((1000*newValue));
                }
            });

        }
    }
    public void initCRFCombo(){
        if(FileService.format instanceof MP4){
            crfField.setVisible(false);
            if(FileService.format.getVideoAttributes().getCodec().get().contains("libx264")){
                crfCombo.setDisable(false);
            }else {
                crfCombo.setDisable(true);
            }
            crfCombo.setItems(MP4.crfValues);
            if(FileService.format.getVideoAttributes().getCrf().isPresent()) {
                crfCombo.setValue(FileService.format.getVideoAttributes().getCrf().get());
            }
            crfCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                    FileService.format.getVideoAttributes().setCrf(newValue);
                    if(newValue == 0){
                        FileService.format.getVideoAttributes().setX264Profile(X264_PROFILE.HIGH444);
                        profileCombo.setValue(X264_PROFILE.HIGH444);
                        profileCombo.setDisable(true);
                    }else {profileCombo.setDisable(false);}
                }
            });
        }
    }
    public void initProfileCombo(){
        if(FileService.format instanceof MP4){
            if(FileService.format.getVideoAttributes().getCodec().get().contains("libx264")){
                profileCombo.setDisable(false);
            }else {

                profileCombo.setDisable(true);
            }
            if(FileService.format.getVideoAttributes().getCrf().isPresent()) {
                if (FileService.format.getVideoAttributes().getCrf().get() == 0) {
                    profileCombo.setValue(X264_PROFILE.HIGH444);
                    profileCombo.setDisable(true);
                }
            }
            profileCombo.setItems(MP4.profiles);
            if(FileService.format.getVideoAttributes().getX264Profile().isPresent()){
                profileCombo.setValue(FileService.format.getVideoAttributes().getX264Profile().get());
            }

            profileCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<X264_PROFILE>() {
                @Override
                public void changed(ObservableValue<? extends X264_PROFILE> observable, X264_PROFILE oldValue, X264_PROFILE newValue) {
                    if(newValue != X264_PROFILE.BASELINE){
                        FileService.format.getVideoAttributes().setX264Profile(newValue);
                    }else {
                        FileService.format.getVideoAttributes().setX264Profile(null);
                    }
                }
            });
        }
    }
    public void initFrameRateCombo(){
        if(FileService.format instanceof MP4){
            frameRateField.setVisible(false);
            frameRateCombo.setItems(MP4.frameRates);
            if(FileService.format.getVideoAttributes().getFrameRate().isPresent()){
                frameRateCombo.setValue(FileService.format.getVideoAttributes().getFrameRate().get());
            }

            frameRateCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                    FileService.format.getVideoAttributes().setFrameRate(newValue);
                }
            });
        }
    }
    public void initPixelFormatCombo(){

        if(FileService.format instanceof MP4){
            if(FileService.format.getVideoAttributes().getPixelFormat().isPresent()){
                pixelFormatCombo.setValue(FileService.format.getVideoAttributes().getPixelFormat().get());
            }else {
                pixelFormatCombo.setValue("Default");
            }
            if (encoderCombo.getValue() == MP4.AV1){
                pixelFormatCombo.setItems(MP4.AV1Pixels);
            } else if (encoderCombo.getValue() == MP4.CFHD) {
                pixelFormatCombo.setItems(MP4.CFHDPixels);
            } else if (encoderCombo.getValue() == MP4.LIBVPX_VP9) {
                pixelFormatCombo.setItems(MP4.LIBVPX_VP9Pixels);
            }else if (encoderCombo.getValue() == MP4.LIBX264) {
                pixelFormatCombo.setItems(MP4.LIBX264Pixels);
            }else if (encoderCombo.getValue() == MP4.LIBX264RGB) {
                pixelFormatCombo.setItems(MP4.LIBX264RGBPixels);
            }else if (encoderCombo.getValue() == MP4.H264_AMF) {
                pixelFormatCombo.setItems(MP4.H264_AMFPixels);
            }else if (encoderCombo.getValue() == MP4.H264_NVENC) {
                pixelFormatCombo.setItems(MP4.H264_NVENCPixels);
            }else if (encoderCombo.getValue() == MP4.H264_QSV) {
                pixelFormatCombo.setItems(MP4.H264_QSVPixels);
            }else if (encoderCombo.getValue() == MP4.LIBX265) {
                pixelFormatCombo.setItems(MP4.LIBX265Pixels);
            }else if (encoderCombo.getValue() == MP4.HEVC_AMF) {
                pixelFormatCombo.setItems(MP4.HEVC_AMFPixels);
            }else if (encoderCombo.getValue() == MP4.HEVC_NVENC) {
                pixelFormatCombo.setItems(MP4.HEVC_NVENCPixels);
            }else if (encoderCombo.getValue() == MP4.HEVC_QSV) {
                pixelFormatCombo.setItems(MP4.HEVC_QSVPixels);
            }
            pixelFormatCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    FileService.format.getVideoAttributes().setPixelFormat(newValue);
                }
            });
        }
    }
    public void initPresetCombo(){
        presetCombo.setItems(MP4.presets);
        presetCombo.setValue("Default");
        if(FileService.format.getVideoAttributes().getPreset().isPresent()){
            presetCombo.setValue(FileService.format.getVideoAttributes().getPreset().get());
        }
        presetCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if(newValue != "Default") {
                    FileService.format.getVideoAttributes().setPreset(newValue);
                }else{
                    FileService.format.getVideoAttributes().setPreset(null);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resolutionComboMirror = resolutionCombo;

        initEncoderCombo();
        initResolutionCombo();
        initBitrateCombo();
        initCRFCombo();
        initCRFCombo();
        initProfileCombo();
        initFrameRateCombo();
        initPixelFormatCombo();
        initPresetCombo();


    }
}
/*  LakeConverter: A java Gui wrapper with jave for ffmpeg
 *  Copyright (C) André Schild
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
import codes.rayacode.lakeconverter.conversion.FileConverterInit;

import codes.rayacode.lakeconverter.conversion.Formats.Format;
import codes.rayacode.lakeconverter.conversion.Formats.MP4;
import codes.rayacode.lakeconverter.fileoperations.FileService;
import codes.rayacode.lakeconverter.models.FormatsModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConvertCellWidgetFormatSelector implements Initializable {

    public static boolean isUsed = false;
    @FXML
    private SplitPane motherSplitPane;

    @FXML
    private HBox formatCategBar;

    @FXML
    private Button videoFormatsButton;

    @FXML
    private Button audioFormatsButton;

    @FXML
    private HBox searchBar;

    @FXML
    private TextField formatSearchTField;

    @FXML
    private SplitPane childSplitPane;

    @FXML
    private ListView<String> formatContainers;

    @FXML
    private ListView<String> formatAtrrs;
    private ConvertCellWidget convertCellWidget;

    @FXML
    void onAudioFormatLIstAction() {
        
    }
    Format format;
    @FXML
    void onVideoFormatLIstAction() {
        FormatsModel.singleFormatContainers.clear();
        FormatsModel.singleFormatContainers.add(MP4.formatContainerName.toUpperCase());

        formatContainers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue){
                        case "MP4":
                            FormatsModel.singleFormatAttrs.clear();
                            FormatsModel.singleFormatAttrs.setAll(MP4.getMinimalSettingMap().values());
                            FileService.format = new MP4();
                            format = new MP4();
                            FileService.format.setDefault();
                            break;

                    }

                }
        );
        formatAtrrs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    MainController mainController =
                            Main.mainControllerFxmlLoader.getController();
                    ConvertCellWidget convertCellWidget = mainController.
                            getConvertCellWidgetListView().
                            getSelectionModel().getSelectedItem();
                    FileConverterInit fileConverterInit = convertCellWidget.getFileConverterInit();
                    isUsed = true;

                    for (Map.Entry<String, String> entry : MP4.getMinimalSettingMap().entrySet()) {
                        if (Objects.equals(newValue, entry.getValue())) {

                            switch (entry.getKey()){
                                case "8K":
                                        ((MP4)format).set8K();

                                    fileConverterInit.getService().setTargetFormat(format);
                                    fileConverterInit.getService().setSingleFormatChanged(true);
                                    Platform.runLater(() -> {
                                        String input = entry.getValue();
                                        String[] parts = input.split(" ");
                                        String result = String.format("%s %s", parts[0], parts[1]);
                                        convertCellWidget.getConvertToButton().setText(result);
                                    });
                                    break;
                                case "4K":
                                    ((MP4)format).setUHD2160();
                                    fileConverterInit.getService().setTargetFormat(format);
                                    fileConverterInit.getService().setSingleFormatChanged(true);
                                    Platform.runLater(() -> {
                                        String input = entry.getValue();
                                        String[] parts = input.split(" ");
                                        String result = String.format("%s %s", parts[0], parts[1]);
                                        convertCellWidget.getConvertToButton().setText(result);
                                    });
                                    break;
                                case "1080p":
                                    ((MP4)format).set1080p();
                                    fileConverterInit.getService().setTargetFormat(format);
                                    fileConverterInit.getService().setSingleFormatChanged(true);
                                    Platform.runLater(() -> {
                                        String input = entry.getValue();
                                        String[] parts = input.split(" ");
                                        String result = String.format("%s %s", parts[0], parts[1]);
                                        convertCellWidget.getConvertToButton().setText(result);
                                    });
                                    break;
                                case "720p":
                                    ((MP4)format).set720p();
                                    fileConverterInit.getService().setTargetFormat(format);
                                    fileConverterInit.getService().setSingleFormatChanged(true);
                                    Platform.runLater(() -> {
                                        String input = entry.getValue();
                                        String[] parts = input.split(" ");
                                        String result = String.format("%s %s", parts[0], parts[1]);
                                        convertCellWidget.getConvertToButton().setText(result);
                                    });
                                    break;
                                case "640p":
                                    ((MP4)format).set640p();
                                    fileConverterInit.getService().setTargetFormat(format);
                                    fileConverterInit.getService().setSingleFormatChanged(true);
                                    Platform.runLater(() -> {
                                        String input = entry.getValue();
                                        String[] parts = input.split(" ");
                                        String result = String.format("%s %s", parts[0], parts[1]);
                                        convertCellWidget.getConvertToButton().setText(result);
                                    });
                                    break;
                                case "480p":
                                    ((MP4)format).set480p();
                                    fileConverterInit.getService().setTargetFormat(format);
                                    fileConverterInit.getService().setSingleFormatChanged(true);
                                    Platform.runLater(() -> {
                                        String input = entry.getValue();
                                        String[] parts = input.split(" ");
                                        String result = String.format("%s %s", parts[0], parts[1]);
                                        convertCellWidget.getConvertToButton().setText(result);
                                    });
                                    break;
                            }
                        }
                    }
                });

    }

    public ConvertCellWidget getConvertCellWidget() {
        return convertCellWidget;
    }

    public void setConvertCellWidget(ConvertCellWidget convertCellWidget) {
        this.convertCellWidget = convertCellWidget;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        formatContainers.setItems(FormatsModel.singleFormatContainers);
        formatAtrrs.setItems(FormatsModel.singleFormatAttrs);
    }
}
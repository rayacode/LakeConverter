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
import codes.rayacode.lakeconverter.UIUpdater;
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

public class FormatsController implements Initializable {

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

    @FXML
    void onVideoFormatLIstAction() {
        FormatsModel.formatContainers.clear();
        FormatsModel.formatContainers.add(MP4.formatContainerName.toUpperCase());

        formatContainers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue){
                        case "MP4":
                            FormatsModel.formatAttrs.clear();
                            FormatsModel.formatAttrs.setAll(MP4.getMinimalSettingMap().values());

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
                isUsed = true;
                    for (Map.Entry<String, String> entry : MP4.getMinimalSettingMap().entrySet()) {
                        if (Objects.equals(newValue, entry.getValue())) {

                            switch (entry.getKey()){
                                case "8K":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4) FileService.format).set8K();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "4K":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4) FileService.format).set4K();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "UHD2160":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4)FileService.format).setUHD2160();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "1080p":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4)FileService.format).set1080p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "720p":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4)FileService.format).set720p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "640p":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4)FileService.format).set640p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "480p":
                                    FileService.format = new MP4();
                                    FileService.format.setDefault();
                                    ((MP4)FileService.format).set480p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                            }
                        }
                    }
        });

    }
    private void updateFormatAndUI(Map.Entry<String, String> entry, MainController mainController) {
        FormatsSettingsController.isCustomResolutionUsed = false;
        FileService.qConversionManager.changeFormats(FileService.format);
        FormatsSettingsController.programInit = false;
        Platform.runLater(() -> {
            String input = entry.getValue();
            String[] parts = input.split(" ");
            String result = String.format("%s %s", parts[0], parts[1]);
            mainController.getConvertToButton().setText(result);

            for(ConvertCellWidget cellWidget : UIUpdater.items){
                if(!cellWidget.getFileConverterInit().getService().isSingleFormatChanged()){
                    cellWidget.getConvertToButton().setText(result);
                    System.out.println(cellWidget.isSingleFormatSelected() +"hey");
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
        videoFormatsButton.setFocusTraversable(true);
        formatContainers.setItems(FormatsModel.formatContainers);
        formatAtrrs.setItems(FormatsModel.formatAttrs);
    }
}
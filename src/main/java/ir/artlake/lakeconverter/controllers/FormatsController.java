package ir.artlake.lakeconverter.controllers;

import ir.artlake.lakeconverter.ConvertCellWidget;
import ir.artlake.lakeconverter.Main;
import ir.artlake.lakeconverter.UIUpdater;
import ir.artlake.lakeconverter.conversion.Formats.MP4;
import ir.artlake.lakeconverter.conversion.Formats.MP4Attr;
import ir.artlake.lakeconverter.fileoperations.FileService;
import ir.artlake.lakeconverter.models.FormatsModel;
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
        // TODO: Implement your logic here
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
                            FormatsModel.formatAttrs.setAll(MP4Attr.getMinimalSettingMap().values());
                            FileService.format = new MP4();

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
                isUsed = true;
                    for (Map.Entry<String, String> entry : MP4Attr.getMinimalSettingMap().entrySet()) {
                        if (Objects.equals(newValue, entry.getValue())) {

                            switch (entry.getKey()){
                                case "8K":
                                    ((MP4) FileService.format).set8K();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "4K":
                                    ((MP4) FileService.format).set4K();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "UHD2160":
                                    ((MP4)FileService.format).setUHD2160();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "1080p":
                                    ((MP4)FileService.format).set1080p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "720p":
                                    ((MP4)FileService.format).set720p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "640p":
                                    ((MP4)FileService.format).set640p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                                case "480p":
                                    ((MP4)FileService.format).set480p();
                                    updateFormatAndUI(entry, mainController);
                                    break;
                            }
                        }
                    }
        });

    }
    private void updateFormatAndUI(Map.Entry<String, String> entry, MainController mainController) {
        FileService.qConversionManager.changeFormats(FileService.format);

        Platform.runLater(() -> {
            String input = entry.getValue();
            String[] parts = input.split(" ");
            String result = String.format("%s %s", parts[0], parts[1]);
            mainController.getConvertToButton().setText(result);

            for(ConvertCellWidget cellWidget : UIUpdater.items){
                if(!cellWidget.getFileConverterInit().getService().isSingleFormatChanged()){
                    cellWidget.getConvertToButton().setText(result);
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

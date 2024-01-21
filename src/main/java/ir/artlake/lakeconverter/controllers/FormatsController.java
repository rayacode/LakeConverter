package ir.artlake.lakeconverter.controllers;

import ir.artlake.lakeconverter.conversion.Formats.MP4;
import ir.artlake.lakeconverter.conversion.Formats.MP4Attr;
import ir.artlake.lakeconverter.models.FormatsModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class FormatsController implements Initializable {

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

    @FXML
    void onAudioFormatLIstAction() {
        // TODO: Implement your logic here
    }

    @FXML
    void onVideoFormatLIstAction() {
        // TODO: Implement your logic here
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FormatsModel.formatContainers.add(MP4.formatContainerName.toUpperCase());
        FormatsModel.formatContainers.add("MKV");


        formatContainers.setItems(FormatsModel.formatContainers);
        formatAtrrs.setItems(FormatsModel.formatAttrs);

        formatContainers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue){
                        case "MP4":
                            FormatsModel.formatAttrs.clear();
                            FormatsModel.formatAttrs.setAll(MP4Attr.getMinimalSettingMap().values());
                            break;
                        case "MKV":
                            FormatsModel.formatAttrs.clear();
                            FormatsModel.formatAttrs.add("MKV 480p 720*480");
                            break;
                    }

                }
        );
    }
}

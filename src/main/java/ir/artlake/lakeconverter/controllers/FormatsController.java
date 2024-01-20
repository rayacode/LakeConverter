package ir.artlake.lakeconverter.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FormatsController {

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
    private ListView<?> formatContainers;

    @FXML
    private ListView<?> formatAtrrs;

    @FXML
    void onAudioFormatLIstAction() {
        // TODO: Implement your logic here
    }

    @FXML
    void onVideoFormatLIstAction() {
        // TODO: Implement your logic here
    }
}

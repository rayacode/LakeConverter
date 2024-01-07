package ir.artlake.lakeconverter;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIUpdater {
    private ListView convListView;
    private Label messageLabel;

    public UIUpdater(){
        // Bind the ListView's items to the ObservableList

    }
    public void setConvWidgetsContainer(ListView convWidgetsContainer) {
        this.convListView = convWidgetsContainer;
        convListView.setItems(items);
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }
    // List to keep track of the File objects that have already been added
    private List<File> addedFiles = new ArrayList<>();
    // Create an ObservableList to back your items
    ObservableList<ConvertWidgetBox> items = FXCollections.observableArrayList();



    public void handleFileSelection(List<File> sourceFiles, List<FileConverterInit> fileConverterInitList) throws Exception {
        for (int i = 0; i < sourceFiles.size() && i < fileConverterInitList.size(); i++) {
            File file = sourceFiles.get(i);
            // Only add the fileBox if the file hasn't been added before
            if (!addedFiles.contains(file)) {
                // Retrieve the FileConverterInit for this file, or create a new one if it doesn't exist

                ConvertWidgetBox fileBox = new ConvertWidgetBox(fileConverterInitList.get(i), file);

                items.add(fileBox);
                addedFiles.add(file);

            }
        }
    }

    public void showFadeTransition(String message) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        messageLabel.setText(message);
        fadeIn.play();

        fadeIn.setOnFinished(event -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished(event -> {
            messageLabel.setText("");
        });
    }
}

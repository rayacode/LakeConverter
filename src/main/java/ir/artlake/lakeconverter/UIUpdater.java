package ir.artlake.lakeconverter;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UIUpdater {
    private ListView convListView;
    private Label messageLabel;

    public void setConvWidgetsContainer(ListView convWidgetsContainer) {
        this.convListView = convWidgetsContainer;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }
    // List to keep track of the File objects that have already been added
    private List<File> addedFiles = new ArrayList<>();

    public void handleFileSelection(List<File> sourceFiles, List<FileConverterInit> fileConverterInitList) throws Exception {
        for (int i = 0; i < sourceFiles.size() && i < fileConverterInitList.size(); i++) {
            File file = sourceFiles.get(i);
            // Only add the fileBox if the file hasn't been added before
            if (!addedFiles.contains(file)) {
                ConvertWidgetBox fileBox = new ConvertWidgetBox(fileConverterInitList.get(i), file);

                convListView.getItems().add(fileBox);
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

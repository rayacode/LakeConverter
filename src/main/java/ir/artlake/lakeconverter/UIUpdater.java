package ir.artlake.lakeconverter;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class UIUpdater {
    private VBox convWidgetsContainer;
    private Label messageLabel;

    public void setConvWidgetsContainer(VBox convWidgetsContainer) {
        this.convWidgetsContainer = convWidgetsContainer;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }
    public void handleFileSelection(List<File> sourceFiles, List<FileConverterInit> fileConverterInitList) throws Exception {
        for (int i = 0; i < sourceFiles.size() && i < fileConverterInitList.size(); i++) {
            ConvertWidgetBox fileBox = new ConvertWidgetBox(fileConverterInitList.get(i), sourceFiles.get(i));
            convWidgetsContainer.getChildren().add(fileBox);
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

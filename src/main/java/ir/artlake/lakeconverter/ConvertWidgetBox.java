package ir.artlake.lakeconverter;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

public class ConvertWidgetBox extends HBox {
    private ProgressBar progressBar;
    private Label progressLabel;
    ThumbnailGenerator thumbnailGenerator;

    public ConvertWidgetBox(FileConverterInit fileConverterInit, File file) throws Exception {
        thumbnailGenerator = new ThumbnailGenerator();
        progressBar = new ProgressBar();
        progressLabel = new Label();
        Label fileNameLabel = new Label(file.getName());
        /*ImageView thumbnailView =
                new ImageView(
                        thumbnailGenerator.generateThumbnail(file.getPath()).toString());*/
        progressBar.progressProperty().bind(fileConverterInit.getTask().progressProperty());
        fileConverterInit.getTask().progressProperty().addListener((obs, oldProgress, newProgress) -> {
            Platform.runLater(() -> {
                progressLabel.setText(String.format("%.0f%%", newProgress.doubleValue() * 100));
            });
        });

        this.getChildren().addAll(progressBar, progressLabel);
    }
}

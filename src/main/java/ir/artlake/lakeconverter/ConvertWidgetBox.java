package ir.artlake.lakeconverter;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

public class ConvertWidgetBox extends HBox {
    private Label fileNameLabel;
    private ProgressBar progressBar;
    private Label progressLabel;
    private Label convertStatusLabel;
    ThumbnailGenerator thumbnailGenerator;

    public ConvertWidgetBox(FileConverterInit fileConverterInit, File file) throws Exception {

        fileNameLabel = new Label(file.getName().substring(0, file.getName().lastIndexOf('.')));
        HBox.setMargin(fileNameLabel, new javafx.geometry.Insets(0, 10, 0, 0));
        HBox.setHgrow(fileNameLabel, javafx.scene.layout.Priority.ALWAYS);
        thumbnailGenerator = new ThumbnailGenerator();

        progressBar = new ProgressBar();
        HBox.setMargin(progressBar, new javafx.geometry.Insets(0, 10, 0, 0));
        HBox.setHgrow(progressBar, javafx.scene.layout.Priority.ALWAYS);
        progressLabel = new Label();
        HBox.setMargin(progressLabel, new javafx.geometry.Insets(0, 10, 0, 0));
        HBox.setHgrow(progressLabel, javafx.scene.layout.Priority.ALWAYS);
        convertStatusLabel = new Label("Start!");
        HBox.setMargin(convertStatusLabel, new javafx.geometry.Insets(0, 10, 0, 0));
        HBox.setHgrow(convertStatusLabel, javafx.scene.layout.Priority.ALWAYS);
        //Label fileNameLabel = new Label(file.getName());
        /*ImageView thumbnailView =
                new ImageView(
                        thumbnailGenerator.generateThumbnail(file.getPath()).toString());*/
        progressBar.progressProperty().bind(fileConverterInit.getTask().progressProperty());
        int[] dotCount = {0}; // Array to hold the dot count
        fileConverterInit.getTask().progressProperty().addListener((obs, oldProgress, newProgress) -> {
            Platform.runLater(() -> {
                progressLabel.setText(String.format("%.0f%%", newProgress.doubleValue() * 100));
                if (newProgress.doubleValue() < 1.0 && newProgress.doubleValue() > 0.0) {
                    String dots = new String(new char[dotCount[0] % 3 + 1]).replace("\0", ".");
                    convertStatusLabel.setText("Converting" + dots);
                    dotCount[0]++;
                } else {
                    convertStatusLabel.setText("Done!");
                }
                System.out.println("Progress property for file " + file.getName() + " changed, new progress: " + newProgress);
            });
        });

        this.getChildren().addAll(fileNameLabel, progressBar, progressLabel, convertStatusLabel);
    }
}

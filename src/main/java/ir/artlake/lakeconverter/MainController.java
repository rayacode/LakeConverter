package ir.artlake.lakeconverter;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ProgressBar progBar;
    @FXML
    private Button convertBtt;
    @FXML
    private Label messageLabel;
    @FXML
    private Label progressLabel;
    public MainController(){

    }
    @FXML
    protected void onHelloButtonClick() {
        ConversionProgressTask task = new ConversionProgressTask();
        ConvertProgressListener listener = new ConvertProgressListener(task);
        Converter converter = new Converter(listener);

        progBar.progressProperty().bind(task.progressProperty());
        task.progressProperty().addListener((obs, oldProgress, newProgress) -> {
            Platform.runLater(() -> {
                progressLabel.setText(String.format("%.0f%%", newProgress.doubleValue() * 100));
            });
        });
        Thread thread = new Thread(() -> {
            converter.convert();
            Platform.runLater(() -> {
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), messageLabel);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.setCycleCount(1);

                // Create a fade out transition
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), messageLabel);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.setCycleCount(1);

                // Show the message with the fade in transition
                messageLabel.setText("The file has been successfully converted!");
                fadeIn.play();

                // Start the fade out transition after the fade in transition finishes
                fadeIn.setOnFinished(event -> {
                    fadeOut.play();
                });

                // Clear the message after the fade out transition finishes
                fadeOut.setOnFinished(event -> {
                    messageLabel.setText("");
                    progBar.progressProperty().unbind();
                    progBar.setProgress(0);
                });
            });
        });

        thread.start();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        convertBtt.getStyleClass().setAll("btn","btn-danger");
        progressLabel.getStyleClass().setAll("lbl","lbl-primary");

    }
}
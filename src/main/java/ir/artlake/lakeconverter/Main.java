package ir.artlake.lakeconverter;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.File;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static ExecutorService executorService;

    @Override
    public void start(Stage stage) throws IOException {
        //Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1100, 700);

        stage.setTitle("LakeConverter");
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.xProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() < screenBounds.getMinX()) {
                stage.setX(screenBounds.getMinX());
            } else if (newVal.doubleValue() > screenBounds.getMaxX() - stage.getWidth()) {
                stage.setX(screenBounds.getMaxX() - stage.getWidth());
            }
        });

        stage.yProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() < screenBounds.getMinY()) {
                stage.setY(screenBounds.getMinY());
            } else if (newVal.doubleValue() > screenBounds.getMaxY() - stage.getHeight()) {
                stage.setY(screenBounds.getMaxY() - stage.getHeight());
            }
        });

        stage.show();

        stage.setOnCloseRequest(event -> {
            executorService.shutdownNow(); // Shutdown the ExecutorService
        });

    }
    public static void main(String[] args) {
        //System.setProperty("prism.order", "sw");
        executorService= Executors.newFixedThreadPool(10);
        launch();

    }
}

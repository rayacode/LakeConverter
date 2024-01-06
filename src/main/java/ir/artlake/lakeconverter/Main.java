package ir.artlake.lakeconverter;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.File;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static ExecutorService executorService;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1100, 700);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("LakeConverter");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            executorService.shutdown(); // Shutdown the ExecutorService
        });

    }
    public static void main(String[] args) {
        executorService= Executors.newFixedThreadPool(10);
        launch();

    }
}

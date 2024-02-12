package ir.artlake.lakeconverter;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    Logger LOG = LoggerFactory.getLogger(Main.class);
    public static ExecutorService executorService;
    public static FXMLLoader mainControllerFxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        //Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        mainControllerFxmlLoader= new FXMLLoader(Main.class.getResource("main/main.fxml"));

        Scene scene = new Scene(mainControllerFxmlLoader.load(), 1100, 700);

        stage.setTitle("LakeConverter");
        stage.setScene(scene);
        stage.setResizable(false);
        ScreenUtils.lockEdges(stage);

        stage.show();

        stage.setOnCloseRequest(event -> {
            executorService.shutdownNow(); // Shutdown the ExecutorService
        });

    }
    public static void main(String[] args) {
        Thread.UncaughtExceptionHandler h = (th, ex) -> {
            System.out.println("Uncaught exception: " + ex);
        };
        ThreadFactory factory = r -> {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler(h);
            return t;
        };
        //System.setProperty("prism.order", "sw");
        executorService= Executors.newCachedThreadPool( );
        launch();

    }
}

/*  LakeConverter: A java Gui wrapper with jave for ffmpeg
 *  Copyright (C) 2023 Mohammad Ali Solhjoo mohammadalisolhjoo@live.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package codes.rayacode.lakeconverter;


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
        
        mainControllerFxmlLoader= new FXMLLoader(Main.class.getResource("main/main.fxml"));

        Scene scene = new Scene(mainControllerFxmlLoader.load(), 1100, 700);

        stage.setTitle("LakeConverter");
        stage.setScene(scene);
        stage.setResizable(false);
        ScreenUtils.lockEdges(stage);

        stage.show();

        stage.setOnCloseRequest(event -> {
            executorService.shutdownNow();
            stage.close();
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
        
        executorService= Executors.newCachedThreadPool( );
        launch();

    }
}
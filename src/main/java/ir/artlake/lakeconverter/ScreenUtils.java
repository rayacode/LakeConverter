package ir.artlake.lakeconverter;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenUtils {
    public static void lockEdges(Stage stage) {
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
    }

}

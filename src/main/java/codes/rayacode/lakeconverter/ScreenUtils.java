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
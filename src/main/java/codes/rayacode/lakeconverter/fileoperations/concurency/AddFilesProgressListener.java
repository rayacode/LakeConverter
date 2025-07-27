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

package codes.rayacode.lakeconverter.fileoperations.concurency;

import javafx.application.Platform;

import java.util.function.BiConsumer;

public class AddFilesProgressListener {
    private final BiConsumer<Double, Double> progressConsumer;

    public AddFilesProgressListener(BiConsumer<Double, Double> progressConsumer){
        this.progressConsumer = progressConsumer;
    }

    public void progress(int p,int total) {
        double progress = (double)p / total;
        System.out.println("Progress: " + progress);
        Platform.runLater(() -> {
            progressConsumer.accept(progress, 1.0);
        });
    }
}
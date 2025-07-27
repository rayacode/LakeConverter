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

package codes.rayacode.lakeconverter.conversion;

import javafx.application.Platform;
import codes.rayacode.lakeconverter.jave.info.MultimediaInfo;
import codes.rayacode.lakeconverter.jave.progress.EncoderProgressListener;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConvertProgressListener implements EncoderProgressListener {


    private BiConsumer<Double, Double> progressConsumer;

    public ConvertProgressListener(){

    }
    public ConvertProgressListener(BiConsumer<Double, Double> progressConsumer) {
        this.progressConsumer = progressConsumer;
    }

    public void progress(int p) {
        double progress = p / 1000.0;
        Platform.runLater(() -> {
            progressConsumer.accept(progress, 1.0);
        });
    }

    public void message(String m) {}
    public void sourceInfo(MultimediaInfo m) {}
    public BiConsumer<Double, Double> getProgressConsumer() {
        return progressConsumer;
    }

    public void setProgressConsumer(BiConsumer<Double, Double> progressConsumer) {
        this.progressConsumer = progressConsumer;
    }
}
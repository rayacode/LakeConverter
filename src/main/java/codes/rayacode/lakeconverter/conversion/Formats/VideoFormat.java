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

package codes.rayacode.lakeconverter.conversion.Formats;

import codes.rayacode.lakeconverter.jave.info.VideoSize;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public abstract class VideoFormat implements Format {
    public abstract ObservableList<String> getVideoEncoders();
    public abstract ObservableList<String> getAudioEncoders();
    public abstract ObservableList<Integer> getBitRates();
    public abstract ObservableList<Integer> getFrameRates();
    public abstract ObservableList<VideoSize> getVideoResolutions();
    public abstract Map<String, List<String>> getEncoderPixelFormats();
}
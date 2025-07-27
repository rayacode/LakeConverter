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

import javafx.beans.value.ObservableValue;
import codes.rayacode.lakeconverter.jave.encode.AudioAttributes;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.encode.VideoAttributes;

public class AAC implements Format{
    AudioAttributes audioAttributes;
    VideoAttributes videoAttributes;
    EncodingAttributes encodingAttributes;
    @Override
    public String getFileExtension() {
        return ".aac";
    }

    @Override
    public AudioAttributes getAudioAttributes() {
        return audioAttributes;
    }

    @Override
    public VideoAttributes getVideoAttributes() {
        return videoAttributes;
    }

    @Override
    public EncodingAttributes getEncodingAttributes() {
        return encodingAttributes;
    }

    @Override
    public void setDefault() {
        audioAttributes.setCodec("aac");
        audioAttributes.setBitRate(128000);
        audioAttributes.setSamplingRate(44100);
        audioAttributes.setChannels(2);


    }

    @Override
    public String getCurrentConfigForTextButton() {
        return null;
    }


}
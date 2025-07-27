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

package codes.rayacode.lakeconverter.fileoperations;

import codes.rayacode.lakeconverter.Main;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import codes.rayacode.lakeconverter.jave.MultimediaObject;
import codes.rayacode.lakeconverter.jave.ScreenExtractor;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ThumbnailGenerator {

    public String generateThumbnail(String mediaPath) throws Exception {


        ScreenExtractor screenExtractor = new ScreenExtractor();



        
        MultimediaObject src = new MultimediaObject(new File(mediaPath));

        
        Path thumbnailPath = Files.createTempFile("thumbnail", ".png");

        
        try {
            if (mediaPath.endsWith(".mp3") || mediaPath.endsWith(".wav") /* add other audio formats here */) {
                AudioFile audioFile = AudioFileIO.read(new File(mediaPath));
                Tag tag = audioFile.getTag();
                Artwork artwork = tag.getFirstArtwork();
                if (artwork != null) {
                    byte[] imageData = artwork.getBinaryData();
                    
                    if (imageData != null && imageData.length > 0) {
                        FileUtils.createDirectory(thumbnailPath.toFile().getParent() + "\\lakeConverter");
                        String fileName = thumbnailPath.toFile().getName();
                        thumbnailPath = Paths.get(thumbnailPath.toFile().getParent() + "\\lakeConverter\\" + fileName);
                        FileUtils.createDirectory(thumbnailPath.toFile().getParent() + "\\thumbnails");
                        thumbnailPath = Paths.get(thumbnailPath.toFile().getParent() + "\\thumbnails\\" + fileName);
                        Files.write(thumbnailPath, imageData);
                    } else {
                        
                        return Main.class.getResource("defaultAduio.jpg").toURI().toURL().toString();
                    }
                } else {
                    
                    return Main.class.getResource("defaultAduio.jpg").toURI().toURL().toString();
                }
            } else {
                
                
                screenExtractor.renderOneImage(src,1080,1080,1000,thumbnailPath.toFile(),1);
            }
        }catch (InvalidAudioFrameException e){
            System.out.println("InvalidAudioFrameException in ThumbnailGenerator.generateThumbnail()" + e.getMessage());
            return Main.class.getResource("defaultAduio.jpg").toURI().toURL().toString();
        }

        return thumbnailPath.toUri().toURL().toString();

    }

    private boolean isFileSizeBetween0And1KB(byte[] imageData) {

        int sizeInBytes = imageData.length;
        double sizeInKB = sizeInBytes / 1024.0;
        return sizeInKB > 0 && sizeInKB <= 1;
    }

}
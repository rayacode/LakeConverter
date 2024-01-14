package ir.artlake.lakeconverter;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.ScreenExtractor;
import ws.schild.jave.encode.EncodingAttributes;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ThumbnailGenerator {

    public String generateThumbnail(String mediaPath) throws Exception {


        ScreenExtractor screenExtractor = new ScreenExtractor();



        // Create the input source from the filename
        MultimediaObject src = new MultimediaObject(new File(mediaPath));

        // Create a temporary file for the thumbnail
        Path thumbnailPath = Files.createTempFile("thumbnail", ".png");

        // Check if the media file is an audio file
        try {
            if (mediaPath.endsWith(".mp3") || mediaPath.endsWith(".wav") /* add other audio formats here */) {
                AudioFile audioFile = AudioFileIO.read(new File(mediaPath));
                Tag tag = audioFile.getTag();
                Artwork artwork = tag.getFirstArtwork();
                if (artwork != null) {
                    byte[] imageData = artwork.getBinaryData();
                    // Check if imageData is valid
                    if (imageData != null && imageData.length > 0) {
                        FileUtils.createDirectory(thumbnailPath.toFile().getParent() + "\\lakeConverter");
                        String fileName = thumbnailPath.toFile().getName();
                        thumbnailPath = Paths.get(thumbnailPath.toFile().getParent() + "\\lakeConverter\\" + fileName);
                        FileUtils.createDirectory(thumbnailPath.toFile().getParent() + "\\thumbnails");
                        thumbnailPath = Paths.get(thumbnailPath.toFile().getParent() + "\\thumbnails\\" + fileName);
                        Files.write(thumbnailPath, imageData);
                    } else {
                        // Use a default image
                        return getClass().getResource("defaultAduio.jpg").toURI().toURL().toString();
                    }
                } else {
                    // Use a default image
                    return getClass().getResource("defaultAduio.jpg").toURI().toURL().toString();
                }
            } else {
                // Encode and save the image
                //encoder.encode(src, thumbnailPath.toFile(), attrs);
                screenExtractor.renderOneImage(src,1080,1080,1000,thumbnailPath.toFile(),1);
            }
        }catch (InvalidAudioFrameException e){
            System.out.println("InvalidAudioFrameException in ThumbnailGenerator.generateThumbnail()" + e.getMessage());
            return getClass().getResource("defaultAduio.jpg").toURI().toURL().toString();
        }

        return thumbnailPath.toUri().toURL().toString();

    }

    private boolean isFileSizeBetween0And1KB(byte[] imageData) {

        int sizeInBytes = imageData.length;
        double sizeInKB = sizeInBytes / 1024.0;
        return sizeInKB > 0 && sizeInKB <= 1;
    }

}

package ir.artlake.lakeconverter;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThumbnailGenerator {
    public Path generateThumbnail(String videoPath) throws Exception {
        // Create a new encoder object
        Encoder encoder = new Encoder();

        // Create the encoding attributes
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("image2");
        attrs.setOffset(1f); // offset for the frame to extract in seconds

        // Create the input source from the filename
        MultimediaObject src = new MultimediaObject(new File(videoPath));

        // Create a temporary file for the thumbnail
        Path thumbnailPath = Files.createTempFile("thumbnail", ".png");

        // Encode and save the image
        encoder.encode(src, thumbnailPath.toFile(), attrs);

        return thumbnailPath;
    }
}

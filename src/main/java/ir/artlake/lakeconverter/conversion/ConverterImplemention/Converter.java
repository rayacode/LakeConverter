package ir.artlake.lakeconverter.conversion.ConverterImplemention;

import ir.artlake.lakeconverter.conversion.ConvertProgressListener;
import ir.artlake.lakeconverter.conversion.Formats.Format;
import org.jetbrains.annotations.NotNull;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;

import java.io.File;
import java.util.Arrays;

public class Converter<S extends Format, T extends Format> {
    private S sourceFormat;
    private T targetFormat;
    @NotNull
    File source;
    @NotNull
    File target;
    ConvertProgressListener listener;
    private Encoder encoder = new Encoder();

    public Converter(ConvertProgressListener listener, String source, String target, S sourceFormat, T targetFormat){
        this.listener = listener;
        this.source = new File(source);
        String sourceName = this.source.getName();
        String baseName = sourceName.substring(0, sourceName.lastIndexOf('.'));
        this.target = new File(target, baseName + targetFormat.getFileExtension());

    }



    public Boolean convert(){
        try {


            //Audio Attributes
            AudioAttributes audio = targetFormat.getAudioAttributes();



            VideoAttributes video = targetFormat.getVideoAttributes();

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat(targetFormat.getFileExtension());
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);

            if (source != null) {
                System.out.println(source.getAbsolutePath());
            } else if (target != null) {
                System.out.println("I'm target"+target.getAbsolutePath());
            } else if (attrs != null) {
                System.out.println("i'm attr" + attrs.toString());
            } else if (listener != null) {
                System.out.println("im listener " + listener.toString());
            }
            int counter = 0;
            String path = target.getPath();
            String base = path.substring(0, path.lastIndexOf("."));
            String extension = path.substring(path.lastIndexOf("."));

            while (target.exists()) {
                counter++;
                target = new File(base + "_" + counter + extension);
            }
            encoder.encode(new MultimediaObject(source), target, attrs, listener);

            return true;

        } catch (EncoderException ex){
            ex.printStackTrace();
            return false;
        }

    }
}
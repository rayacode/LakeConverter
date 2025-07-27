package codes.rayacode.lakeconverter.conversion.ConverterImplemention;

import codes.rayacode.lakeconverter.conversion.ConvertProgressListener;
import codes.rayacode.lakeconverter.conversion.Formats.Format;
import org.jetbrains.annotations.NotNull;
import codes.rayacode.lakeconverter.jave.Encoder;
import codes.rayacode.lakeconverter.jave.EncoderException;
import codes.rayacode.lakeconverter.jave.InputFormatException;
import codes.rayacode.lakeconverter.jave.MultimediaObject;
import codes.rayacode.lakeconverter.jave.encode.AudioAttributes;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.encode.VideoAttributes;
import codes.rayacode.lakeconverter.jave.encode.enums.X264_PROFILE;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Converter<T extends Format> {
    
    private T targetFormat;
    @NotNull
    File source;
    @NotNull
    File target;
    ConvertProgressListener listener;
    private Encoder encoder = new Encoder();

    public Converter(ConvertProgressListener listener, String source, String target, T targetFormat){
        this.listener = listener;
        this.source = new File(source);
        String sourceName = this.source.getName();
        String baseName = sourceName.substring(0, sourceName.lastIndexOf('.'));
        this.target = new File(target, baseName + targetFormat.getFileExtension());
        this.targetFormat = targetFormat;
    }



    public Boolean convert(){
        try {


            


            EncodingAttributes attrs = targetFormat.getEncodingAttributes();




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

        } catch (IllegalArgumentException  | EncoderException ex){
            System.out.print("I through from here!");
            ex.printStackTrace();


            return false;
        }

    }

    public Encoder getEncoder() {
        return encoder;
    }

    public T getTargetFormat() {
        return targetFormat;
    }

    public void setTargetFormat(T targetFormat) {
        this.targetFormat = targetFormat;
    }
}
package ir.artlake.lakeconverter;


import org.jetbrains.annotations.NotNull;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;

import java.io.File;
import java.util.Arrays;

public class Converter {
    @NotNull
    File source;
    @NotNull
    File target;
    ConvertProgressListener listener;
    private Encoder encoder;

    public Converter(ConvertProgressListener listener, String source, String target){
        this.listener = listener;
        this.source = new File(source);
        String sourceName = this.source.getName();
        String baseName = sourceName.substring(0, sourceName.lastIndexOf('.'));
        this.target = new File(target, baseName + ".mp3");

    }

    public Boolean convert(){
        try {


            //Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            //Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(audio);
            if (source != null) {
                System.out.println(source.getAbsolutePath());
            } else if (target != null) {
                System.out.println("I'm target"+target.getAbsolutePath());
            } else if (attrs != null) {
                System.out.println("i'm attr" + attrs.toString());
            } else if (listener != null) {
                System.out.println("im listener " + listener.toString());
            }
            //Encode

                encoder = new Encoder();
            int counter = 0;
            while (target.exists()) {
                counter++;
                target = new File(target.getPath() + "_" + counter  );
            }
                encoder.encode(new MultimediaObject(source), target, attrs, listener);

            return true;

        } catch (EncoderException ex){
            //ex.printStackTrace();
            return false;
        }
    }
    public Boolean convertVideo(){
        try {


            //Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");
// here 64kbit/s is 64000
            audio.setBitRate(64000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);


            VideoAttributes video = new VideoAttributes();
            video.setCodec("h264");
            video.setX264Profile(X264_PROFILE.BASELINE);
// Here 160 kbps video is 160000
            //video.setBitRate(160000);
// More the frames more quality and size, but keep it low based on devices like mobile
            //video.setFrameRate(15);
            //video.setSize(new VideoSize(400, 300));


            //Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
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
            //Encode

            encoder = new Encoder();
            int counter = 0;
            while (target.exists()) {
                counter++;
                target = new File(target.getPath() + "_" + counter  );
            }
            System.out.println("getVideoEncoders:\n " + Arrays.toString(encoder.getSupportedEncodingFormats()));
            System.out.println("getSupportedEncodingFormats:\n " + Arrays.toString(encoder.getSupportedEncodingFormats()));
            System.out.println("getVideoDecoders:\n " + Arrays.toString(encoder.getVideoDecoders()));
            System.out.println("getSupportedDecodingFormats:\n " + Arrays.toString(encoder.getSupportedDecodingFormats()));
            System.out.println("AudioEncoders:\n " + Arrays.toString(encoder.getAudioEncoders()));
            System.out.println("getAudioDecoders:\n " + Arrays.toString(encoder.getAudioDecoders()));
            attrs.setDecodingThreads(4);
            attrs.setEncodingThreads(4);
            encoder.encode(new MultimediaObject(source), target, attrs, listener);

            return true;

        } catch (EncoderException ex){
            //ex.printStackTrace();
            return false;
        }

    }

    public Encoder getEncoder(){
         return encoder;
    }
}
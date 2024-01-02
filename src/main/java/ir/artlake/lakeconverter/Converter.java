package ir.artlake.lakeconverter;


import org.jetbrains.annotations.NotNull;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class Converter {
    @NotNull
    File source;
    @NotNull
    File target;
    ConvertProgressListener listener;

    public Converter(ConvertProgressListener listener, String source, String target){
        this.listener = listener;
        this.source = new File(source);
        this.target = new File(target  + this.source.getName() + ".mp3");

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
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject( source), target , attrs, listener);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }
}
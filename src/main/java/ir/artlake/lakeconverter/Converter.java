package ir.artlake.lakeconverter;


import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class Converter {
    ConvertProgressListener listener;

    public Converter(ConvertProgressListener listener){
        this.listener = listener;
    }

    public Boolean convert(){
        try {
            File source = new File("30sal.mp3");
            File target = new File("30sal");

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

            //Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs, listener);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
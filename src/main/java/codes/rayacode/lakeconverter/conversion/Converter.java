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

package codes.rayacode.lakeconverter.conversion;


import org.jetbrains.annotations.NotNull;
import codes.rayacode.lakeconverter.jave.Encoder;
import codes.rayacode.lakeconverter.jave.EncoderException;
import codes.rayacode.lakeconverter.jave.MultimediaObject;
import codes.rayacode.lakeconverter.jave.encode.AudioAttributes;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.encode.VideoAttributes;
import codes.rayacode.lakeconverter.jave.encode.enums.X264_PROFILE;
import codes.rayacode.lakeconverter.jave.process.ProcessWrapper;

import java.io.File;
import java.util.Arrays;

public class Converter {
    @NotNull
    File source;
    @NotNull
    File target;
    ConvertProgressListener listener;
    private Encoder encoder = new Encoder();

    public Converter(ConvertProgressListener listener, String source, String target){
        this.listener = listener;
        this.source = new File(source);
        String sourceName = this.source.getName();
        String baseName = sourceName.substring(0, sourceName.lastIndexOf('.'));
        this.target = new File(target, baseName + ".mkv");

    }

    public Boolean convert(){
        try {


            
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            
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
            

                encoder = new Encoder();
            int counter = 0;
            while (target.exists()) {
                counter++;
                target = new File(target.getPath() + "_" + counter  );
            }



                encoder.encode(new MultimediaObject(source), target, attrs, listener);

            return true;

        } catch (EncoderException ex){
            
            return false;
        }
    }
    public Boolean convertVideo(){
        try {


            
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");

            audio.setBitRate(64000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);


            VideoAttributes video = new VideoAttributes();
            video.setCodec("hevc");



            

            
            


            
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("matroska");
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
            
            return false;
        }

    }

    public Encoder getEncoder(){
        if(encoder != null) {
            return encoder;
        }else return null;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }
}
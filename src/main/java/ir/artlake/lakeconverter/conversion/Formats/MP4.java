package ir.artlake.lakeconverter.conversion.Formats;

import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.VideoAttributes;

public class MP4 implements Format{
    AudioAttributes audioAttributes;
    VideoAttributes videoAttributes;

    public MP4(){

    }
    public MP4(VideoAttributes videoAttributes, AudioAttributes audioAttributes){
        this.videoAttributes = videoAttributes;
        this.audioAttributes = audioAttributes;

    }


    @Override
    public String getFileExtension() {

        return "mp4";
    }

    @Override
    public AudioAttributes getAudioAttributes() {
        return audioAttributes;
    }

    @Override
    public VideoAttributes getVideoAttributes() {
        return videoAttributes;
    }
}

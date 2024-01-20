package ir.artlake.lakeconverter.conversion.Formats;

import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;
import ws.schild.jave.info.VideoSize;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MP4 extends VideoFormat{
    AudioAttributes audioAttributes;
    VideoAttributes videoAttributes;
    EncodingAttributes encodingAttributes;


    //audio encoders can be used in mp4 container
    public static String AC_3= "ac3";
    public static String AC_3_FIXED= "ac3_fixed";
    public static String AAC = "aac";
    public static String APPLE_LOSSLESS="alac";
    public static String MP2 = "mp2";
    public static String MP2_FIXED = "mp2fixed";
    public static String MP3 = "libmp3lame";

    //video encoders can be used in mp4 container
    public static String AV1="libaom-av1";
    public static String MPEG4="mpeg4";
    //cfhd not supported yet
    public static String CFHD="cfhd";
    //h264 is alias to libx264
    public static String H264 ="h264";
    public static String LIBX264 ="libx264";
    //libx264rgb doesn't support x264 profile
    public static String LIBX264RGB ="libx264rgb";
    //h264_amf to access AMD gpu, (windows only)
    public static String H264_AMF ="h264_amf";
    //h264_nvenc use nvidia gpu cards (work with windows and linux)
    public static String H264_NVENC ="h264_nvenc";
    //h264_qsv use Intel Quick Sync Video (hardware embedded in modern Intel CPU)
    public static String H264_QSV ="h264_qsv";
    //vp9
    public static String LIBVPX_VP9="libvpx-vp9";


    //hevc encoders
    //alias to libx265
    public static String HEVC = "hevc";
    public static String LIBX265 ="libx265";

    //HEVC_amf to access AMD gpu, (windows only)
    public static String HEVC_AMF ="hevc_amf";
    //HEVC_nvenc use nvidia gpu cards (work with windows and linux)
    public static String HEVC_NVENC ="hevc_nvenc";
    //HEVC_qsv use Intel Quick Sync Video (hardware embedded in modern Intel CPU)
    public static String HEVC_QSV ="hevc_qsv";


    //encoders and equivalent pixelformats
    Map<String, List<String>> encoderPixelFormats = new HashMap<>();




    public MP4(){
        encoderPixelFormats.put(MP4.AV1, Arrays.asList("yuv420p", "yuv422p", "yuv444p", "gbrp", "yuv420p10le", "yuv422p10le", "yuv444p10le", "yuv420p12le", "yuv422p12le", "yuv444p12le", "gbrp10le", "gbrp12le", "gray", "gray10le", "gray12le"));
        encoderPixelFormats.put(MP4.MPEG4, Arrays.asList("yuv420p"));
        encoderPixelFormats.put(MP4.LIBX264RGB, Arrays.asList("bgr0", "bgr24", "rgb24"));
        encoderPixelFormats.put(MP4.LIBX264, Arrays.asList("yuv420p", "yuvj420p", "yuv422p", "yuvj422p", "yuv444p", "yuvj444p", "nv12", "nv16", "nv21", "yuv420p10le", "yuv422p10le", "yuv444p10le", "nv20le", "gray", "gray10le"));
        encoderPixelFormats.put(MP4.H264_AMF, Arrays.asList("nv12", "yuv420p", "d3d11", "dxva2_vld"));
        encoderPixelFormats.put(MP4.H264_NVENC, Arrays.asList("yuv420p", "nv12", "p010le", "yuv444p", "p016le", "yuv444p16le", "bgr0", "rgb0", "cuda", "d3d11"));
        encoderPixelFormats.put(MP4.H264_QSV, Arrays.asList("nv12", "p010le", "qsv"));
        encoderPixelFormats.put(MP4.LIBVPX_VP9, Arrays.asList("yuv420p", "yuva420p", "yuv422p", "yuv440p", "yuv444p", "yuv420p10le", "yuv422p10le", "yuv440p10le", "yuv444p10le", "yuv420p12le", "yuv422p12le", "yuv440p12le", "yuv444p12le", "gbrp", "gbrp10le", "gbrp12le"));
        encoderPixelFormats.put(MP4.LIBX265, Arrays.asList("yuv420p", "yuvj420p", "yuv422p", "yuvj422p", "yuv444p", "yuvj444p", "gbrp", "yuv420p10le", "yuv422p10le", "yuv444p10le", "gbrp10le", "yuv420p12le", "yuv422p12le", "yuv444p12le", "gbrp12le", "gray", "gray10le", "gray12le"));
        encoderPixelFormats.put(MP4.HEVC_AMF, Arrays.asList("nv12", "yuv420p", "d3d11", "dxva2_vld"));
        encoderPixelFormats.put(MP4.HEVC_NVENC, Arrays.asList("yuv420p", "nv12", "p010le", "yuv444p", "p016le", "yuv444p16le", "bgr0", "rgb0", "cuda", "d3d11"));
        encoderPixelFormats.put(MP4.HEVC_QSV, Arrays.asList("nv12", "p010le", "qsv"));
    }
    public MP4(VideoAttributes videoAttributes,
               AudioAttributes audioAttributes, EncodingAttributes encodingAttributes){
        this.videoAttributes = videoAttributes;
        this.audioAttributes = audioAttributes;
        this.encodingAttributes = encodingAttributes;


    }

    public void setH264Based(){}
    public void setNoneh264Based(){}
    @Override
    public String getFileExtension() {

        return ".mp4";
    }

    @Override
    public AudioAttributes getAudioAttributes() {
        return audioAttributes;
    }

    @Override
    public VideoAttributes getVideoAttributes() {
        return videoAttributes;
    }
    @Override
    public EncodingAttributes getEncodingAttributes(){
        return encodingAttributes;
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        this.audioAttributes = audioAttributes;
    }

    public void setVideoAttributes(VideoAttributes videoAttributes) {

        this.videoAttributes = videoAttributes;
    }

    public void setEncodingAttributes(EncodingAttributes encodingAttributes) {
        this.encodingAttributes = encodingAttributes;
    }


    public static MP4 defaultMP4(){
        AudioAttributes audioAttributes = new AudioAttributes();
        audioAttributes.setCodec(MP4.AAC);
        audioAttributes.setChannels(2);
        audioAttributes.setSamplingRate(44100);
        audioAttributes.setBitRate(128000);
        VideoAttributes videoAttributes = new VideoAttributes();
        videoAttributes.setCodec(MP4.HEVC_QSV);
        videoAttributes.setCrf(1);
        //videoAttributes.setX264Profile(X264_PROFILE.MAIN);
        videoAttributes.setFrameRate(30);
        videoAttributes.setSize(VideoSize.hd1080);
        videoAttributes.setBitRate(10000);
        videoAttributes.setPixelFormat("nv12");

        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("mp4");
        encodingAttributes.setVideoAttributes(videoAttributes);
        encodingAttributes.setAudioAttributes(audioAttributes);


        return new MP4(videoAttributes, audioAttributes, encodingAttributes);
    }
}

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

package codes.rayacode.lakeconverter.conversion.Formats;

import codes.rayacode.lakeconverter.jave.encode.enums.PresetEnum;
import codes.rayacode.lakeconverter.jave.encode.enums.TuneEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import codes.rayacode.lakeconverter.jave.encode.AudioAttributes;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.encode.VideoAttributes;
import codes.rayacode.lakeconverter.jave.encode.enums.X264_PROFILE;
import codes.rayacode.lakeconverter.jave.info.VideoSize;

import java.util.*;

public class MP4 extends codes.rayacode.lakeconverter.conversion.Formats.VideoFormat {
    AudioAttributes audioAttributes;
    VideoAttributes videoAttributes;
    EncodingAttributes encodingAttributes;

    
    public static String formatContainerName = "mp4";

    
    public static String AC_3= "ac3";
    public static String AC_3_FIXED= "ac3_fixed";
    public static String AAC = "aac";
    public static String APPLE_LOSSLESS="alac";
    public static String MP2 = "mp2";
    public static String MP2_FIXED = "mp2fixed";
    public static String MP3 = "libmp3lame";

    
    public static String AV1="libaom-av1";
    public static String MPEG4="mpeg4";
    
    public static String CFHD="cfhd";
    
    public static String H264 ="h264";
    public static String LIBX264 ="libx264";
    
    public static String LIBX264RGB ="libx264rgb";
    
    public static String H264_AMF ="h264_amf";
    
    public static String H264_NVENC ="h264_nvenc";
    
    public static String H264_QSV ="h264_qsv";
    
    public static String LIBVPX_VP9="libvpx-vp9";


    
    
    public static String HEVC = "hevc";
    public static String LIBX265 ="libx265";

    
    public static String HEVC_AMF ="hevc_amf";
    
    public static String HEVC_NVENC ="hevc_nvenc";
    
    public static String HEVC_QSV ="hevc_qsv";


    private static Map<String, String> minimalSettingMap= new LinkedHashMap<>();
    public static Map<String, String> getMinimalSettingMap(){
        minimalSettingMap.put("8K", "MP4 8K 7680*4320");
        minimalSettingMap.put("4K", "MP4 4K 4096*2160");
        minimalSettingMap.put("UHD2160", "MP4 UHD2160 3840*2160");
        minimalSettingMap.put("1080p", "MP4 1080p 1920*1080");
        minimalSettingMap.put("720p", "MP4 720p 1280*720");
        minimalSettingMap.put("640p", "MP4 640p 960*640");
        minimalSettingMap.put("480p", "MP4 480p 720*480");
        return minimalSettingMap;
    }

    public static ObservableList<TuneEnum> tunes = FXCollections.observableArrayList(
            TuneEnum.FILM,TuneEnum.ANIMATION,TuneEnum.GRAIN,
            TuneEnum.STILLIMAGE,TuneEnum.FASTDECODE,TuneEnum.ZEROLATENCY,
            TuneEnum.PSNR,TuneEnum.SSIM
    );
    public static ObservableList<String> videoEncoders = FXCollections.observableList(Arrays.asList(AV1, MPEG4,CFHD,
            LIBX264, LIBX264RGB, H264_AMF, H264_NVENC, H264_QSV,
            LIBX265, HEVC_AMF, HEVC_NVENC, HEVC_QSV,
            LIBVPX_VP9));
    public static ObservableList<String> audioEncoders = FXCollections.observableList(Arrays.asList(AC_3,
            AC_3_FIXED, AAC, APPLE_LOSSLESS,
            MP2, MP2_FIXED, MP3));
    public static ObservableList<VideoSize> videoResolutions = FXCollections.observableArrayList(VideoSize.ntsc
            ,VideoSize.pal
            ,VideoSize.qntsc ,VideoSize.qpal ,VideoSize.sntsc ,VideoSize.spal
            ,VideoSize.film ,VideoSize.ntsc_film ,VideoSize.sqcif ,VideoSize.qcif
            ,VideoSize.cif ,VideoSize.FOUR_cif ,VideoSize.SIXTEEN_cif ,VideoSize.qqvga
            ,VideoSize.qvga ,VideoSize.vga ,VideoSize.svga ,VideoSize.xga
            ,VideoSize.uxga ,VideoSize.qxga ,VideoSize.sxga ,VideoSize.qsxga
            ,VideoSize.hsxga ,VideoSize.wvga ,VideoSize.wxga ,VideoSize.wsxga
            ,VideoSize.wuxga ,VideoSize.woxga ,VideoSize.wqsxga ,VideoSize.wquxga
            ,VideoSize.whsxga ,VideoSize.whuxga ,VideoSize.cga ,VideoSize.ega
            ,VideoSize.hd480 ,VideoSize.hd720 ,VideoSize.hd1080 ,VideoSize.TWOk
            ,VideoSize.TWOkflat ,VideoSize.TWOkscope ,VideoSize.FOURk ,VideoSize.FOURkflat
            ,VideoSize.FOURkscope ,VideoSize.nhd ,VideoSize.hqvga
            ,VideoSize.wqvga ,VideoSize.fwqvga ,VideoSize.hvga ,VideoSize.qhd
            ,VideoSize.TWOkdci ,VideoSize.FOURkdci ,VideoSize.uhd2160 ,VideoSize.uhd4320);
    public static ObservableList<Integer> bitRates =
            FXCollections.observableArrayList(
                    512,768,900,1000,1200,1500,2000,
                    3000,4000,6000,8000,10000,15000,20000,30000);
    public static ObservableList<String> presets= FXCollections.observableArrayList("Default",
            PresetEnum.ULTRAFAST.getPresetName(),PresetEnum.SUPERFAST.getPresetName(),
            PresetEnum.VERYFAST.getPresetName(),PresetEnum.FASTER.getPresetName(),
            PresetEnum.FAST.getPresetName(),PresetEnum.MEDIUM.getPresetName(),
            PresetEnum.SLOW.getPresetName(),PresetEnum.SLOWER.getPresetName(),
            PresetEnum.VERYSLOW.getPresetName());
    public static ObservableList<Integer> crfValues = FXCollections.observableArrayList(
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
            17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,
            34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51);
    public static ObservableList<Integer> frameRates = FXCollections.observableArrayList(
            12,15,20,24,25,30,50,60,80,100,
            120,140,160,180,200,220,240);
    public static ObservableList<X264_PROFILE> profiles =FXCollections.observableArrayList(
            X264_PROFILE.DEFAULT,
            X264_PROFILE.BASELINE,X264_PROFILE.MAIN,
            X264_PROFILE.HIGH, X264_PROFILE.HIGH10,
            X264_PROFILE.HIGH422, X264_PROFILE.HIGH444);
    
    Map<String, List<String>> encoderPixelFormats = new HashMap<>();


    public static ObservableList<String> AV1Pixels = FXCollections.observableArrayList(
            "yuv420p", "yuv422p", "yuv444p", "gbrp",
            "yuv420p10le", "yuv422p10le", "yuv444p10le", "yuv420p12le",
            "yuv422p12le", "yuv444p12le", "gbrp10le", "gbrp12le", "gray",
            "gray10le", "gray12le");
    public static ObservableList<String> mpeg4Pixels = FXCollections.observableArrayList(
            "yuv420p");
    public static ObservableList<String> LIBX264RGBPixels = FXCollections.observableArrayList(
            "bgr0", "bgr24", "rgb24");
    public static ObservableList<String> LIBX264Pixels = FXCollections.observableArrayList(
            "yuv420p", "yuvj420p", "yuv422p", "yuvj422p", "yuv444p", "yuvj444p", "nv12",
            "nv16", "nv21", "yuv420p10le", "yuv422p10le", "yuv444p10le", "nv20le", "gray", "gray10le");
    public static ObservableList<String> H264_AMFPixels = FXCollections.observableArrayList(
            "nv12", "yuv420p", "d3d11", "dxva2_vld");
    public static ObservableList<String> H264_NVENCPixels = FXCollections.observableArrayList(
            "yuv420p", "nv12", "p010le", "yuv444p", "p016le",
            "yuv444p16le", "bgr0", "rgb0", "cuda", "d3d11");
    public static ObservableList<String> H264_QSVPixels = FXCollections.observableArrayList(
            "nv12", "qsv");
    public static ObservableList<String> LIBX265Pixels = FXCollections.observableArrayList(
            "yuv420p", "yuvj420p", "yuv422p", "yuvj422p",
            "yuv444p", "yuvj444p", "gbrp", "yuv420p10le", "yuv422p10le",
            "yuv444p10le", "gbrp10le", "yuv420p12le", "yuv422p12le",
            "yuv444p12le", "gbrp12le", "gray", "gray10le", "gray12le");
    public static ObservableList<String> HEVC_AMFPixels = FXCollections.observableArrayList(
            "nv12", "yuv420p", "d3d11", "dxva2_vld");
    public static ObservableList<String> HEVC_NVENCPixels = FXCollections.observableArrayList(
            "yuv420p", "nv12", "p010le", "yuv444p", "p016le", "yuv444p16le", "bgr0", "rgb0", "cuda", "d3d11");
    public static ObservableList<String> HEVC_QSVPixels = FXCollections.observableArrayList(
            "nv12", "p010le", "p012le", "yuyv422", "y210le", "qsv", "bgra", "x2rgb10le", "vuyx", "xv30le");
    public static ObservableList<String> LIBVPX_VP9Pixels = FXCollections.observableArrayList(
            "yuv420p", "yuva420p", "yuv422p", "yuv440p", "yuv444p",
            "yuv420p10le", "yuv422p10le", "yuv440p10le", "yuv444p10le",
            "yuv420p12le", "yuv422p12le", "yuv440p12le", "yuv444p12le", "gbrp", "gbrp10le", "gbrp12le");
    public static ObservableList<String> CFHDPixels = FXCollections.observableArrayList(
            "yuv422p10le", "gbrp12le", "gbrap12le");







    public MP4(){
        encoderPixelFormats.put(MP4.AV1, Arrays.asList("yuv420p", "yuv422p", "yuv444p", "gbrp", "yuv420p10le", "yuv422p10le", "yuv444p10le", "yuv420p12le", "yuv422p12le", "yuv444p12le", "gbrp10le", "gbrp12le", "gray", "gray10le", "gray12le"));
        encoderPixelFormats.put(MP4.MPEG4, Arrays.asList("yuv420p"));
        encoderPixelFormats.put(MP4.LIBX264RGB, Arrays.asList("bgr0", "bgr24", "rgb24"));
        encoderPixelFormats.put(MP4.LIBX264, Arrays.asList("yuv420p", "yuvj420p", "yuv422p", "yuvj422p", "yuv444p", "yuvj444p", "nv12", "nv16", "nv21", "yuv420p10le", "yuv422p10le", "yuv444p10le", "nv20le", "gray", "gray10le"));
        encoderPixelFormats.put(MP4.H264_AMF, Arrays.asList("nv12", "yuv420p", "d3d11", "dxva2_vld"));
        encoderPixelFormats.put(MP4.H264_NVENC, Arrays.asList("yuv420p", "nv12", "p010le", "yuv444p", "p016le", "yuv444p16le", "bgr0", "rgb0", "cuda", "d3d11"));
        encoderPixelFormats.put(MP4.H264_QSV, Arrays.asList("nv12", "qsv"));
        encoderPixelFormats.put(MP4.LIBVPX_VP9, Arrays.asList("yuv420p", "yuva420p", "yuv422p", "yuv440p", "yuv444p", "yuv420p10le", "yuv422p10le", "yuv440p10le", "yuv444p10le", "yuv420p12le", "yuv422p12le", "yuv440p12le", "yuv444p12le", "gbrp", "gbrp10le", "gbrp12le"));
        encoderPixelFormats.put(MP4.LIBX265, Arrays.asList("yuv420p", "yuvj420p", "yuv422p", "yuvj422p", "yuv444p", "yuvj444p", "gbrp", "yuv420p10le", "yuv422p10le", "yuv444p10le", "gbrp10le", "yuv420p12le", "yuv422p12le", "yuv444p12le", "gbrp12le", "gray", "gray10le", "gray12le"));
        encoderPixelFormats.put(MP4.HEVC_AMF, Arrays.asList("nv12", "yuv420p", "d3d11", "dxva2_vld"));
        encoderPixelFormats.put(MP4.HEVC_NVENC, Arrays.asList("yuv420p", "nv12", "p010le", "yuv444p", "p016le", "yuv444p16le", "bgr0", "rgb0", "cuda", "d3d11"));
        encoderPixelFormats.put(MP4.HEVC_QSV, Arrays.asList( "nv12" /*,"p010le", "p012le", "yuyv422", "y210le", "qsv", "bgra", "x2rgb10le", "vuyx", "xv30le"*/));
        encoderPixelFormats.put(MP4.CFHD, Arrays.asList( "yuv422p10le", "gbrp12le", "gbrap12le"));
        videoAttributes = new VideoAttributes();
        audioAttributes = new AudioAttributes();
        encodingAttributes = new EncodingAttributes();
        this.set720p();

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


    public  void setDefault(){
        set720p();
    }
    public  void set480p(){
        resCode="480p";

        setInitMp4(VideoSize.hd480);



    }
    public  void set640p(){
        resCode="640p";
        setInitMp4(new VideoSize(960, 640));



    }
    public  void set720p(){
        resCode="720p";

        setInitMp4(VideoSize.hd720);


    }
    public  void set1080p(){
        resCode="1080p";
        setInitMp4(VideoSize.hd1080);



    }
    public  void setUHD2160(){
        resCode="UHD2160";
        setInitMp4(VideoSize.uhd2160);


    }
    public void set4K(){
        resCode="4K";
        setInitMp4(VideoSize.FOURk);

    }

    public  void set8K(){
        resCode="8K";
        setInitMp4(VideoSize.uhd4320);
    }


    public void setInitMp4(VideoSize videoSize){
        audioAttributes.setCodec(MP4.AAC);
        audioAttributes.setChannels(2);
        audioAttributes.setSamplingRate(44100);
        audioAttributes.setBitRate(128000);
        videoAttributes.setCrf(23);
        videoAttributes.setCodec(MP4.LIBX264);

        videoAttributes.setX264Profile(X264_PROFILE.MAIN);
        videoAttributes.setBitRate(5000*1000);
        videoAttributes.setFrameRate(30);
        videoAttributes.setSize(videoSize);




        encodingAttributes.setOutputFormat("mp4");
        encodingAttributes.setVideoAttributes(videoAttributes);
        encodingAttributes.setAudioAttributes(audioAttributes);
        

    }
    private String resCode = "";



    public String getCurrentConfigForTextButton() {
        return "MP4 " + resCode;
    }

    @Override
    public ObservableList<String> getVideoEncoders() {
        return videoEncoders;
    }

    @Override
    public ObservableList<String> getAudioEncoders() {
        return audioEncoders;
    }

    @Override
    public ObservableList<Integer> getBitRates() {
        return bitRates;
    }

    @Override
    public ObservableList<Integer> getFrameRates() {
        return frameRates;
    }

    @Override
    public ObservableList<VideoSize> getVideoResolutions() {
        return videoResolutions;
    }

    @Override
    public Map<String, List<String>> getEncoderPixelFormats() {
        return encoderPixelFormats;
    }
}
package ir.artlake.lakeconverter.conversion.Formats;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MP4Attr {
    public static List<String> minimalSetting =
            Arrays.asList("MP4 8K 7680*4320",
                    "MP4 4K 3840*2160",
                    "MP4 1080p 1920*1080",
                    "MP4 720p 1280*720",
                    "MP4 640p 960*640",
                    "MP4 480p 720*480");

    private static Map<String, String> minimalSettingMap= new LinkedHashMap<>();
    public static Map<String, String> getMinimalSettingMap(){
        minimalSettingMap.put("8K", "MP4 8K 7680*4320");
        minimalSettingMap.put("4K", "MP4 4K 3840*2160");
        minimalSettingMap.put("1080p", "MP4 1080p 1920*1080");
        minimalSettingMap.put("720p", "MP4 720p 1280*720");
        minimalSettingMap.put("640p", "MP4 640p 960*640");
        minimalSettingMap.put("480p", "MP4 480p 720*480");
        return minimalSettingMap;
    }
}

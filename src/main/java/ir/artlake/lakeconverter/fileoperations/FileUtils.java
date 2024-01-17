package ir.artlake.lakeconverter.fileoperations;

import java.io.File;

public class FileUtils {
    public static File createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public static String getFilePathWithoutExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}

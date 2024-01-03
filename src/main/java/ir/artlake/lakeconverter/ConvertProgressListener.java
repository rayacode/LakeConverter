package ir.artlake.lakeconverter;

import javafx.application.Platform;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

import java.io.File;

public class ConvertProgressListener implements EncoderProgressListener {
    private final ConversionProgressTask task;
    private File file;
    public ConvertProgressListener(ConversionProgressTask task) {
        this.task = task;
    }
    public ConvertProgressListener(ConversionProgressTask task, File file) {
        this.task = task;
        this.file = file;
        task.setName(file.getName());
    }
    public void progress(int p) {
        double progress = p / 1000.0;
        Platform.runLater(() -> {
            task.updateTaskProgress(progress, 1);
        });
    }
    public void message(String m) {}
    public void sourceInfo(MultimediaInfo m) {}
}
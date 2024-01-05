package ir.artlake.lakeconverter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.Semaphore;

public class ConversionProgressTask extends Task<Boolean> {
    private String name;
    private ConvertProgressListener listener;
    private String source;
    private String target;
    private Semaphore semaphore;

    /*public ConversionProgressTask(String source, String target, Semaphore semaphore) {
        this.source = source;
        this.target = target;
        this.semaphore = semaphore;
        this.listener = new ConvertProgressListener(this);
        this.name = new File(source).getName();
    }*/

    @Override
    protected Boolean call() throws Exception {
        semaphore.acquire();
        try {
            Converter converter = new Converter(listener, source, target);
            return converter.convert();
        } finally {
            semaphore.release();
        }
    }

    public void updateTaskProgress(double workDone, double max) {
        updateProgress(workDone, max);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
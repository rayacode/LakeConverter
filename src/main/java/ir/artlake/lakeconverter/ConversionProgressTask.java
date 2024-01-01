package ir.artlake.lakeconverter;

import javafx.concurrent.Task;

public class ConversionProgressTask extends Task<Void> {
    @Override
    protected Void call() throws Exception {
        return null;
    }
    public void updateTaskProgress(double workDone, double max) {
        updateProgress(workDone, max);
    }
}
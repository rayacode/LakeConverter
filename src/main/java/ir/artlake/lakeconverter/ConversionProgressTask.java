package ir.artlake.lakeconverter;

import javafx.concurrent.Task;

public class ConversionProgressTask extends Task<Void> {
private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Void call() throws Exception {

        return null;
    }
    public void updateTaskProgress(double workDone, double max) {
        updateProgress(workDone, max);
    }

}
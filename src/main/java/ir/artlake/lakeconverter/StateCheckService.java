package ir.artlake.lakeconverter;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.io.File;
import java.util.Map;

public class StateCheckService extends Service<Boolean> {
    private final Map<File, FileConverterInit> fileConverterInitMap;

    public StateCheckService(Map<File, FileConverterInit> fileConverterInitMap) {
        this.fileConverterInitMap = fileConverterInitMap;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                while (!isCancelled()) {
                    boolean isAnyRunning = false;
                    for (FileConverterInit fileConverterInit : fileConverterInitMap.values()) {
                        if (fileConverterInit.getTask().getState() == Worker.State.RUNNING) {
                            isAnyRunning = true;
                            break;
                        }
                    }
                    if (!isAnyRunning) {
                        break; // Exit the loop if none of the tasks are running
                    }
                    Thread.sleep(1000); // Sleep for 1 second
                }
                return false;
            }
        };
    }
}

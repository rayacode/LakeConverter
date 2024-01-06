package ir.artlake.lakeconverter;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.concurrent.Semaphore;

public class ConversionService extends Service<Boolean> {
    private String name;
    private Converter headServiceConvertClass;
    private ConvertProgressListener listener;
    private String source;
    private String target;
    private Semaphore semaphore;
    public ConversionService(String source, String target, Semaphore semaphore) {
        this.source = source;
        this.target = target;
        this.semaphore = semaphore;

        this.name = new File(source).getName();

    }

    @Override
    protected Task<Boolean> createTask() {
        return new ConversionTask();

    }
    class ConversionTask extends Task<Boolean> {
        Converter converter;
        @Override
        protected Boolean call() throws Exception {
            ConvertProgressListener listener = new ConvertProgressListener(this::updateProgress);
            semaphore.acquire();
            try {
                converter = new Converter(listener, source, target);
                headServiceConvertClass = converter;

                return converter.convert();
            }
            finally {
                semaphore.release();
            }
        }


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Converter getHeadServiceConvertClass() {
        return headServiceConvertClass;
    }
}

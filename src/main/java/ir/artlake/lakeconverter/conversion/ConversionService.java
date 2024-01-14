package ir.artlake.lakeconverter.conversion;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ws.schild.jave.Encoder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class ConversionService extends Service<Boolean> {
    private String name;
    private Converter headServiceConvertClass;

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

        ConvertProgressListener listener= new ConvertProgressListener(this::updateProgress);
        Converter converter = new Converter(listener, source, target);
        boolean permitReleased = false;

        @Override
        protected Boolean call() throws Exception {

            semaphore.acquire();

            try {

                headServiceConvertClass = converter;

                return converter.convertVideo();
            }
            finally {
                semaphore.release();
            }

        }
        private void releasePermit() {

                semaphore.release();


        }
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            boolean cancelled = super.cancel(mayInterruptIfRunning);


                if(converter.getEncoder() != null){
                    converter.getEncoder().abortEncoding();
                    //converter.setEncoder(new Encoder());
                }




            //releasePermit();

            return cancelled;
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

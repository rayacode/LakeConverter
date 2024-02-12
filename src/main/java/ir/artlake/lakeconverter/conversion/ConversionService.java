package ir.artlake.lakeconverter.conversion;

import ir.artlake.lakeconverter.conversion.Formats.Format;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jetbrains.annotations.NotNull;
import ir.artlake.lakeconverter.jave.Encoder;
import ir.artlake.lakeconverter.jave.EncoderException;
import ir.artlake.lakeconverter.jave.MultimediaObject;
import ir.artlake.lakeconverter.jave.encode.EncodingAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import static ir.artlake.lakeconverter.Main.executorService;

public class ConversionService<T extends Format> extends Service<Boolean> {
    Logger LOG = LoggerFactory.getLogger(ConversionTask.class);
    private String name;

    private File source;
    private File target;
    private Semaphore semaphore;
    private boolean isSingleFormatChanged;
    private T targetFormat;
    ConvertProgressListener listener = new ConvertProgressListener();
    private Encoder encoder = new Encoder();
    public ConversionService(String source, String target, Semaphore semaphore, T format) {

        this.source = new File(source);
        String sourceName = this.source.getName();
        String baseName = sourceName.substring(0, sourceName.lastIndexOf('.'));
        this.targetFormat = format;
        this.target = new File(target, baseName + targetFormat.getFileExtension());

        this.semaphore = semaphore;
        this.isSingleFormatChanged = false;
        this.setExecutor(executorService);
    }
    @Override
    protected Task<Boolean> createTask() {
        return new ConversionTask();
    }
    class ConversionTask extends Task<Boolean> {
        ConversionTask(){
            listener.setProgressConsumer(this::updateProgress);
        }

        @Override
        protected Boolean call() throws Exception {
            semaphore.acquire();
            try {


                //Audio Attributes


                EncodingAttributes attrs = targetFormat.getEncodingAttributes();

                LOG.info(attrs.toString());



                int counter = 0;
                String path = target.getPath();
                String base = path.substring(0, path.lastIndexOf("."));
                String extension = path.substring(path.lastIndexOf("."));

                while (target.exists()) {
                    counter++;
                    target = new File(base + "_" + counter + extension);
                }

                encoder.encode(new MultimediaObject(source), target, attrs, listener);

                return true;

            } catch (IllegalArgumentException | EncoderException ex){
                System.out.print("I through from here!");
                ex.printStackTrace();


                return false;
            }finally {
                semaphore.release();
            }


        }
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            boolean cancelled = super.cancel(mayInterruptIfRunning);


            if(encoder != null){
                encoder.abortEncoding();
                encoder = new Encoder();
            }




            //releasePermit();

            return cancelled;
        }
    }

    public boolean isSingleFormatChanged() {
        return isSingleFormatChanged;
    }

    public void setSingleFormatChanged(boolean singleFormatChanged) {
        isSingleFormatChanged = singleFormatChanged;
    }

    public T getTargetFormat() {
        return targetFormat;
    }

    public void setTargetFormat(T targetFormat) {
        this.targetFormat = targetFormat;
    }
}

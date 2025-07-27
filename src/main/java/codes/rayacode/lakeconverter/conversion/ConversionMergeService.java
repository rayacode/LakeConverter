package codes.rayacode.lakeconverter.conversion;

import codes.rayacode.lakeconverter.conversion.Formats.Format;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import codes.rayacode.lakeconverter.jave.Encoder;
import codes.rayacode.lakeconverter.jave.EncoderException;
import codes.rayacode.lakeconverter.jave.MultimediaObject;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import codes.rayacode.lakeconverter.jave.filters.FilterChain;
import codes.rayacode.lakeconverter.jave.filters.FilterGraph;
import codes.rayacode.lakeconverter.jave.filters.MediaConcatFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static codes.rayacode.lakeconverter.Main.executorService;

public class ConversionMergeService<T extends Format> extends Service<Boolean> {
    private String name;

    List<MultimediaObject> source;
    private File target;
    private Semaphore semaphore;
    private boolean isSingleFormatChanged;
    private T targetFormat;
    ConvertProgressListener listener = new ConvertProgressListener();
    private Encoder encoder = new Encoder();
    public ConversionMergeService(List<MultimediaObject> source, String target, Semaphore semaphore, T format) {

        this.source = source;
        String sourceName = this.source.get(0).getFile().getName();
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


                


                EncodingAttributes attrs = targetFormat.getEncodingAttributes();




                int counter = 0;
                String path = target.getPath();
                String base = path.substring(0, path.lastIndexOf("."));
                String extension = path.substring(path.lastIndexOf("."));

                while (target.exists()) {
                    counter++;
                    target = new File(base + "_" + counter + extension);
                }

                FilterGraph complexFiltergraph= new FilterGraph();
                FilterChain fc= new FilterChain();
                fc.addFilter(new MediaConcatFilter(source.size(), true, true));
                complexFiltergraph.addChain(fc);
                for(MultimediaObject multimediaObject : source){
                    System.out.println(multimediaObject.getFile().getName());
                }
                encoder.encode(source, target, attrs, listener);

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
                
            }




            

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

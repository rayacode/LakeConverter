package ir.artlake.lakeconverter.conversion;

import ir.artlake.lakeconverter.conversion.Formats.Format;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

import static ir.artlake.lakeconverter.Main.executorService;

public class ConversionServiceOLD extends Service<Boolean> {
    private String name;
    private ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter<Format> headServiceConvertClass;

    private String source;
    private String target;
    private Semaphore semaphore;
    private Format targetFormat;
    private boolean isSingleFormatChanged;
    public ConversionServiceOLD(String source, String target, Semaphore semaphore, Format format) {
        this.source = source;
        this.target = target;
        this.semaphore = semaphore;
        this.targetFormat = format;
        this.name = new File(source).getName();
        this.setExecutor(executorService);
        this.isSingleFormatChanged = false;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new ConversionTask();

    }

    public boolean isSingleFormatChanged() {
        return isSingleFormatChanged;
    }

    public void setSingleFormatChanged(boolean singleFormatChanged) {
        isSingleFormatChanged = singleFormatChanged;
    }
    BiConsumer<Double, Double> progressConsumer;
    class ConversionTask extends Task<Boolean> {




        ConvertProgressListener listener= new ConvertProgressListener(this::updateProgress);

        ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter<Format> converter = new ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter<>(listener, source, target, targetFormat);

        boolean permitReleased = false;
        ConversionTask(){
            headServiceConvertClass = converter;
        }
        @Override
        protected Boolean call() throws Exception {

            semaphore.acquire();

            try {


                boolean returnValue = converter.convert();
                System.out.println("int try body im "+returnValue);
                return returnValue;
            }catch (Exception exception){
                System.out.println("Strange exception got cought");
                exception.printStackTrace();
                return false;
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

    public ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter getHeadServiceConvertClass() {
        return headServiceConvertClass;
    }

    public Format getTargetFormat() {
        return getHeadServiceConvertClass().getTargetFormat();
    }

    public void setTargetFormat(Format targetFormat) {
        getHeadServiceConvertClass().setTargetFormat(targetFormat);
    }
}

package ir.artlake.lakeconverter.conversion;

import ir.artlake.lakeconverter.conversion.Formats.Format;
import ir.artlake.lakeconverter.conversion.Formats.MP4;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import static ir.artlake.lakeconverter.Main.executorService;

public class ConversionService extends Service<Boolean> {
    private String name;
    private ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter<Format> headServiceConvertClass;

    private String source;
    private String target;
    private Semaphore semaphore;
    private Format targetFormat;
    private boolean isSingleFormatChanged;
    public ConversionService(String source, String target, Semaphore semaphore, Format format) {
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

    class ConversionTask extends Task<Boolean> {




        ConvertProgressListener listener= new ConvertProgressListener(this::updateProgress);
        ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter<Format> converter = new ir.artlake.lakeconverter.conversion.ConverterImplemention.Converter<>(listener, source, target, targetFormat);
        boolean permitReleased = false;

        @Override
        protected Boolean call() throws Exception {

            semaphore.acquire();

            try {

                headServiceConvertClass = converter;
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
        return targetFormat;
    }

    public void setTargetFormat(Format targetFormat) {
        this.targetFormat = targetFormat;
    }
}

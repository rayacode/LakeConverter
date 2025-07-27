/*  LakeConverter: A java Gui wrapper with jave for ffmpeg
 *  Copyright (C) 2023 Mohammad Ali Solhjoo mohammadalisolhjoo@live.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package codes.rayacode.lakeconverter.conversion;

import codes.rayacode.lakeconverter.conversion.Formats.Format;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jetbrains.annotations.NotNull;
import codes.rayacode.lakeconverter.jave.Encoder;
import codes.rayacode.lakeconverter.jave.EncoderException;
import codes.rayacode.lakeconverter.jave.MultimediaObject;
import codes.rayacode.lakeconverter.jave.encode.EncodingAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import static codes.rayacode.lakeconverter.Main.executorService;

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
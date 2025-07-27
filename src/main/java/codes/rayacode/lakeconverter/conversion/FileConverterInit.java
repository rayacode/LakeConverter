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

import codes.rayacode.lakeconverter.ConvertCellWidget;
import codes.rayacode.lakeconverter.conversion.Formats.Format;
import javafx.concurrent.Worker;

import java.io.File;
import java.util.concurrent.Semaphore;

import static codes.rayacode.lakeconverter.Main.executorService;



public class FileConverterInit {
    private final ConversionService service;

    private boolean isConverted;

    private Semaphore semaphore;

    private File source;

    private ConvertCellWidget convertCellWidget;

    private String targetFolder;

    public FileConverterInit(String source, String target, Semaphore semaphore, Format format) {
        this.semaphore = semaphore;

        service = new ConversionService(source, target, semaphore, format);
        this.targetFolder = target;
        this.source = new File(source);
        service.setExecutor(executorService);
        this.isConverted = false;


    }

    public synchronized  void startConversion() {
            if(service.getState() == Worker.State.READY && isConverted == false) {

                    service.start();
                    isConverted = true;

            }
    }
    public synchronized  void restartConversion(){

        

            service.reset();

            service.start();

    }
    public synchronized void deleteOrCancelConvertFileThread(){
        if (service.getState() == Worker.State.RUNNING || service.getState() == Worker.State.SCHEDULED) {
            service.cancel();
        }

    }
    public void changeFormat(Format format){
        if (!service.isSingleFormatChanged()) {
            service.setTargetFormat(format);
            service.setSingleFormatChanged(true);
        }


    }

    public ConversionService getService() {
        return service;
    }

    public File getSource() {
        return source;
    }


    public ConvertCellWidget getConvertCellWidget() {
        return convertCellWidget;
    }

    public void setConvertCellWidget(ConvertCellWidget convertCellWidget) {
        this.convertCellWidget = convertCellWidget;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }
}
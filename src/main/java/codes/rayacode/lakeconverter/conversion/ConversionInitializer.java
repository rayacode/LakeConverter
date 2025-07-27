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
import codes.rayacode.lakeconverter.fileoperations.FileUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConversionInitializer {
    private final Semaphore semaphore;
    private File selectedTarget;

    public ConversionInitializer(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public List<FileConverterInit> initializeConversions(List<File> selectedFiles, File selectedTarget, Format format) {
        List<FileConverterInit> fileConverterInitList = new LinkedList<>();
        for (File file : selectedFiles) {
            if(selectedTarget == null || this.selectedTarget != selectedTarget ){

                selectedTarget = FileUtils.createDirectory(file.getParent() + "\\converted");
                this.selectedTarget = selectedTarget;


                fileConverterInitList.add(new FileConverterInit( file.getAbsolutePath(), selectedTarget.getAbsolutePath(), semaphore, format));
            }else {
                fileConverterInitList.add(new FileConverterInit( file.getAbsolutePath(), selectedTarget.getAbsolutePath(), semaphore, format));
            }

        }

        return fileConverterInitList;
    }
}
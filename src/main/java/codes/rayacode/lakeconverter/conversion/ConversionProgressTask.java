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

import javafx.concurrent.Task;

import java.util.concurrent.Semaphore;

public class ConversionProgressTask extends Task<Boolean> {
    private String name;
    private ConvertProgressListener listener;
    private String source;
    private String target;
    private Semaphore semaphore;

    /*public ConversionProgressTask(String source, String target, Semaphore semaphore) {
        this.source = source;
        this.target = target;
        this.semaphore = semaphore;
        this.listener = new ConvertProgressListener(this);
        this.name = new File(source).getName();
    }*/

    @Override
    protected Boolean call() throws Exception {
        semaphore.acquire();
        try {
            Converter converter = new Converter(listener, source, target);
            return converter.convert();
        } finally {
            semaphore.release();
        }
    }

    public void updateTaskProgress(double workDone, double max) {
        updateProgress(workDone, max);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
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

package codes.rayacode.lakeconverter.jave.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

/**
 * Use this class in a try-with-resources block to automatically delete the referenced file when
 * this goes out of scope.
 *
 * @author mressler
 */
public class AutoRemoveableFile extends File implements AutoCloseable {

  private static final Logger logger = LoggerFactory.getLogger(AutoRemoveableFile.class);

  private static final long serialVersionUID = 1270202558229293283L;

  public AutoRemoveableFile(File parent, String child) {
    super(parent, child);
  }

  @Override
  public void close() {
    boolean closed = delete();
    if (!closed) {
      logger.warn(
          "File "
              + getAbsolutePath()
              + " did not automatically delete itself: "
              + Arrays.toString(Thread.currentThread().getStackTrace()));
    }
  }
}
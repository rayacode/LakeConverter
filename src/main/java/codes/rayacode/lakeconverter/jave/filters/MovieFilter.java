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

package codes.rayacode.lakeconverter.jave.filters;

import java.io.File;

public class MovieFilter extends Filter {

  /**
   * A simple instantiation of the <a href="https://ffmpeg.org/ffmpeg-filters.html#movie-1">movie</a> filter.
   * @param source The source image to be used for this movie filter.
   */
  public MovieFilter(File source) {
    super("movie");
    /*
     * Need escaping special characters []\':,;
     */
    addOrderedArgument(escapingPath(source.getAbsolutePath()));
  }

  public MovieFilter(File source, String outputLabel) {
    this(source);
    addOutputLabel(outputLabel);
  }

}
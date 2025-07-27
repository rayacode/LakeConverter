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

/**
 * A crop filter as described by the <a href=
 * "https://ffmpeg.org/ffmpeg-filters.html#crop">
 * FFMPEG Documentation</a>.
 */
public class CropFilter extends Filter {

  /**
   * Crop filter
   */
  public CropFilter() {
    super("crop");
  }
  
  /**
   * Simple constructor - crop input stream to given w/h//x/y
   * 
   * @param width width crop here
   * @param height height of crop area
   * @param posX origin of crop area
   * @param posY origin of crop area
   */
  public CropFilter(int width, int height, int posX, int posY) {
    super("crop");
    addNamedArgument("w", Integer.toString(width));
    addNamedArgument("h", Integer.toString(height));
    addNamedArgument("x", Integer.toString(posX));
    addNamedArgument("y", Integer.toString(posY));
  }

  /**
   * Simple constructor - crop input stream with given expression
   * For example: in_w/2:in_h/2:in_w/2:in_h/2 for bottom right quarter
   * 
   * @param cropExpression string expression
   */
  public CropFilter(String cropExpression) {
    super("crop");
    addOrderedArgument(cropExpression);
  }
}
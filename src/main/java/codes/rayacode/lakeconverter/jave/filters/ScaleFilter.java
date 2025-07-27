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

import codes.rayacode.lakeconverter.jave.filters.helpers.ForceOriginalAspectRatio;
import codes.rayacode.lakeconverter.jave.info.VideoSize;

/**
 * An implementation of the scale filter as found in the <a
 * href="https://ffmpeg.org/ffmpeg-filters.html#scale-1">FFMPEG Documentation</a>.
 */
public class ScaleFilter extends Filter {

  public ScaleFilter() {
    super("scale");
  }

  /** 
   * Scale the video to a particular size and maintain aspect ratio.
   * @param toSize What size should the video be scaled to?
   */
  public ScaleFilter(VideoSize toSize) {
    super("scale");
    addNamedArgument("w", toSize.getWidth().toString());
    addNamedArgument("h", toSize.getHeight().toString());
  }

  /** 
   * Scale the video to a particular size and maintain aspect ratio.
   * @param scaleExpression What size should the video be scaled to?
   * Can be an expression like "trunc(iw/2)*2:trunc(ih/2)*2"
   */
  public ScaleFilter(String scaleExpression) {
    super("scale");
    addOrderedArgument(scaleExpression);
  }
  
  /** 
   * Scale the video to a particular size and maintain aspect ratio.
   * @param toSize What size should the video be scaled to?
   * @param foar Should the video be increased or decreased to size?
   */
  public ScaleFilter(VideoSize toSize, ForceOriginalAspectRatio foar) {
    super("scale");
    addNamedArgument("w", toSize.getWidth().toString());
    addNamedArgument("h", toSize.getHeight().toString());
    addNamedArgument("force_original_aspect_ratio", foar.getCommandLine());
  }
}
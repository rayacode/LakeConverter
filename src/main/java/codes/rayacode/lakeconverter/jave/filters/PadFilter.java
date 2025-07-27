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

import codes.rayacode.lakeconverter.jave.info.VideoSize;

public class PadFilter extends Filter {

  public PadFilter() {
    super("pad");
  }

  /**
   * Uses the <a href="https://ffmpeg.org/ffmpeg-filters.html#pad-1">pad</a> filter to pad the
   * source image to the same aspect ratio as {@code aspectRatio}.
   *
   * @param aspectRatio A {@link VideoSize} that represents the desired resulting aspect ratio.
   */
  public PadFilter(VideoSize aspectRatio) {
    super("pad");
    addNamedArgument("w", "ih*" + aspectRatio.aspectRatioExpression());
    addNamedArgument("h", "ih");
    addNamedArgument("x", "(ow-iw)/2");
    addNamedArgument("y", "(oh-ih)/2");
  }
}
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

public class ZoomPanFilter extends Filter {

  public ZoomPanFilter() {
    super("zoompan");
  }
  
  /**
   * A "top to bottom" zoom and pan of an image/video using the <a
   * href="https://ffmpeg.org/ffmpeg-filters.html#zoompan">zoompan</a> filter.
   *
   * <p>This instance of zoompan will animate gently using a sigmoid function. The first third of
   * the video is focused on the beginning of the image, the last third is focused on the bottom
   * part of the image, and the middle section will animate gracefully between the two.
   *
   * <p>It is expected that the input video is of the same aspect ratio as the output video. If
   * zooming in to a video of a different size, try using the {@link PadFilter} is used to pad the
   * source image/video to the same aspect ratio as {@code ouptputSize}
   *
   * @param durationFrames The number of frames to emit for this zoompan filter. Default FPS is 25.
   * @param inputSize The size of the original image/video this filter is zooming around in.
   * @param outputSize The size of the resulting video after the zoompan filter is applied.
   */
  public ZoomPanFilter(Integer durationFrames, VideoSize inputSize, VideoSize outputSize) {
    super("zoompan");
    addNamedArgument("d", durationFrames.toString());
    addNamedArgument("s", outputSize.asEncoderArgument());
    addNamedArgument(
        "zoom", outputSize.aspectRatioExpression() + "/" + inputSize.aspectRatioExpression());
    addNamedArgument("x", "(" + inputSize.getWidth() + "*zoom-" + inputSize.getWidth() + ")/2");
    addNamedArgument("y", "1/(1+exp(-20*(on/(25*4)-0.5)))*(ih-ih/zoom)");
  }

  /**
   * A simple usage of the <a href="https://ffmpeg.org/ffmpeg-filters.html#zoompan">zoompan</a>
   * filter.
   *
   * @param durationFrames The number of frames to emit for this zoompan filter. Default FPS is 25.
   * @param outputSize The size of the resulting video after the zoompan filter is applied.
   * @param zoomExpression An <a href="https://ffmpeg.org/ffmpeg-utils.html#Expression-Evaluation">
   *     expression</a> that represents the current zoom level.
   * @param xExpression An <a href="https://ffmpeg.org/ffmpeg-utils.html#Expression-Evaluation">
   *     expression</a> that represents the current x location.
   * @param yExpression An <a href="https://ffmpeg.org/ffmpeg-utils.html#Expression-Evaluation">
   *     expression</a> that represents the current y location.
   */
  public ZoomPanFilter(
      Integer durationFrames,
      VideoSize outputSize,
      String zoomExpression,
      String xExpression,
      String yExpression) {
    super("zoompan");
    addNamedArgument("d", durationFrames.toString());
    addNamedArgument("s", outputSize.asEncoderArgument());
    addNamedArgument("zoom", zoomExpression);
    addNamedArgument("x", xExpression);
    addNamedArgument("y", yExpression);
  }
}
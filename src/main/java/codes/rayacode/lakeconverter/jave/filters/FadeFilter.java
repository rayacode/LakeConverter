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

import codes.rayacode.lakeconverter.jave.filters.helpers.FadeDirection;

/**
 * An implementation of the fade filter as found in the <a href= "https://ffmpeg.org/ffmpeg-filters.html#fade"> FFMPEG Documentation</a>.
 */
public class FadeFilter extends Filter {
  
  public FadeFilter() {
    super("fade");
  }
  
  /**
   * Standard usage - fase in or out at some time for some duration.
   * @param dir In or Out.
   * @param startTimeSeconds When to start the fade.
   * @param durationSeconds How long to fade in or out.
   */
  public FadeFilter(FadeDirection dir, Double startTimeSeconds, Double durationSeconds) {
    super("fade");
    addNamedArgument("type", dir.toString());
    addNamedArgument("start_time", startTimeSeconds.toString());
    addNamedArgument("duration", durationSeconds.toString());
  }
  
}
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

package codes.rayacode.lakeconverter.jave.filters.helpers;

public enum ForceOriginalAspectRatio {
  /**
   * Scale the video as specified and disable this feature.
   */
  DISABLE,
  /**
   * The output video dimensions will automatically be decreased if needed.
   */
  DECREASE,
  /**
   * The output video dimensions will automatically be increased if needed.
   */
  INCREASE;
  
  public String getCommandLine() {
    return name().toLowerCase();
  }
}
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
 * An implementation of the overlay filter as specified by <a
 * href="https://ffmpeg.org/ffmpeg-filters.html#trim">FFMPEG Documentation</a>
 *
 * <p>Important implementation note: Most common usage of the trim filter requires a setpts filter
 * applied immediately after in the filter chain.
 *
 * @author mressler
 */
public class TrimFilter extends Filter {  
  
  public TrimFilter() {
    super("trim");
  }
  
  public TrimFilter(Double start, Double duration) {
    super("trim");
    addNamedArgument("start", start.toString());
    addNamedArgument("duration", duration.toString());
  }

  public TrimFilter(String inputLabel, Double start, Double duration) {
    this(start, duration);
    addInputLabel(inputLabel);
  }
}
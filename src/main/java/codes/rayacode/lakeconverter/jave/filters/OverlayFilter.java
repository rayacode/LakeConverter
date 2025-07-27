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

import codes.rayacode.lakeconverter.jave.filters.helpers.OverlayLocation;

import java.util.Optional;

/**
 * An implementation of the overlay filter as specified by <a
 * href="https://ffmpeg.org/ffmpeg-filters.html#overlay-1">FFMPEG Documentation</a>
 *
 * @author mressler
 */
public class OverlayFilter extends Filter {

  public OverlayFilter() {
    super("overlay");
  }
  
  /**
   * Overlay video onto {@code baseInputLabel} at {@code location}. Offsets specify x/y 
   * offsets from the four locations. This constructor has an implicit unmatched input pad that 
   * needs to be filled by a previous filter in the chain.
   * 
   * @param baseInputLabel The location to overlay video onto.
   * @param location One of the four corners.
   * @param offsetX An offset from one of the four corners.
   * @param offsetY An offset from one of the four corners.
   */
  public OverlayFilter(
      String baseInputLabel, 
      OverlayLocation location, 
      Integer offsetX, 
      Integer offsetY) 
  {
    super("overlay");
    addInputLabel(baseInputLabel);
    addOrderedArgument(
        location.getX(Optional.ofNullable(offsetX)), location.getY(Optional.ofNullable(offsetY)));
  }

  public OverlayFilter(
      String baseInputLabel,
      String overlayInputLabel,
      OverlayLocation location,
      Integer offsetX,
      Integer offsetY) 
  {
    this(baseInputLabel, location, offsetX, offsetY);
    addInputLabel(overlayInputLabel);
  }
}
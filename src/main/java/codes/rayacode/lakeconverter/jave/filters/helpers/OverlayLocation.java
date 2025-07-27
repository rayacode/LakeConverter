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

import java.util.Optional;

/**
 * Use this class to specify the starting location of your overlay. This accounts for video and
 * overlay dimensions and still allows for relative offset.
 *
 * @author mressler
 */
public enum OverlayLocation {
  TOP_LEFT(null, null),
  TOP_RIGHT("main_w-overlay_w", null),
  BOTTOM_RIGHT("main_w-overlay_w", "main_h-overlay_h"),
  BOTTOM_LEFT(null, "main_h-overlay_h");

  private final Optional<String> x;
  private final Optional<String> y;

  private OverlayLocation(String x, String y) {
    this.x = Optional.ofNullable(x);
    this.y = Optional.ofNullable(y);
  }

  public String getExpression(Optional<Integer> offsetX, Optional<Integer> offsetY) {
    return getX(offsetX) + ":" + getY(offsetY);
  }

  private static String resolveExpression(Optional<String> location, Optional<Integer> offset) {
    Optional<String> offsetValue = offset.map(Object::toString);
    return location.map(loc -> loc.concat(offsetValue.orElse(""))).orElse(offsetValue.orElse("0"));
  }

  public String getX(Optional<Integer> offset) {
    return resolveExpression(x, offset);
  }

  public String getY(Optional<Integer> offset) {
    return resolveExpression(y, offset);
  }
}
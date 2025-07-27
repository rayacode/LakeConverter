/*  LakeConverter: A java Gui wrapper with jave for ffmpeg
 *  Copyright (C) André Schild
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

package codes.rayacode.lakeconverter.jave.encode.enums;

public enum X264_PROFILE {
  DEFAULT("default"),
  BASELINE("baseline"),
  MAIN("main"),
  HIGH("high"),
  HIGH10("high10"),
  HIGH422("high422"),
  HIGH444("high444");
  
  private final String modeName;

  X264_PROFILE(String modeName) {
    this.modeName = modeName;
  }

  public String getModeName() {
    return modeName;
  }
}
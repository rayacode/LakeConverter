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

package codes.rayacode.lakeconverter.jave.encode.enums;

/**
 * Add VSYNC methods described in the <a href="https://ffmpeg.org/ffmpeg.html#Advanced-options">FFMPEG Documentation</a>.
 *
 */
public enum VsyncMethod {
  /**
   * Each frame is passed with its timestamp from the demuxer to the muxer.
   */
  PASSTHROUGH("passthrough"),
  /**
   * Frames will be duplicated and dropped to achieve exactly the requested constant frame rate.
   */
  CFR("cfr"),
  /**
   * Frames are passed through with their timestamp or dropped so as to prevent 2 frames from having the same timestamp.
   */
  VFR("vfr"),
  /**
   * As passthrough but destroys all timestamps, making the muxer generate fresh timestamps based on frame-rate.
   */
  DROP("drop"),
  /**
   * Chooses between CFR and VFR depending on muxer capabilities. This is the default method.
   */
  AUTO("auto");
  
  private final String methodName;
  
  private VsyncMethod(String parameter) {
    methodName = parameter;
  }
  
  public String getMethodName() {
    return methodName;
  }
}
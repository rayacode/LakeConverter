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

package codes.rayacode.lakeconverter.jave.filtergraphs;

import codes.rayacode.lakeconverter.jave.filters.FilterChain;
import codes.rayacode.lakeconverter.jave.filters.FilterGraph;
import codes.rayacode.lakeconverter.jave.filters.MovieFilter;
import codes.rayacode.lakeconverter.jave.filters.OverlayFilter;
import codes.rayacode.lakeconverter.jave.filters.helpers.OverlayLocation;

import java.io.File;

/**
 * Overlay an image over an input video. Input video must be specified using a -i option to ffmpeg
 *
 * @author mressler
 */
public class OverlayWatermark extends FilterGraph {

  /**
   * Create an overlay filtergraph that will overlay a watermark image on the video.
   *
   * @param watermark The location of the watermark image
   * @param location The location on the video that the watermark should be overlaid
   * @param offsetX The offset from the location that the watermark should be offset. Positive
   *     values move the image right. Negative values move it left.
   * @param offsetY The offset from the location that the watermark should be offset. Positive
   *     values move the image down. Negative values move it up.
   */
  public OverlayWatermark(
      File watermark, OverlayLocation location, Integer offsetX, Integer offsetY) {
    super(
        new FilterChain(new MovieFilter(watermark, "watermark")),
        new FilterChain(new OverlayFilter("0:v", "watermark", location, offsetX, offsetY)));
  }
}
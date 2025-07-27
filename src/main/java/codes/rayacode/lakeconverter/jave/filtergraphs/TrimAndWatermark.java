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
import codes.rayacode.lakeconverter.jave.filters.SetPtsFilter;
import codes.rayacode.lakeconverter.jave.filters.TrimFilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Trim and watermark any number of input videos. */
public class TrimAndWatermark extends FilterAndWatermark {

  public TrimAndWatermark(File watermark, List<TrimInfo> trimInfo) {
    super(
        watermark,
        IntStream.range(0, trimInfo.size())
            .mapToObj(i -> filterChainForTrimInfo(trimInfo.get(i)))
            .collect(Collectors.toList()));
  }

  public TrimAndWatermark(File watermark, Double trimStart, Double trimDuration) {
    this(watermark, Arrays.asList(new TrimInfo(trimStart, trimDuration)));
  }

  public static class TrimInfo {
    public Double trimStart;
    public Double trimDuration;

    public TrimInfo(Double trimStart, Double trimDuration) {
      this.trimStart = trimStart;
      this.trimDuration = trimDuration;
    }
  }

  public static FilterChain filterChainForTrimInfo(TrimInfo info) {
    return new FilterChain(new TrimFilter(info.trimStart, info.trimDuration), new SetPtsFilter());
  }
}
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

import codes.rayacode.lakeconverter.jave.filtergraphs.TrimAndWatermark.TrimInfo;
import codes.rayacode.lakeconverter.jave.filters.FadeFilter;
import codes.rayacode.lakeconverter.jave.filters.FilterChain;
import codes.rayacode.lakeconverter.jave.filters.helpers.FadeDirection;

import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrimFadeAndWatermark extends FilterAndWatermark {
  
  public TrimFadeAndWatermark(File watermark, List<TrimInfo> trimInfo) {
    super(
        watermark, 
        IntStream.range(0, trimInfo.size())
            .mapToObj(i -> filterChainForTrimInfo(trimInfo.get(i), fadesFromIndex(i, trimInfo.size()), 0.1))
            .collect(Collectors.toList()));
  }

  public static EnumSet<FadeDirection> fadesFromIndex(Integer i, Integer size) {
    EnumSet<FadeDirection> toFade = EnumSet.noneOf(FadeDirection.class);
    
    if (i != 0) {
      toFade.add(FadeDirection.IN);
    }
    
    if (i < size) {
      toFade.add(FadeDirection.OUT);
    }
    
    return toFade;
  }
  
  public static FilterChain filterChainForTrimInfo(TrimInfo info, EnumSet<FadeDirection> fades, Double fadeDuration) {
    FilterChain toReturn = TrimAndWatermark.filterChainForTrimInfo(info);
    
    if (fades.contains(FadeDirection.IN)) {
      toReturn.prependFilter(new FadeFilter(FadeDirection.IN, 0.0, fadeDuration));
    }
    
    if (fades.contains(FadeDirection.OUT)) {
      toReturn.addFilter(new FadeFilter(FadeDirection.OUT, info.trimDuration - fadeDuration, fadeDuration));
    }
    
    return toReturn;
  }
  
}
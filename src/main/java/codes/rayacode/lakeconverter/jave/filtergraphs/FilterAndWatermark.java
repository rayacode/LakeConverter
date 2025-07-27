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

import codes.rayacode.lakeconverter.jave.filters.*;
import codes.rayacode.lakeconverter.jave.filters.helpers.OverlayLocation;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * An abstract filtergraph that will run a filter on multiple input videos then concatenate and
 * watermark the result.
 *
 * <p>Implementors are expected to provide the filter chains to this abstract class via the init()
 * method.
 */
public abstract class FilterAndWatermark extends FilterGraph {

  public FilterAndWatermark(File watermark, List<FilterChain> inputFilterChains) {
    super();

    // Apply the provided filterchain for each input video
    IntStream.range(0, inputFilterChains.size())
      .mapToObj(i -> prepFilterChain(inputFilterChains.get(i), i))
      .forEach(this::addChain);
    
    // Concatenate all input videos
    addChain(
        new FilterChain(
            new ConcatFilter(
                    IntStream.range(0, inputFilterChains.size())
                        .mapToObj(this::labelForOutput)
                        .collect(Collectors.toList()))
                .addOutputLabel("concatenated")));

    // Finally overlay the watermark
    addChain(
        new FilterChain(
            new MovieFilter(watermark), // Movie output is the second input to the overlay filter
            new OverlayFilter("concatenated", OverlayLocation.BOTTOM_RIGHT, -10, -10)));
  }

  private FilterChain prepFilterChain(FilterChain chain, Integer i) {
    return chain
        .setInputLabel(i.toString())
        .setOutputLabel(labelForOutput(i));
  }

  private String labelForOutput(Integer i) {
    return "filtered" + i;
  }
}
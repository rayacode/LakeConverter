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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A filtergraph as described by <a
 * href="https://ffmpeg.org/ffmpeg-filters.html#Filtergraph-syntax-1">FFMPEG Documentation</a>.
 *
 * <p>A filtergraph can optionally start with sws_flags for scaling of outputs and is then composed
 * of a semi-colon separated series of filterchains.
 *
 * @author mressler
 */
public class FilterGraph implements VideoFilter {

  private Optional<String> swsFlags;
  private List<FilterChain> chains;

  /** Create an empty filtergraph. */
  public FilterGraph() {
    swsFlags = Optional.empty();
    chains = new ArrayList<>();
  }
  /**
   * Create a filtergraph with a specified list of filterchains.
   *
   * @param chains The list of filterchains to be used in this filtergraph.
   */
  public FilterGraph(FilterChain... chains) {
    this();
    this.chains = new ArrayList<>(Arrays.asList(chains));
  }
  /**
   * Create a filtergraph with a specified list of filterchains and specified sws_flags.
   *
   * @param chains The list of filterchains to be used in this filtergraph.
   * @param swsFlags The sws_flags parameter to pass to libavfilter scale filters.
   */
  public FilterGraph(String swsFlags, FilterChain... chains) {
    this(chains);
    this.swsFlags = Optional.of(swsFlags);
  }

  /**
   * Add a filterchain to this filtergraph.
   *
   * @param chain The filterchain to add to this filtergraph.
   * @return this FilterGraph for builder pattern magic
   */
  public FilterGraph addChain(FilterChain chain) {
    chains.add(chain);
    return this;
  }

  /**
   * set the sws_flags to pass to libavfilter scale filters.
   *
   * @param swsFlags The flags that will; be passed to libavfilter scale filters.
   * @return this FilterGraph for builder pattern magic
   */
  public FilterGraph setSwsFlags(String swsFlags) {
    this.swsFlags = Optional.of(swsFlags);
    return this;
  }

  @Override
  public String getExpression() {
    return swsFlags.map(s -> "sws_flags=" + s + ";").orElse("")
        + chains.stream().map(VideoFilter::getExpression).collect(Collectors.joining(";"));
  }
}
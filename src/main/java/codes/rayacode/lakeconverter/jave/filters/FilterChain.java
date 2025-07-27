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
import java.util.stream.Collectors;

/**
 * A filterchain as described by <a
 * href="https://ffmpeg.org/ffmpeg-filters.html#Filtergraph-syntax-1">FFMPEG Documentation</a>.
 *
 * <p>A filterchain is a comma separated series of filters.
 *
 * @author mressler
 */
public class FilterChain implements VideoFilter {

  private final List<Filter> filters;

  /** Create an empty filterchain. */
  public FilterChain() {
    filters = new ArrayList<>();
  }
  /**
   * Create a filterchain with the specified filters
   *
   * @param filters The ordered list of filters in this chain
   */
  public FilterChain(Filter... filters) {
    this.filters = new ArrayList<>(Arrays.asList(filters));
  }

  /**
   * Add one Filter to this filterchain
   *
   * @param filter The Filter to add to this chain.
   * @return this FilterChain for builder pattern magic
   */
  public FilterChain addFilter(Filter filter) {
    filters.add(filter);
    return this;
  }
  
  public FilterChain prependFilter(Filter filter) {
    filters.add(0, filter);
    return this;
  }
  
  /**
   * Adds an input label to the first filter in this chain.
   * @param label The label to use for the input label for the first filter in this chain
   * @return this FilterChain for builder pattern magic
   * @throws IndexOutOfBoundsException if there are no filters in this chain.
   */
  public FilterChain setInputLabel(String label) {
    filters.get(0).addInputLabel(label);
    return this;
  }
  
  /**
   * Adds an output label to the first filter in this chain.
   * @param label The label to use for the output label for the last filter in this chain
   * @return this FilterChain for builder pattern magic
   * @throws IndexOutOfBoundsException if there are no filters in this chain.
   */
  public FilterChain setOutputLabel(String label) {
    filters.get(filters.size() - 1).addOutputLabel(label);
    return this;
  }

  @Override
  public String getExpression() {
    return filters.stream().map(VideoFilter::getExpression).collect(Collectors.joining(","));
  }
}
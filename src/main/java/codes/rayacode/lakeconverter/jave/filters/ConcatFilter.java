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

import java.util.List;

public class ConcatFilter extends Filter {

  /**
   * Apply the concatenate filter to the associated input labels
   * @param inputLabels The list of labels to be used as inputs to this concat filter.
   */
  public ConcatFilter(List<String> inputLabels) {
    super("concat");
    inputLabels.stream().forEach(this::addInputLabel);
    addNamedArgument("n", Integer.toString(inputLabels.size()));
  }
}
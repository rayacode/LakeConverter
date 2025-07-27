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

package codes.rayacode.lakeconverter.jave.encode;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A SimpleArgument is an EncodingArgument that provides all of its components, The argument type
 * and a Function from EncodingAttributes to a Stream&lt;String&gt; (arguments to ffmpeg)
 *
 * @author mressler
 */
public class SimpleArgument implements EncodingArgument {

  private final ArgType argumentType;
  private final Function<EncodingAttributes, Stream<String>> getArguments;

  public SimpleArgument(
      ArgType argumentType, Function<EncodingAttributes, Stream<String>> getArguments) {
    this.argumentType = argumentType;
    this.getArguments = getArguments;
  }

  @Override
  public Stream<String> getArguments(EncodingAttributes context) {
    return getArguments.apply(context);
  }

  @Override
  public ArgType getArgType() {
    return argumentType;
  }
}
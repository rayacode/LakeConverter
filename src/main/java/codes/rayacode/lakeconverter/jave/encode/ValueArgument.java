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

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A ValueArgument is an EncodingArgument that is optionally present based on the presence of the
 * provided valueGetter.
 *
 * @author mressler
 */
public class ValueArgument implements EncodingArgument {

  private final ArgType argumentType;
  private final String argumentName;
  private final Function<EncodingAttributes, Optional<String>> valueGetter;

  public ValueArgument(
      ArgType argType,
      String argumentName,
      Function<EncodingAttributes, Optional<String>> valueGetter) {
    this.argumentType = argType;
    this.argumentName = argumentName;
    this.valueGetter = valueGetter;
  }

  protected Boolean isPresent(EncodingAttributes context) {
    return getValue(context).isPresent();
  }

  @Override
  public Stream<String> getArguments(EncodingAttributes context) {
    return getValue(context).map(value -> Stream.of(getName(), value)).orElseGet(Stream::empty);
  }

  private String getName() {
    return argumentName;
  }

  private Optional<String> getValue(EncodingAttributes context) {
    return valueGetter.apply(context);
  }

  @Override
  public ArgType getArgType() {
    return argumentType;
  }
}
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

import java.util.stream.Stream;

/**
 * An EncodingArgument is a placeholder for a future argument to FFMPEG. It uses the
 * EncodingAttributes object to determine context and provides a Stream&lt;String&gt; of arguments
 * back to the caller to be used as arguments.
 *
 * @author mressler
 */
public interface EncodingArgument {

  /**
   * Gets the Stream of arguments given the EncodingAttributes as context. Implementers must take
   * care to return a new Stream on each successive call as doing otherwise will result in the
   * stream already being operated on exceptions.
   *
   * @param context The EncodingAttributes specified by the user. Use this in your closure to
   *     generate the arguments you'd like to pass to ffmpeg.
   * @return A stream of arguments to pass to ffmpeg.
   */
  public Stream<String> getArguments(EncodingAttributes context);

  public ArgType getArgType();
}
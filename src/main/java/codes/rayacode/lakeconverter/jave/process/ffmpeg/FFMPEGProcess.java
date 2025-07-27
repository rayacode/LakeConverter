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

package codes.rayacode.lakeconverter.jave.process.ffmpeg;

import codes.rayacode.lakeconverter.jave.process.ProcessWrapper;

import java.util.stream.Stream;

/**
 * The standard FFMPEGProcess - enhances the ProcessWrapper by always suppressing the FFMPEG banner.
 *
 * @author mressler
 */
public class FFMPEGProcess extends ProcessWrapper {

  public FFMPEGProcess(String ffmpegExecutablePath) {
    super(ffmpegExecutablePath);
  }

  @Override
  protected Stream<String> enhanceArguments(Stream<String> execArgs) {
    return Stream.concat(execArgs, Stream.of("-hide_banner"));
  }
}
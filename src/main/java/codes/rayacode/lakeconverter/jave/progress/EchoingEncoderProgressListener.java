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

package codes.rayacode.lakeconverter.jave.progress;

import codes.rayacode.lakeconverter.jave.info.MultimediaInfo;

import java.io.PrintStream;

/**
 * A simple progress listener that will echo progress out to any PrintStream.
 *
 * @author mressler
 */
public class EchoingEncoderProgressListener implements EncoderProgressListener {

  private PrintStream out;
  private String prefix;

  public EchoingEncoderProgressListener() {
    out = System.out;
    prefix = "";
  }

  public EchoingEncoderProgressListener(String prefix) {
    this();
    this.prefix = prefix;
  }

  public EchoingEncoderProgressListener(String prefix, PrintStream out) {
    this(prefix);
    this.out = out;
  }

  @Override
  public void sourceInfo(MultimediaInfo info) {
    out.println(prefix + " source info: " + info);
  }

  @Override
  public void progress(int permil) {
    out.println(prefix + " progress: " + permil);
  }

  @Override
  public void message(String message) {
    out.println(prefix + " message: " + message);
  }
}
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

import java.io.PrintStream;

/**
 * Simple class to echo progress to Standard out - or any PrintStream.
 *
 * @author mressler
 */
public class EchoingProgressListener implements VideoProgressListener {

  private String prefix;
  private PrintStream out;

  public EchoingProgressListener() {
    out = System.out;
    prefix = "";
  }

  public EchoingProgressListener(String prefix) {
    this();
    this.prefix = prefix;
  }

  public EchoingProgressListener(String prefix, PrintStream out) {
    this(prefix);
    this.out = out;
  }

  @Override
  public void onBegin() {
    out.println(prefix + " Beginning");
  }

  @Override
  public void onMessage(String message) {
    out.println(prefix + " Message Received: " + message);
  }

  @Override
  public void onProgress(Double progress) {
    out.println(prefix + " Progress Notification: " + progress);
  }

  @Override
  public void onError(String message) {
    out.println(prefix + " Error Encountered: " + message);
  }

  @Override
  public void onComplete() {
    out.println(prefix + " Complete!");
  }
}
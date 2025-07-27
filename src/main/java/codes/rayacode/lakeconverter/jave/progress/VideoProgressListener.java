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

/**
 * A VideoProgressListener is meant to share progress from potentially any number of
 * EncoderProgressListeners. Because it would be hard to determine the overall status by just
 * tracking successive progress from ffmpeg, an onBbegin and onComplete have been added.
 *
 * @author mressler
 */
public interface VideoProgressListener {

  /** It has begun! */
  public void onBegin();

  /**
   * Any messages that arise during the activity.
   *
   * @param message Whatever the process reported out.
   */
  public void onMessage(String message);

  /**
   * Meaningful progress has been made.
   *
   * @param progress Current percentage complete. (0-1)
   */
  public void onProgress(Double progress);

  /**
   * An error has occurred!
   *
   * @param message The error message reported by the process.
   */
  public void onError(String message);

  /** It has ended! */
  public void onComplete();
}
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.rayacode.lakeconverter.jave.utils;

import java.text.DecimalFormat;

/** @author a.schild */
public class Utils {

  /**
   * * https://www.ffmpeg.org/ffmpeg-utils.html#time-duration-syntax
   *
   * <p>Build a time/duration string based on the milisenconds passed in milis [-][HH:]MM:SS[.m...]
   * or [-]S+[.m...]
   *
   * @param milis number of miliseconds, can be negative too
   * @return String to be used for specifying positions in the video/audio file
   */
  public static String buildTimeDuration(long milis) {
    DecimalFormat df2 = new DecimalFormat("00");
    DecimalFormat df3 = new DecimalFormat("000");
    long milisPart = Math.abs(milis) % 1000;
    long seconds = Math.abs(milis) / 1000;
    long secondsPart = seconds % 60;
    long minutes = seconds / 60;
    long minutesPart = minutes % 60;
    long hours = minutes / 60;
    StringBuilder retVal = new StringBuilder();
    if (milis < 0) {
      retVal.append("-");
    }
    if (hours != 0) {
      retVal.append(df2.format(hours)).append(":");
    }
    if (minutesPart != 0 || hours != 0) {
      retVal.append(df2.format(minutesPart)).append(":");
    }
    retVal.append(df2.format(secondsPart));
    if (milisPart != 0) {
      retVal.append(".").append(df3.format(milisPart));
    }
    return retVal.toString();
  }

  /**
   * Escape all special characters []=;,' to be safe to use in command line
   * See https://ffmpeg.org/ffmpeg-utils.html#toc-Quoting-and-escaping
   * @param argumentIn input argument to escape
   * @return escaped string
   */
  public static String escapeArgument(String argumentIn) {
    String retVal;
    if (argumentIn.contains("\'"))
    {
        retVal= "'"+argumentIn.replace("'", "'\''")+"'";
    }
    else if (argumentIn.startsWith(" ") || argumentIn.endsWith(" "))
    {
        retVal= "'"+argumentIn+"'";
    }
    else
    {
        retVal = argumentIn.replace("[", "\\[");
        retVal = retVal.replace("]", "\\]");
        retVal = retVal.replace("=", "\\=");
        retVal = retVal.replace(":", "\\:");
        retVal = retVal.replace(",", "\\,");
    }
    return retVal;
  }
}
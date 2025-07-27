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

package codes.rayacode.lakeconverter.jave.encode.enums;

public enum TuneEnum {
    /**
     * use for high quality movie content; lowers deblocking
     */
    FILM("film"),
    /**
     * good for cartoons; uses higher deblocking and more reference frames
     */
    ANIMATION("animation"),
    /**
     * preserves the grain structure in old, grainy film material
     */
    GRAIN("grain"),
    /**
     * good for slideshow-like content
     */
    STILLIMAGE("stillimage"),
    /**
     * allows faster decoding by disabling certain filters
     */
    FASTDECODE("fastdecode"),
    /**
     * good for fast encoding and low-latency streaming
     */
    ZEROLATENCY("zerolatency"),
    /**
     * ignore this as it is only used for codec development
     */
    PSNR("psnr"),
    /**
     * ignore this as it is only used for codec development
     */
    SSIM("ssim");

    private final String tuneName;

    TuneEnum(String tuneName) {
        this.tuneName = tuneName;
    }

    public String getTuneName() {
        return tuneName;
    }
}
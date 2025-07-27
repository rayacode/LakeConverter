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
package codes.rayacode.lakeconverter.jave.filters;

/**
 *
 * @author a.schild
 */
public class MediaConcatFilter extends Filter {

    /**
     * Concat all input files and map first video and audio stream
     * Fails when a media has no video or audio stream
     * 
     * @param nSources number of input sources
     */
    public MediaConcatFilter(int nSources) {
        super("concat");
        initFilter(nSources, true, true);
    }

    /**
     * Concat all input files and map first video and audio stream
     * Fails when a media has no video or audio stream
     * 
     * @param nSources number of input sources
     * @param concatVideo Should we output the video stream
     * @param concatAudio Should we output the audio stream
     */
    public MediaConcatFilter(int nSources, boolean concatVideo, boolean concatAudio) {
        super("concat");
        initFilter(nSources, concatVideo, concatAudio);
    }
    
    protected void initFilter(int nSources, boolean concatVideo, boolean concatAudio)
    {
        String destinationDescription= "";
        if (concatVideo && concatAudio)
        {
            destinationDescription=  "v=1:a=1";
        }
        else if (concatVideo)
        {
            destinationDescription=  "v=1:a=0";
        }
        else if (concatAudio)
        {
            destinationDescription=  "v=0:a=1";
        }
        initFilter(nSources, concatVideo, concatAudio, destinationDescription);
    }
    
    protected void initFilter(int nSources, 
            boolean concatVideo, 
            boolean concatAudio, 
            String destinationDescription)
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < nSources; i++)
        {
            if (i > 0)
            {
                sb.append("] [");
            }
            sb.append(Integer.toString(i));
            if (concatVideo)
            {
                sb.append(":v:0");
                if (concatAudio)
                {
                    sb.append("] [").append(Integer.toString(i)).append(":a:0");
                }
            }
            else if (concatAudio)
            {
                sb.append(":a:0");
            }
        }
        addInputLabel(sb.toString());
        addNamedArgument("n", Integer.toString(nSources) + ":" + destinationDescription);
    }
}
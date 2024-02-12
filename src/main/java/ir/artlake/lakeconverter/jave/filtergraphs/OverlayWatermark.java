package ir.artlake.lakeconverter.jave.filtergraphs;

import ir.artlake.lakeconverter.jave.filters.FilterChain;
import ir.artlake.lakeconverter.jave.filters.FilterGraph;
import ir.artlake.lakeconverter.jave.filters.MovieFilter;
import ir.artlake.lakeconverter.jave.filters.OverlayFilter;
import ir.artlake.lakeconverter.jave.filters.helpers.OverlayLocation;

import java.io.File;

/**
 * Overlay an image over an input video. Input video must be specified using a -i option to ffmpeg
 *
 * @author mressler
 */
public class OverlayWatermark extends FilterGraph {

  /**
   * Create an overlay filtergraph that will overlay a watermark image on the video.
   *
   * @param watermark The location of the watermark image
   * @param location The location on the video that the watermark should be overlaid
   * @param offsetX The offset from the location that the watermark should be offset. Positive
   *     values move the image right. Negative values move it left.
   * @param offsetY The offset from the location that the watermark should be offset. Positive
   *     values move the image down. Negative values move it up.
   */
  public OverlayWatermark(
      File watermark, OverlayLocation location, Integer offsetX, Integer offsetY) {
    super(
        new FilterChain(new MovieFilter(watermark, "watermark")),
        new FilterChain(new OverlayFilter("0:v", "watermark", location, offsetX, offsetY)));
  }
}

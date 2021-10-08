package com.turtle.api;
public class ColorUtils {
  public static ImmutableTriplet<Integer, Integer, Integer> getComponentsOfColor(int rgb) {
    int red = (rgb >> 16) & 0x0ff;
    int green = (rgb >> 8) & 0x0ff;
    int blue = (rgb) & 0x0ff;
    return new ImmutableTriplet<>(red, green, blue);
  }

  public static int componentsToInt(ImmutableTriplet<Integer, Integer, Integer> color) {
    return ((color.getFirst() & 0x0ff) << 16) | ((color.getSecond() & 0x0ff) << 8) | (color.getThird() & 0x0ff);
  }

  public static int componentsToInt(int r, int g, int b) {
    return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
  }
}
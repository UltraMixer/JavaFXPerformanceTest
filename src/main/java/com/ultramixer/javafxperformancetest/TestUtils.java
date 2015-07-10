package com.ultramixer.javafxperformancetest;

/**
 * Created by TB on 10.07.15.
 */
public class TestUtils
{
    public static String formatDuration(long duration)
    {
        int minutes = (int) (duration / 1000d / 60d);
        int seconds = (int) (duration / 1000d);
        int ms = (int) ((duration / 1000d - ((int) (duration / 1000d))) * 1000);
        return String.format("%02d:%02d:%03d", minutes, seconds, ms);
    }

}

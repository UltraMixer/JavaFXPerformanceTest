package com.ultramixer.javafxperformancetest.util;

import java.math.BigDecimal;

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

    /**
     * @param value  Decimal number to round.
     * @param digits Number of digits after comma.
     * @return Rounded decimal number.
     */
    public static double round(final double value, int digits)
    {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();

        // Diese Methode sieht etwas kompliziert aus, aber sie ist tatsaechlich um den Faktor 25
        // schneller als BigDecimal. Ausnahme: Beim Runden grosser Zahlen auf viele Nachkommastellen
        // kann es passieren, dass der Wertebereich von Double (etwa 15 signifikante Stellen)
        // ueberschritten wird und Ungenauigkeiten entstehen. Spielt meist aber keine Rolle. (Thiemo)
        //double scale = 1;
        //while (--digits > 0)
        //{
        //    scale *= 10;
        //}
        //return (int) (value * scale + (value < 0 ? -0.5 : 0.5)) / scale;
    }

}

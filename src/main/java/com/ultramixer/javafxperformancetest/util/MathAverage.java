package com.ultramixer.javafxperformancetest.util;

import com.ultramixer.javafxperformancetest.util.CircularArray;

/**
 * Created by UltraMixer
 * User: messe
 * Date: 24.11.2008
 * Time: 15:04:47
 */
public class MathAverage
{
    private int numberCount;
    private CircularArray<Double> numbers;
    private int windors = 1; //Anzahl der Stellen, die oben und unten abgeschnitten werden, bevor der Mittelwert gebildet wird
    private int maxDiff = 0;
    private double lastAdd = -1;

    private int windorsOptimizedRange = 5;

    public MathAverage(int numberCount)
    {
        this.numberCount = numberCount;
        this.numbers = new CircularArray(numberCount, Double.class);
        stop();
    }

    public int getWindorsOptimizedRange()
    {
        return windorsOptimizedRange;
    }

    public void setWindorsOptimizedRange(int windorsOptimizedRange)
    {
        this.windorsOptimizedRange = windorsOptimizedRange;
    }

    public int getWindors()
    {
        return windors;
    }

    public void setWindors(int windors)
    {
        this.windors = windors;
    }

    public void start()
    {
    }

    public void addNumber(double number)
    {
        if (lastAdd >= 0)
        {
            if (Math.abs(number - lastAdd) > maxDiff)
            {
                number = lastAdd;
            }
        }

        numbers.add(number);
    }

    /**
     * Man übergibt eine Nummer und bekommt den Durschnitt der letzten x Nummern zurück.
     *
     * @return Durchschnitt (avg)
     */
    public double getAverage()
    {

        double sum = 0;
        int num = 0;
        for (double d : numbers.getBuffer())
        {
            if (d != 0)
            {
                sum = sum + d;
                num++;
            }
        }
        return sum / num;
    }

    public double getWindorsAverage()
    {
        numbers.sort();

        int num = 0;
        double sum = 0;
        for (int i = windors; i < numbers.size() - windors; i++)
        {
            double n = numbers.get(i);
            if (n != 0)
            {
                sum = sum + n;
                num++;
            }
        }
        if (num > 0)
        {
            sum = sum / num;
        }

        return sum;
    }

    public double getWindorsHarmonicMedian()
    {
        numbers.sort();

        double nenner = 0;
        int num = 0;
        for (int i = windors; i < numbers.size() - windors; i++)
        {
            double n = numbers.get(i);
            if (n != 0)
            {
                nenner = nenner + (1.0 / n);
                num++;
            }
        }
        return num / nenner;
    }

    public double getWindorsOptimized()
    {
        double bpm = getAverage();

        int num = 0;
        double sum = 0;
        for (double n : numbers.getBuffer())
        {
            if (n != 0)
            {
                if (Math.abs(n - bpm) <= this.windorsOptimizedRange)
                {
                    sum = sum + n;
                    num++;
                }
            }
        }
        return sum / num;
    }

    /**
     * harmonisches Mittel
     */
    public double getHarmonicMedian()
    {
        double nenner = 0;
        int num = 0;
        for (double n : numbers.getBuffer())
        {
            if (n != 0)
            {
                nenner = nenner + (1.0 / n);
                num++;
            }
        }

        return num / nenner;

    }

    public double getQuadradicMedian()
    {
        double avg = 0;
        int num = 0;
        for (double n : numbers.getBuffer())
        {
            if (n != 0)
            {
                avg += n * n;
                num++;
            }
        }
        avg = Math.sqrt(avg / num);
        return avg;

    }

    public void stop()
    {
        for (int i = 0; i < numbers.size(); i++)
        {
            numbers.set(i, 0.0);
        }
    }

    public void restart()
    {
        this.stop();
        this.start();
    }


    public int getMaxDiff()
    {
        return maxDiff;
    }

    public void setMaxDiff(int maxDiff)
    {
        this.maxDiff = maxDiff;
    }
}

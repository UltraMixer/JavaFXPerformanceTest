package com.ultramixer.javafxperformancetest.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * If you use this code, please retain this comment block.
 *
 * @author Isak du Preez
 *         isak at du-preez dot com
 *         www.du-preez.com
 */
public class CircularArray<E>
{

    private E[] buffer; // a List implementing RandomAccess
    private int tail = 0;

    public E[] getBuffer()
    {
        return buffer;
    }

    public CircularArray(int capacity, Class<E> type)
    {
        buffer = (E[]) Array.newInstance(type, capacity);
        for (int i = 0; i < capacity; i++)
        {
            buffer[i] = (E) new Double(0);
        }
    }

    public int capacity()
    {
        return buffer.length;
    }

    private int wrapIndex(int i)
    {
        return i % buffer.length;
    }

    public int size()
    {
        return buffer.length;
    }

    public E get(int i)
    {
        return buffer[i];
    }

    public void add(E e)
    {
        tail = wrapIndex(tail);
        set(tail, e);
        tail++;
    }


    public void sort()
    {
        Arrays.sort(buffer);
    }

    public void set(int i, E element)
    {
        buffer[i] = element;
    }
}
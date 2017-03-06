package com.ultramixer.javafxperformancetest;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TB on 18.11.16.
 */
public class CleanFrameTest extends JFrame
{
    public CleanFrameTest() throws HeadlessException
    {
        this.setSize(800, 600);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new CleanFrameTest();
    }
}

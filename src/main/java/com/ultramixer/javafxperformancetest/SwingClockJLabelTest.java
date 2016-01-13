package com.ultramixer.javafxperformancetest;

import com.ultramixer.javafxperformancetest.util.TestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

/**
 * Created by TB on 10.07.15.
 */
public class SwingClockJLabelTest extends JFrame
{
    private JLabel clockLabel;
    private JToggleButton startStopButton;
    private long startTime = 0;
    private JLabel fpsLabel;

    private Logger logger = Logger.getLogger(getClass().getName());
    private Timer clockTimer;


    public SwingClockJLabelTest()
    {
        this.setTitle("Test: Digital Clock");



        /* headline */
        JLabel headlineLabel = new JLabel("Clock via Swing and JLabel");
        headlineLabel.setFont(headlineLabel.getFont().deriveFont(26f));
        headlineLabel.setForeground(new Color(200, 200, 200));
        headlineLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        headlineLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headlinePanel = new JPanel();
        headlinePanel.setOpaque(false);
        headlinePanel.add(headlineLabel);


        /* Clock */
        clockLabel = new JLabel("00:00:00");
        clockLabel.setFont(clockLabel.getFont().deriveFont(120f));
        clockLabel.setForeground(Color.white);

        JPanel clockPanel = new JPanel();
        clockPanel.setOpaque(false);
        clockPanel.add(clockLabel);


        /* StartStop button */
        startStopButton = new JToggleButton("Start/Stop");
        startStopButton.setBackground(new Color(255, 255, 255, 127));
        startStopButton.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (startStopButton.isSelected())
                {
                    startTime = System.currentTimeMillis();
                    clockTimer.start();
                }
                else
                {
                    clockTimer.stop();
                }
            }
        });

        JPanel startStopPanel = new JPanel();
        startStopPanel.setOpaque(false);
        startStopPanel.add(startStopButton);


        /* FPS */
        fpsLabel = new JLabel("FPS:");
        fpsLabel.setFont(fpsLabel.getFont().deriveFont(14f));
        fpsLabel.setForeground(Color.white);


        JPanel fpsPanel = new JPanel();
        fpsPanel.setOpaque(false);
        fpsPanel.add(fpsLabel);


        /* root */
        JPanel root = new JPanel();
        BoxLayout layout = new BoxLayout(root, BoxLayout.Y_AXIS);
        root.setLayout(layout);

        root.setBackground(new Color(33, 33, 33));
        root.add(headlinePanel);
        root.add(clockPanel);
        root.add(startStopPanel);
        root.add(fpsPanel);


        this.getContentPane().add(root);
        this.setSize(640, 480);
        this.setVisible(true);


        this.createClockTimeline();

        this.createPerformanceTracker();

    }

    public static void main(String[] args)
    {
        new SwingClockJLabelTest();

    }

    public void createPerformanceTracker()
    {
    }


    public void createClockTimeline()
    {
        this.clockTimer = new Timer(10, evt -> clockLabel.setText(TestUtils.formatDuration(System.currentTimeMillis() - startTime)));
    }
}

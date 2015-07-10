package com.ultramixer.javafxperformancetest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

/**
 * Created by TB on 10.07.15.
 */
public class SwingClockCanvasTest extends JFrame
{
    private final Canvas clockCanvas;
    private JToggleButton startStopButton;
    private long startTime = 0;
    private JLabel fpsLabel;

    private Logger logger = Logger.getLogger(getClass().getName());
    private Timer clockTimer;
    private String clock;
    private Font clockFont = UIManager.getFont("Label.font");


    public SwingClockCanvasTest()
    {
        this.setTitle("Test: Digital Clock - Swing - Canvas");

        clockFont = clockFont.deriveFont(120f);


        /* headline */
        JLabel headlineLabel = new JLabel("Clock via Swing and Canvas");
        headlineLabel.setFont(headlineLabel.getFont().deriveFont(26f));
        headlineLabel.setForeground(new Color(200, 200, 200));
        headlineLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        headlineLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headlinePanel = new JPanel();
        headlinePanel.setOpaque(false);
        headlinePanel.add(headlineLabel);


        /* Clock */
        this.clockCanvas = new Canvas()
        {
            @Override
            public void paint(Graphics g)
            {
                if (clock != null)
                {
                    Graphics2D g2d = (Graphics2D) clockCanvas.getGraphics();
                    g2d.setColor(Color.white);
                    g2d.setFont(clockFont);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                   // g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    g2d.drawString(clock, 10, 100);
                    g2d.dispose();
                }
            }
        };
        clockCanvas.setPreferredSize(new Dimension(640, 300));


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
        root.add(clockCanvas);
        root.add(startStopPanel);
        root.add(fpsPanel);


        this.getContentPane().add(root);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        this.createClockTimeline();

        this.createPerformanceTracker();

    }

    public static void main(String[] args)
    {
        new SwingClockCanvasTest();

    }

    public void createPerformanceTracker()
    {
    }


    public void createClockTimeline()
    {
        this.clockTimer = new Timer(10, (ActionEvent evt) -> {
            clock = TestUtils.formatDuration(System.currentTimeMillis() - startTime);
            clockCanvas.repaint();
        });


    }
}

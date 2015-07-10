package com.ultramixer.javafxperformancetest;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasPerformance extends Application
{

    public static final int CYCLE_TIME_MS = 10000;
    public static final double CYCLE_TIME_NORM_FACTOR = 1d / CYCLE_TIME_MS;
    private AnimationTimer timer;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        DemoCanvas canvas = new DemoCanvas();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);

        canvas.widthProperty().bind(
                stackPane.widthProperty());
        canvas.heightProperty().bind(
                stackPane.heightProperty());

        final Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setTitle(getClass().getSimpleName());
        stage.show();

        final PerformanceTracker tracker = PerformanceTracker.getSceneTracker(scene);

        timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                long nowMS = now / 1000000;
                double cycleOffset = (nowMS % CYCLE_TIME_MS) * CYCLE_TIME_NORM_FACTOR;
                canvas.fill = cycleOffset;
                canvas.draw();
                canvas.currentFPS = tracker.getInstantFPS();
            }
        };
        timer.start();
    }

    @Override
    public void stop() throws Exception
    {
        timer.stop();
    }

    static class DemoCanvas extends Canvas
    {

        private static final Color CIRCLE_BASE_FILL_COLOR = Color.LIGHTSKYBLUE;
        private static final Color OUTLINE_COLOR = Color.GRAY;
        private static final Color BACKGROUND_COLOR = Color.WHITE;
        public double fill = 0;
        private double currentFPS;

        public DemoCanvas()
        {
            // Redraw canvas when size changes.
            widthProperty().addListener(evt -> draw());
            heightProperty().addListener(evt -> draw());
        }

        private void draw()
        {
            final double h = getHeight();
            final double w = getWidth();

            final double cx = w / 2;
            final double cy = h / 2;

            final double outerRadius = Math.min(w / 2, h / 2);
            final double innerRadius = outerRadius * 0.2;
            final double range = outerRadius - innerRadius;

            final double currentRadius = innerRadius + range * fill;

            final GraphicsContext context = getGraphicsContext2D();


            context.setFill(BACKGROUND_COLOR);
            context.fillRect(0, 0, w, h);

            context.setFill(CIRCLE_BASE_FILL_COLOR);
            context.fillOval(cx - currentRadius, cy - currentRadius, currentRadius * 2, currentRadius * 2);

            context.setStroke(OUTLINE_COLOR);

            context.strokeOval(cx - innerRadius, cy - innerRadius, innerRadius * 2, innerRadius * 2);
            context.strokeOval(cx - currentRadius, cy - currentRadius, currentRadius * 2, currentRadius * 2);

            context.strokeText("size " + (int) w + "x" + (int) h + ", fps " + currentFPS, 10, cy);
        }

        @Override
        public boolean isResizable()
        {
            return true;
        }

        @Override
        public double prefWidth(double height)
        {
            return getWidth();
        }

        @Override
        public double prefHeight(double width)
        {
            return getHeight();
        }
    }
}
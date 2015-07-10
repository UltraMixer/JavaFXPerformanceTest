package com.ultramixer.javafxperformancetest;


import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NodeBasedAnimationPerformance extends Application
{

    public static final int CYCLE_TIME_MS = 10000;
    public static final double CYCLE_TIME_NORM_FACTOR = 1d / CYCLE_TIME_MS;
    private static final Color CIRCLE_BASE_FILL_COLOR = Color.LIGHTSKYBLUE;
    private static final Color OUTLINE_COLOR = Color.GRAY;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private AnimationTimer timer;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {

        StackPane stackPane = new StackPane();
        final Circle outerCircle = new Circle();
        outerCircle.setStroke(OUTLINE_COLOR);
        outerCircle.setFill(CIRCLE_BASE_FILL_COLOR);
        stackPane.getChildren().add(outerCircle);
        final Text displayText = new Text();
        stackPane.getChildren().add(displayText);

        stackPane.setSnapToPixel(false);


        final Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setTitle(getClass().getSimpleName());
        stage.setWidth(200);
        stage.setHeight(200);
        stage.show();

        final PerformanceTracker tracker = PerformanceTracker.getSceneTracker(scene);

        timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                long nowMS = now / 1000000;
                double cycleOffset = (nowMS % CYCLE_TIME_MS) * CYCLE_TIME_NORM_FACTOR;
                double fill = cycleOffset;
                final double h = stackPane.getHeight();
                final double w = stackPane.getWidth();

                final double cx = w / 2;
                final double cy = h / 2;

                final double outerRadius = Math.min(w / 2, h / 2);
                final double innerRadius = outerRadius * 0.2;
                final double range = outerRadius - innerRadius;

                final double currentRadius = innerRadius + range * fill;

                displayText.setText("size " + (int) w + "x" + (int) h + ", fps " + tracker.getInstantFPS());

                outerCircle.setRadius(currentRadius);
            }
        };
        timer.start();
    }

    @Override
    public void stop() throws Exception
    {
        timer.stop();
    }
}
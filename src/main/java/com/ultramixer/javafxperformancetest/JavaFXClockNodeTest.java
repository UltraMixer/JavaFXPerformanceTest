package com.ultramixer.javafxperformancetest;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.logging.Logger;


/**
 * Created by TB on 08.07.15.
 */
public class JavaFXClockNodeTest extends Application
{
    private Timeline clockTimeline;
    private Label clockLabel;
    private ToggleButton startStopButton;
    private long startTime = 0;
    private Label fpsLabel;
    private PerformanceTracker tracker;

    private Logger logger = Logger.getLogger(getClass().getName());
    private Label headlineLabel;


    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Test: Digital Clock");

        headlineLabel = new Label("Clock via JavaFX and Nodes");
        headlineLabel.setStyle("-fx-font-size: 26px;-fx-text-fill: #AAA;");

        clockLabel = new Label("00:00:000");
        clockLabel.setManaged(true);
        clockLabel.setCacheHint(CacheHint.SPEED);
        clockLabel.setStyle("-fx-font-size: 10em;-fx-text-fill: white;");

        startStopButton = new ToggleButton("Start/Stop");
        startStopButton.setStyle("-fx-background-color: rgba(255,255,255,.4)");
        startStopButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    startTime = System.currentTimeMillis();
                    clockTimeline.play();
                }
                else
                {
                    clockTimeline.stop();
                }
            }
        });

        fpsLabel = new Label("FPS:");
        fpsLabel.setStyle("-fx-font-size: 1em;-fx-text-fill: white;");


        VBox root = new VBox();
        root.setStyle("-fx-background-color: #333");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(headlineLabel,clockLabel, startStopButton, fpsLabel);

        VBox.setMargin(fpsLabel, new Insets(20, 0, 0, 0));


        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();


        this.createClockTimeline();

        this.createPerformanceTracker(scene);

        // clockLabel.setCache(false);
        // clockLabel.setCacheShape(true);


    }

    public void createPerformanceTracker(Scene scene)
    {
        tracker = PerformanceTracker.getSceneTracker(scene);
        AnimationTimer frameRateMeter = new AnimationTimer()
        {

            @Override
            public void handle(long now)
            {
                float fps = getFPS();
                fpsLabel.setText(String.format("Current frame rate: %.0f fps", fps));

            }
        };

        //frameRateMeter.start();
    }

    private float getFPS()
    {
        float fps = tracker.getAverageFPS();
        tracker.resetAverageFPS();
        return fps;
    }

    public void createClockTimeline()
    {
        this.clockTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent t)
                    {
                        clockLabel.setText(TestUtils.formatDuration(System.currentTimeMillis() - startTime));
                    }
                }));
        clockTimeline.setCycleCount(Timeline.INDEFINITE);


    }
}

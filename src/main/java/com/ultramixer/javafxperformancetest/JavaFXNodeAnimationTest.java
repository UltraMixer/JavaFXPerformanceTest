package com.ultramixer.javafxperformancetest;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by TB on 08.07.15.
 */
public class JavaFXNodeAnimationTest extends Application
{
    public static final int NO_ANIMATION_NODES = 5;


    private ToggleButton startStopButton;
    private long startTime = 0;
    private Label fpsLabel;
    private PerformanceTracker tracker;

    private Logger logger = Logger.getLogger(getClass().getName());
    private Label headlineLabel;

    private Scene scene;
    private ArrayList<Node> animationNodes = new ArrayList<Node>();
    private ArrayList<TranslateTransition> animationTransitions = new ArrayList<TranslateTransition>();
    private Image image;


    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Test: Node Animation");

        headlineLabel = new Label("Animation via JavaFX and Nodes");
        headlineLabel.setStyle("-fx-font-size: 26px;-fx-text-fill: #AAA;");

        this.image = new Image("http://www.ich-und-so-weiter.de/wp-content/uploads/2014/06/Fußball.png");

        for (int i = 0; i < NO_ANIMATION_NODES; i++)
        {
            Node node = createAnimationNode();
            animationNodes.add(node);
            animationTransitions.add(createTransition(node));
        }


        startStopButton = new ToggleButton("Start/Stop");
        startStopButton.setStyle("-fx-background-color: rgba(255,255,255,.4)");
        startStopButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    startTime = System.currentTimeMillis();
                    for (TranslateTransition tt : animationTransitions)
                    {
                        tt.play();
                    }
                }
                else
                {
                    for (TranslateTransition tt : animationTransitions)
                    {
                        tt.stop();
                    }
                }
            }
        });

        fpsLabel = new Label("FPS:");
        fpsLabel.setStyle("-fx-font-size: 1em;-fx-text-fill: white;");


        VBox root = new VBox();
        root.setStyle("-fx-background-color: #333");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(headlineLabel);
        for (Node node : animationNodes)
        {
            root.getChildren().add(node);
        }
        root.getChildren().addAll(startStopButton, fpsLabel);

        VBox.setMargin(fpsLabel, new Insets(20, 0, 0, 0));


        this.scene = new Scene(root, 640, 680);
        stage.setScene(scene);
        stage.show();


        this.createPerformanceTracker(scene);

        // clockLabel.setCache(false);
        // clockLabel.setCacheShape(true);


    }

    private ImageView createAnimationNode()
    {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(100);
        iv.setFitHeight(100);
        iv.setManaged(true);
        iv.setCacheHint(CacheHint.SPEED);
        iv.setCache(true);
        return iv;
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

    private TranslateTransition createTransition(Node node)
    {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), node);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setToX(640);
        tt.setFromX(-320);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.setAutoReverse(true);
        return tt;
    }

}

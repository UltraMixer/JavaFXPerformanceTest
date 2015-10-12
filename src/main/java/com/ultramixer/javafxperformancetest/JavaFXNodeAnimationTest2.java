package com.ultramixer.javafxperformancetest;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by TB on 08.07.15.
 */
public class JavaFXNodeAnimationTest2 extends Application
{
    public static final int NO_ANIMATION_NODES = 1;


    private long startTime = 0;
    private PerformanceTracker tracker;

    private Logger logger = Logger.getLogger(getClass().getName());

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


        this.image = new Image("http://www.ich-und-so-weiter.de/wp-content/uploads/2014/06/Fußball.png");

        ImageView node = createAnimationNode();

        animationTransitions.add(createTransition(node));

        StackPane sp = new StackPane();
        sp.setAlignment(Pos.BASELINE_RIGHT);
        sp.getChildren().add(node);
        sp.setCache(false);

        this.scene = new Scene(sp, 640, 680);
        stage.setScene(scene);
        stage.show();


        Timeline tt = new Timeline(100);
        tt.getKeyFrames().add(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                double x = node.getTranslateX()-1;
                if(Math.abs(x)> scene.getWidth())
                {
                    x = 0;
                }
                node.setTranslateX(x);
            }
        }));
        tt.setCycleCount(-1);
        tt.play();



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


    private TranslateTransition createTransition(Node node)
    {
        /*
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), node);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setToX(640);
        tt.setFromX(-320);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.setAutoReverse(true);

        AnimationTimer a = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                node.setTranslateX(-1);
            }
        };
        return tt;
        */
        return null;

    }

}

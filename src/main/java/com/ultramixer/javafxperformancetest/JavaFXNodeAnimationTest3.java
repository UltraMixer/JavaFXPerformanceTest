package com.ultramixer.javafxperformancetest;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by TB on 15.01.16.
 */
public class JavaFXNodeAnimationTest3 extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Circle c = new Circle();
        c.setFill(Color.RED);
        c.setStrokeWidth(2);
        c.setRadius(64);
        c.setCenterX(64);
        c.setCenterY(64);
        c.setCache(true);
        c.setCacheHint(CacheHint.SPEED);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), c);
        tt.setToX(600);
        tt.setAutoReverse(true);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.play();


        primaryStage.setScene(new Scene(new Pane(c), 800, 200));
        primaryStage.show();
    }
}

package com.ultramixer.javafxperformancetest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by TB on 09.10.15.
 */
public class WebViewYoutubeTest extends Application
{
    private Stage primaryStage;

    public WebViewYoutubeTest()
    {

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        WebView webView = new WebView();
        webView.getEngine().load("https://www.youtube.com/watch?v=b-Cr0EWwaTk");

        Scene scene = new Scene(webView,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        startTest();
    }

    private void startTest()
    {
        Timeline tt = new Timeline();
        tt.getKeyFrames().add(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                double w = ((Math.random()*10000) % 400)+400;
                primaryStage.setMaxWidth(w);
                primaryStage.setMinWidth(w);

                double h = ((Math.random() * 10000) % 300) + 300;
                primaryStage.setMaxHeight(h);
                primaryStage.setMinHeight(h);
            }
        }));
        tt.setCycleCount(-1);
        tt.play();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

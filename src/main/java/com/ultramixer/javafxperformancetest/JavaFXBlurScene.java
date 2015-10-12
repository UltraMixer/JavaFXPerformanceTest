package com.ultramixer.javafxperformancetest;

import com.sun.scenario.effect.BoxBlur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by TB on 10.08.15.
 */
public class JavaFXBlurScene extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        StackPane sp = new StackPane();
        sp.setStyle(String.format("-fx-background-image: url(\"%s\");","http://gutwald.de/wp-content/uploads/2012/10/iStock_000016500085XSmall-sch%C3%B6ne-Frau-Z%C3%A4hne.jpg"));


        AnchorPane ap1 = new AnchorPane();


        Button blurMe = new Button("Blur me");
        Label l = new Label("Blur Demo");
        ap1.getChildren().addAll(blurMe);

        //pane.setStyle("-fx-opacity: .8;-fx-background-color: white;");
        javafx.scene.effect.BoxBlur blurEffect = new javafx.scene.effect.BoxBlur();
        blurEffect.setWidth(5);
        blurEffect.setHeight(5);
        blurEffect.setIterations(5);


        sp.getChildren().addAll(ap1);
//        sp.getChildren().addAll(ap1);

        Scene scene = new Scene(sp,320,480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

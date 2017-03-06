package com.ultramixer.javafxperformancetest;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

/**
 * Created by tobi on 03.03.2017.
 */
public class TestTransparentWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group group = new Group();
        Button b = new Button("TEST");

        group.getChildren().add(b);
        StackPane root = new StackPane();
      //  root.setBackground(Background.EMPTY);

        javafx.scene.shape.Rectangle rect =  new Rectangle(0,0,500,500);
        rect.setFill(Color.RED);
        root.setStyle("-fx-background-color: transparent;-fx-border-color: red;-fx-border-width:50;-fx-border-radius: 50px;");
        TransparentNode transNode = new TransparentNode();
        transNode.setStyle("-fx-background-color: transparent;-fx-min-width: 100px;-fx-min-height: 100px;-fx-max-width: 100px;-fx-max-height: 100px;");
        root.getChildren().add(transNode);

        Scene scene = new Scene(root,800,600, Color.TRANSPARENT);


        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}


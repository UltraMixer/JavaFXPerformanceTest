package com.ultramixer.javafxperformancetest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by TB on 18.11.16.
 */
public class CleanStageTest extends Application
{
    public CleanStageTest()
    {

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

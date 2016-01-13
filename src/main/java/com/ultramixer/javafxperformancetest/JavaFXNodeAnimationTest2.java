package com.ultramixer.javafxperformancetest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;


/**
 * Created by TB on 08.07.15.
 */
public class JavaFXNodeAnimationTest2 extends Application
{
    private Logger logger = Logger.getLogger(getClass().getName());
    private Scene scene;
    private Parent root;

    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Test: Node Animation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ultramixer/javafxperformancetest/JavaFXNodeAnimationTest2.fxml"));
        this.root = loader.load();

        this.scene = new Scene(root, 640, 200);
        ((JavaFXNodeAnimationTest2Controller) loader.getController()).setScene(scene);

        stage.setScene(scene);
        stage.show();


    }


}

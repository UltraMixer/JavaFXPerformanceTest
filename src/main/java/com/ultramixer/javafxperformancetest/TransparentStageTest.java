package com.ultramixer.javafxperformancetest;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by TB on 06.03.17.
 */
public class TransparentStageTest extends Application
{
    private StackPane root;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Button b = new Button("JavaFX");

        root = new StackPane();
        root.setMouseTransparent(true);
        root.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                updateMask();

            }
        });



        root.setBackground(Background.EMPTY);


        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);

        //primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
         primaryStage.initStyle(StageStyle.TRANSPARENT);
         primaryStage.setMaximized(true);
        primaryStage.show();

        updateMask();
    }

    private void updateMask()
    {
        Rectangle rect = new Rectangle(0, 0, root.getWidth(), root.getHeight());
        //rect.widthProperty().bind(root.widthProperty());
        //rect.heightProperty().bind(root.heightProperty());

        Circle circle = new Circle(100);
        circle.setLayoutX(rect.getWidth() / 2);
        circle.setLayoutY(rect.getHeight() / 2);

        Circle circle2 = new Circle(50);
        circle2.setLayoutX(rect.getWidth()/2-200);
        circle2.setLayoutY(rect.getHeight()/2+100);

        Shape shape = Shape.subtract(rect, circle);
        shape = Shape.subtract(shape, circle2);
        shape.setFill(Color.BLACK);
        shape.setMouseTransparent(true);

        root.getChildren().clear();
        root.getChildren().addAll(shape);
    }
}

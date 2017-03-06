package com.ultramixer.javafxperformancetest;

import com.jprotechnologies.jpro.extensions.JProProgressIndicator;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by TB on 01.03.17.
 */
public class ProgressIndicatorPerformanceTest extends Application
{
    public ProgressIndicatorPerformanceTest()
    {

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        StackPane sp = new StackPane();
        ToggleButton showPIButton = new ToggleButton("Show/Hide ProgressIndicator");
        showPIButton.setTranslateY(100);
        JProProgressIndicator pi = new JProProgressIndicator();

        pi.setMaxWidth(40);
        pi.setMaxHeight(40);
        showPIButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                pi.setVisible(newValue);
            }
        });

        sp.getChildren().addAll(pi, showPIButton);

        Scene scene = new Scene(sp, 800, 600);
        primaryStage.setScene(scene);

        pi.setVisible(false);
        pi.setProgress(-1);
        
        primaryStage.show();


    }
}

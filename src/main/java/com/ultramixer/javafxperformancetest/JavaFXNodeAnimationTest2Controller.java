package com.ultramixer.javafxperformancetest;

import com.bley.java.lang.Lang_HC;
import com.sun.javafx.perf.PerformanceTracker;
import com.ultramixer.util.MathAverage_HC;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by TB on 12.01.16.
 */
public class JavaFXNodeAnimationTest2Controller implements Initializable
{
    private static final double TEST_DURATION = 30;
    private static final double NODE_SIZE = 128;
    public Pane animationPane;
    public CheckBox useCachingCheckBox;
    public ToggleButton playStopButton;
    public Label cpuValue;
    public Label cpuAvgValue;
    private ArrayList<Node> animationNodes = new ArrayList<Node>();
    private ArrayList<TranslateTransition> animationTransitions = new ArrayList<TranslateTransition>();
    private Image movingImage;
    private Node movingNode;
    private Scene scene;
    private Sigar sigar;
    private boolean stopMonitoring = false;
    private String cpuUsage = "";
    private Double testCpuUsage = null;

    private long refreshRate = 1000;

    private Logger logger = Logger.getLogger(getClass().getName());
    private long pid;
    private MathAverage_HC cpuLoggerAverage;
    private Timeline cpuLogger;
    private Timeline tt;

    public Scene getScene()
    {
        return scene;
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
        this.tracker = PerformanceTracker.getSceneTracker(scene);

    }

    public static final int NO_ANIMATION_NODES = 1;

    private long startTime = 0;
    private PerformanceTracker tracker;


    private AnimationTimer a;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        cpuLoggerAverage = new MathAverage_HC(10);

        //this.movingImage = new Image("http://www.ich-und-so-weiter.de/wp-content/uploads/2014/06/Fußball.png");
        this.movingImage = new Image(getClass().getResource("/test.jpg").toExternalForm());
        System.out.println("movingImage = " + movingImage);


        //this.movingNode = createAnimationImageNode();
        this.movingNode = createAnimationRegionNode();
        this.movingNode.cacheProperty().bind(useCachingCheckBox.selectedProperty());

        //animationTransitions.add(createTransition(movingNode));

        if (animationPane instanceof StackPane)
        {
            ((StackPane) animationPane).setAlignment(Pos.TOP_LEFT);
        }
        else if (animationPane instanceof NullLayoutPane)
        {
            animationPane.setMinWidth(100);
            animationPane.setMinHeight(100);

        }
        this.animationPane.getChildren().add(movingNode);

        this.createAnimation();

        this.playStopButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    a.start();
                }
                else
                {
                    a.stop();
                }
            }
        });

        this.initSigar();


        // root.setCache(true);
        //root.setCacheHint(CacheHint.SPEED);
    }

    private void createAnimation()
    {

        Timeline tt = new Timeline();
        tt.getKeyFrames().add(new KeyFrame(Duration.millis(1000.0 / 60.0), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                double x = movingNode.getTranslateX() + 1;
                if (Math.abs(x) > scene.getWidth())
                {
                    x = 0;
                }
                movingNode.setTranslateX(x);
            }
        }));
        tt.setCycleCount(-1);
        // tt.play();


        a = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                double x = movingNode.getTranslateX() + 1;
                if (Math.abs(x) > scene.getWidth())
                {
                    x = 0;
                }
                movingNode.setTranslateX(x);

                //  cpuValue.setText(String.format("%f02",tracker.getAverageFPS()));
            }
        };
    }


    private Region createAnimationRegionNode()
    {
        Region r = new Region();
        r.setMaxSize(NODE_SIZE, NODE_SIZE);
        r.setMinSize(NODE_SIZE, NODE_SIZE);
        r.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
        //r.setStyle("-fx-background-color: red;");
        r.cacheProperty().bind(useCachingCheckBox.selectedProperty());
        r.setCacheHint(CacheHint.SPEED);
        return r;
    }

    private ImageView createAnimationImageNode()
    {
        ImageView iv = new ImageView(movingImage);
        iv.setFitWidth(NODE_SIZE);
        iv.setFitHeight(NODE_SIZE);
        iv.setManaged(true);
        iv.setCacheHint(CacheHint.SPEED);
        iv.setCache(true);
        return iv;
    }

    private void initSigar()
    {
        this.sigar = new Sigar();

        pid = sigar.getPid();

        Thread t = new Thread()
        {
            public void run()
            {
                while (!stopMonitoring)
                {
                    try
                    {
                        ProcCpu cpu = sigar.getProcCpu(pid);
                        double percent = cpu.getPercent() * 100;
                        Platform.runLater(() -> cpuValue.setText(Lang_HC.round(percent, 1) + " %"));
                    }
                    catch (SigarException e)
                    {
                        logger.log(Level.WARNING, "", e);
                    }

                    try
                    {
                        Thread.sleep(refreshRate);
                    }
                    catch (InterruptedException e)
                    {
                        logger.log(Level.WARNING, "", e);
                    }
                }
            }
        };
        t.start();
    }


    public void handleStartTest(ActionEvent actionEvent)
    {
        tt = new Timeline();
        tt.getKeyFrames().add(new KeyFrame(Duration.seconds(TEST_DURATION), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                cpuLogger.stop();
                a.stop();
                cpuAvgValue.setText((com.bley.java.lang.Lang_HC.round(cpuLoggerAverage.getHarmonicMedian() * 100, 1)) + " %");

                System.out.println("cpuLoggerAverage.getHarmonicMedian() = " + cpuLoggerAverage.getHarmonicMedian());
                System.out.println("cpuLoggerAverage.getQuadradicMedian() = " + cpuLoggerAverage.getQuadradicMedian());
                System.out.println("cpuLoggerAverage.getWindorsAverage() = " + cpuLoggerAverage.getWindorsAverage());
                System.out.println("cpuLoggerAverage.getWindorsHarmonicMedian() = " + cpuLoggerAverage.getWindorsHarmonicMedian());
                System.out.println("cpuLoggerAverage.getWindorsOptimized() = " + cpuLoggerAverage.getWindorsOptimized());
            }
        }));


        cpuLogger = new Timeline();
        cpuLogger.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                ProcCpu cpu = null;
                try
                {
                    cpu = sigar.getProcCpu(pid);

                    cpuLoggerAverage.addNumber(cpu.getPercent());
                    cpuAvgValue.setText((com.bley.java.lang.Lang_HC.round(cpuLoggerAverage.getHarmonicMedian() * 100, 1)) + " %");
                }
                catch (SigarException e)
                {
                    e.printStackTrace();
                }

            }
        }));
        cpuLogger.setCycleCount(Animation.INDEFINITE);

        a.start();

        cpuLoggerAverage.restart();
        cpuLogger.play();
        tt.play();


    }
}

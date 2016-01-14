package com.ultramixer.javafxperformancetest;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by TB on 04.01.16.
 */
public class NullLayoutPane extends Pane
{
    public NullLayoutPane()
    {
        //this.setManaged(false);
    }

    @Override
    public boolean isResizable()
    {
        return false;
    }

    @Override
    //wird beim child.setLayoutX() ständig aufgerufen!
    protected void updateBounds()
    {
        //System.out.println("updateBounds");
       // super.updateBounds();
    }


    @Override
    //wird beim child.setLayoutX() ständig aufgerufen!
    public void requestLayout()
    {
        System.out.println("NullLAyoutPane: request layout...");
        //  super.requestLayout();
    }

    @Override
    protected void layoutChildren()
    {
         System.out.println("layoutChildren");
        super.layoutChildren();
        //getChildren().get(0).autosize();
    }

    @Override
    protected void layoutInArea(Node child, double areaX, double areaY, double areaWidth, double areaHeight, double areaBaselineOffset, HPos halignment, VPos valignment)
    {
       // super.layoutInArea(child, areaX, areaY, areaWidth, areaHeight, areaBaselineOffset, halignment, valignment);
    }

    @Override
    protected void layoutInArea(Node child, double areaX, double areaY, double areaWidth, double areaHeight, double areaBaselineOffset, Insets margin, HPos halignment, VPos valignment)
    {
       // super.layoutInArea(child, areaX, areaY, areaWidth, areaHeight, areaBaselineOffset, margin, halignment, valignment);
    }

    @Override
    protected void layoutInArea(Node child, double areaX, double areaY, double areaWidth, double areaHeight, double areaBaselineOffset, Insets margin, boolean fillWidth, boolean fillHeight, HPos halignment, VPos valignment)
    {
        //super.layoutInArea(child, areaX, areaY, areaWidth, areaHeight, areaBaselineOffset, margin, fillWidth, fillHeight, halignment, valignment);
    }

    @Override
    public void resize(double width, double height)
    {
        System.out.println("resiuze");
        //super.resize(width, height);
    }

    @Override
    protected double computePrefWidth(double height)
    {
        System.out.println("computePrefWidth");
        return super.computePrefWidth(height);
    }

    @Override
    protected void positionInArea(Node child, double areaX, double areaY, double areaWidth, double areaHeight, double areaBaselineOffset, HPos halignment, VPos valignment)
    {
       // super.positionInArea(child, areaX, areaY, areaWidth, areaHeight, areaBaselineOffset, halignment, valignment);
    }

    @Override
    public void resizeRelocate(double x, double y, double width, double height)
    {
      //  super.resizeRelocate(x, y, width, height);
    }
}

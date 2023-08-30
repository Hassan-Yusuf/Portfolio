package com.company;
import javax.swing.*;

public class plot extends JComponent{
    int width = 600, height = 600;
    double xmin=0 ,xmax=1, ymin=0, ymax=1;
    public int scaleX(double x){
        x +=180;
        if(x>360){
            x-=360;
        }
        return (int) (width*(x-xmin)/(xmax-xmin));
    }
    public int scaleY(double y){
        return (int) (width*(ymin-y)/(ymax-ymin)+height);

    }
    public void setScaleX(double min, double max) {
        xmin = min;
        xmax = max;
    }
    public void setScaleY(double min, double max){
        ymin = min;
        ymax=max;
    }
}

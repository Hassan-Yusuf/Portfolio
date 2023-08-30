package com.company;

import java.awt.*;
import java.io.FileNotFoundException;

public class plotMap extends plot {
    Earth earth1;
    int blocksize = 1;

    public plotMap(String filename) throws FileNotFoundException {
        earth1 = new Earth();
        earth1.readDataArray(filename);

        setScaleX(-2, 362);
        setScaleY(-92, 92);

    }
    public plotMap(double [][] array){
        earth1 = new Earth();
        earth1.arrayOfEarth=array;

        setScaleX(-2, 362);
        setScaleY(-92, 92);
    }
    public void paintComponent(Graphics g) {
        double x, y, alt;
        Graphics2D g2d = (Graphics2D) g;
        this.width = getWidth();
        this.height = getHeight();
        for (int i = 0; i < earth1.arrayOfEarth.length; i++) {
            x = earth1.arrayOfEarth[i][0];
            y = earth1.arrayOfEarth[i][1];
            alt = earth1.arrayOfEarth[i][2];
            if (alt < 0) {
                int grad = (int) alt / 75;
                g.setColor(new Color(0, 0, 170 + grad));
            } else {
                int grad = (int) alt / 75;
                g.setColor(new Color(0, 170 - grad, 0));
            }
            g.fillRect(scaleX(x), scaleY(y), blocksize, blocksize);
        }
    }
}
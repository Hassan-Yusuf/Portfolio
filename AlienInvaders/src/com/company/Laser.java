package com.company;

import javax.swing.ImageIcon;

public class Laser extends gameObject {

    public Laser() {
    }

    public Laser(int x, int y) {

        initBullet(x, y);
    }

    private void initBullet(int x, int y) {

        var bulletImage = "res/Laser.png";
        var tempImage = new ImageIcon(bulletImage);
        setImage(tempImage.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}

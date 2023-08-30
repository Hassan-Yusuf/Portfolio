package com.company;

import javax.swing.ImageIcon;

public class asteroid extends gameObject {

    public asteroid(int x, int y) { initAsteroid(x, y); }

    private void initAsteroid(int x, int y){
        var tempImage = new ImageIcon(AlienInvaders.asteroidImage);
        setImage(tempImage.getImage());
        setX(x);
        setY(y);

    }

}

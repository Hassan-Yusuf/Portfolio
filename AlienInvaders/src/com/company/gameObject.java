package com.company;

import java.awt.Image;

public class gameObject {
    int x;int y;int vectorX;
    private Image image;
    private boolean visible;
    private boolean dying;


    public void die() {

        visible = false;
    }
    public gameObject() {

        visible = true;
    }



    protected void setVisible() {

        this.visible = true;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public boolean isVisible() {

        return visible;
    }


    public void setX(int x) {
        this.x = x; }

    public void setY(int y) {
        this.y = y; }

    public int getY() {
        return y; }

    public int getX() {
        return x; }
    public Image getImage() { return image; }
    public boolean isKilled() {

        return this.dying;
    }
    public void kill(boolean dying) {

        this.dying = dying;
    }


}
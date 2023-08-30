package com.company;


import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class Player extends gameObject {

    private int width;
                                        //self explanatory
    public Player() {
        initPlayer();
    }

    private void initPlayer() {

        var playerImg = "res/player.png";
        var tempIcon = new ImageIcon(playerImg);
                                                //sets image to player image in file
        width = AlienInvaders.PLAYER_W;
        setImage(tempIcon.getImage());

        int START_Y = 290;
        setY(START_Y);
        int START_X = 200; //where player starts
        setX(START_X);
    }

    public void doActions() {

        x = x + vectorX;

        if (x <= 2) {  //moves character for how much vectorX is until it reaches edges

            x = 2;
        }

        if (x <= (int)(AlienInvaders.B_WIDTH - width*1.5)) {
            return;
        }

        x = (int) (AlienInvaders.B_WIDTH - width*1.5);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { //sets direction to go if left or right key pressed

            vectorX = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            vectorX = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { //if not pressing left or right doesn't move.

            vectorX = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            vectorX = 0;
        }
    }

}

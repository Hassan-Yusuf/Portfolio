package com.company;

import javax.swing.ImageIcon;

public class Invader extends gameObject {

    private Invader.invaderAttack invaderAttack;

    public Invader(int x, int y) {
        initInvader(x, y);
    }

    private void initInvader(int x, int y) {

        invaderAttack = new invaderAttack(x, y);
        ImageIcon ii = new ImageIcon(AlienInvaders.invaderImg); //initialises our invader, sets image and x,y
        setImage(ii.getImage());    //and initialises the attacks
        setX(x);
        setY(y);
    }

    public void act(int direction) {

        this.x += direction; //movement of alien
    }

    public Invader.invaderAttack getInvaderAttack() {

        return invaderAttack; //instantiates alien attack
    }

    public static class invaderAttack extends gameObject {

        private boolean destroyed;

        public invaderAttack(int x, int y) {  //this creates and sets up alien attack

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            kill(true);

            this.x = x;
            this.y = y;

            var bombImg = "res/alienattack.png"; //handles our alienAttack image and x,y.
            var ii = new ImageIcon(bombImg);
            setImage(ii.getImage());
        }
        public boolean isKilled() {

            return destroyed;
        }                                       //self explanatory
        public void kill(boolean destroyed) {

            this.destroyed = destroyed;
        }

    }
}
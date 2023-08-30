package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AlienInvaders extends JFrame {
    public static AlienInvaders mainLoop;
    //public static int clock = 0;
    public static String asteroidImage = "res/asteroid.png"; //file locations of images
    public static String invaderImg = "res/alien.png";

    //List of Constants
    public static int B_WIDTH = 500;
    public static int B_HEIGHT = 500; //width and height of game board
    public static int RIGHT_BORDER = 30; //border sizes of game board
    public static int LEFT_BORDER = 5;
    public static Dimension mainMenuDimension = new Dimension(800, 600);

    public static int GROUND = 300; //where the ground is

    public static int BOMB_HEIGHT = 9; //height of bomb;

    public static int ALIEN_INIT_X = 150; //where the aliens initially start from
    public static int ALIEN_INIT_Y = 5;

    public static int GO_DOWN = 14; //how much alien goes down by
    public static int CHANCE = 5;
    public static int DELAY = 18;
    public static int ALIEN_H = 25;
    public static int ALIEN_W = 25; //self explanatory here
    public static int PLAYER_W = 25;
    public static int PLAYER_H = 16;
    public static int GARBAGE_W = 25;
    public static int GARBAGE_H = 25;
    public static int ASTEROID_H = 25;
    public static int ASTEROID_W = 25;
    public static int LASER_H = 5;
    public static int LASER_W = 1;


    public AlienInvaders(){
        setTitle("Alien Invaders");setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);setLocation(500,250); //our JFrame settings
        setSize(800,600);
        mainMenu();
        soundManager.play(soundManager.music);
    }
    public void mainMenu(){
        setSize(800,600); //mainMenu tab
        add(new mainMenu());
    }
    public void GameLoop(){ //starts the game and game music
        add(new View());
        setSize(AlienInvaders.B_WIDTH, AlienInvaders.B_HEIGHT);
        soundManager.play(soundManager.gameMusic);
    }
    public void highScores() throws FileNotFoundException { //highScores tab
        setSize(800,600);
        add(new highScores());
    }
    public void gameOver(int score) { //gameOver tab and game music handling
        soundManager.stop(soundManager.gameMusic);
        add(new gameOver(score));
        soundManager.play(soundManager.gameOver);

    }
    public static void main(String[] args) { //initialises AlienInvaders!
            mainLoop = new AlienInvaders();
            mainLoop.setVisible(true);
    }

}

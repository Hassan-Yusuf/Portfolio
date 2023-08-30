package com.company;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.company.AlienInvaders.mainLoop;


public class View extends JPanel {
    public void emptyGrid(JPanel object){
        if(object!=null){
            this.remove(object); //empties our JPanel
        }
    }
    //variables
    public int asteroidVelocity=2;
    public int clock = 0;
    public int score = 0; //self-explanatory
    public int level = 1;
    private Color currentColour = Color.GRAY;
    private Dimension dim;
    private Player player;
    private garbage garbage1;
    private int dir = -1;
    private Laser laser;
    private ArrayList<asteroid> asteroids;
    private ArrayList<Invader> invaders;
    public int colour1,colour2,colour3; //this is for handling of changing font Colour to rainbow

    public boolean drawCollected = false;//whether to draw garbage is collected
    public boolean isCollected= false; //whether garbage is collected
    public boolean inGameLoop = true;
    public boolean alienTime = false; //whether it has reached the boss
    private final String boomImage = "res/boom.png";
    private final String collectedImage = "res/collected.jpg";
    ImageIcon tempCollected = new ImageIcon(collectedImage);

    public Timer timer,timer2,timer8,timer5,timer6,timer7,timer9,timer10; //our timers
    public void setupView() {
        addKeyListener(new TAdapter()); //this sets up our JPanel for JFrame and calls game initializer
        setFocusable(true);
        dim = new Dimension(AlienInvaders.B_WIDTH, AlienInvaders.B_HEIGHT);
        timer = new Timer(AlienInvaders.DELAY, new timerGameLoop());
        timer.start();
        initialiseGame(); //game initializer
    }
    public View() {
        setupView();
    }




    private void initialiseGame() { //initializes the game
        asteroids = new ArrayList<>();
        player = new Player();
        laser = new Laser();
        garbage1 = new garbage(10,285);
        Timer timer4 = new Timer(1000, e -> {
            int randomInt = (int) Math.floor(Math.random() * (450 - 1 + 1) + 1); //spawns asteroid
            asteroid asteroid1 = new asteroid(randomInt, 10);
            asteroids.add(asteroid1);
        });
        timer4.start();
        timer5 = new Timer(20000, e -> {
           level+=1;
           soundManager.play(soundManager.levelUp); //plays levelUp sound when levelled up
        });
        timer5.start();
        timer6 = new Timer(1000,e -> clock+=1);
        timer6.start();
        timer2 = new Timer(50, e -> {
            colour1+=2;colour2+=3;colour3+=4; //this is the rainbow font control for alienInvaders to arrive
            if(colour1>=255)colour1=0;if(colour2>=255)colour2=0;if(colour3>=255)colour3=0;
            currentColour = new Color(colour1,colour2,colour3);
            alienTime = true;
        });
        invaders = new ArrayList<>();
        timer9 = new Timer(85000,e -> {
            String invaderCol1 = "res/alien.png";
            String invaderCol2 = "res/alien2.png"; //handles making our invaders change colour to angryInvader(blue then red constantly) when angry
            ImageIcon ii1 = new ImageIcon(invaderCol1);
            ImageIcon ii2 = new ImageIcon(invaderCol2);
            timer10 = new Timer(200,a -> invaders.forEach(invader -> {
                if(invader.getImage()==ii1.getImage()) {
                    invader.setImage(ii2.getImage());
                }      //switches images every 0.2s
                else{
                    invader.setImage(ii1.getImage());
                }
            }));
            timer10.start();
        });
        timer9.start();
        timer9.setRepeats(false);
        timer7 = new Timer(80000, e -> {    //this spawns our aliens when 80s has passed
            timer4.stop();
            timer2.start();
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 7; y++) {
                    Invader alien = new Invader(AlienInvaders.ALIEN_INIT_X + 40 * y, AlienInvaders.ALIEN_INIT_Y + 30 * x);
                    invaders.add(alien); //spawning aliens
                }
            }
            timer8 = new Timer(700,a -> {
                if(dir<0){
                    dir-=1; //this speeds up the aliens every 0.7s until it bounces on the wall
                }
                else{
                    dir+=1;
                }
            });
            timer8.start();
        });
        timer7.start();timer7.setRepeats(false);


    }
    private void drawAsteroids(Graphics g) { //draws asteroids
        for(asteroid asteroid1 : asteroids){
            if (asteroid1.isVisible()) {
                g.drawImage(asteroid1.getImage(),asteroid1.getX(),asteroid1.getY(),this);
            }
            if (!asteroid1.isKilled()){
                int y = asteroid1.getY() + asteroidVelocity; //if asteroid isn't killed it makes it fall.
                if (AlienInvaders.GROUND-12.5 < y) {
                    asteroid1.kill(true); //if hits ground kill
                }
                else{
                    asteroid1.setY(y); //if hasn't hit ground move it
                }continue;
            }asteroid1.die(); //if asteroid is killed make it die also

        }

    }

    private void drawAliens(Graphics g) {

        for (Invader invader : invaders) {

            if (invader.isVisible()) { //draws the Aliens

                g.drawImage(invader.getImage(), invader.getX(), invader.getY(), this);
            }

            if (!invader.isKilled()) { continue; }
            invader.die();
        }
    }
    public void drawAlienTime(Graphics g){ //draws text
        g.setColor(currentColour);
        g.drawString(("ALIENS ARE ATTACKING!!!"),100,450);
    }
    private void drawAlienAttack(Graphics g) { //draws alien attacks

        invaders.stream().map(Invader::getInvaderAttack).filter(b -> !b.isKilled()).forEach(b -> g.drawImage(b.getImage(), b.getX(), b.getY(), this));
    }

    private void drawPlayer(Graphics g) { //draws player

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this); //self explanatory
        }

        if (!player.isKilled()) {
            return;
        }

        player.die();
        inGameLoop = false;
    }
    public void drawGarbage(Graphics g) { //draws garbage
        if (!garbage1.isVisible()) {
            return;
        }
        g.drawImage(garbage1.getImage(), //self explanatory
                garbage1.getX(),
                garbage1.getY(),
                this);

    }
    private boolean isColliding (gameObject a, gameObject b){ //collision handling for player and asteroid and garbage
        int width = 25-5;int aHeight=25;int bHeight=25;
        aHeight = a instanceof Player? AlienInvaders.PLAYER_H : aHeight;aHeight = a instanceof asteroid? AlienInvaders.ASTEROID_H : aHeight;
        bHeight = b instanceof Player? AlienInvaders.PLAYER_H : bHeight;bHeight = b instanceof asteroid? AlienInvaders.ASTEROID_H : bHeight;
        aHeight+=5;bHeight+=5;
        return a.x < b.x + width &&
                a.x + width > b.x && //basic rectangle and rectangle collision
                a.y < b.y + bHeight &&
                a.y + aHeight > b.y;
    }

    private void drawLaser(Graphics g) { //draws lasers
        

        if (!laser.isVisible()) {
            return;
        }

        g.drawImage(laser.getImage(), //self explanatory
                laser.getX(),
                laser.getY(),
                this);
    }

    public void drawTimeLeft(Graphics g){ //draws time left
        long initSeconds = 120-clock;
        long minute = TimeUnit.SECONDS.toMinutes(initSeconds);
        long seconds = TimeUnit.SECONDS.toSeconds(initSeconds) - (TimeUnit.SECONDS.toMinutes(initSeconds)*60); //self explanatory
        g.setColor(currentColour);
        g.drawString(("Time left: "+ minute+"m "+seconds+"s"),325,425);
    }

    public void drawLevel(Graphics g){
        g.setColor(currentColour);
        g.drawString(("Level: "+ level),300,400); //draws level text
    }

    public void drawScore(Graphics g){  //draws score text
        g.setColor(currentColour);
        g.drawString(("Score: "+ score),400,400);
        g.setColor(Color.green);
    }

    @Override
    public void paintComponent(Graphics g) { //this handles painting our JPanel
        super.paintComponent(g);
        try {
            drawAll(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawAll(Graphics g) throws IOException { //this draws everything needed to be drawn

        g.setColor(Color.darkGray);
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(Color.red);

        if (!inGameLoop) {

            if (timer.isRunning()) {  //stops everything and calls gameOver if not in game loop
                timer.stop();
            }
            if (timer5.isRunning()){
                timer5.stop();
            }
            emptyGrid(this);setVisible(false);
            mainLoop.gameOver(score);
        } else {
            drawGarbage(g);
            drawPlayer(g);
            drawAsteroids(g);drawAliens(g); //if in game loop draws everything
            drawAlienAttack(g);
            drawLevel(g);drawTimeLeft(g);
            drawLaser(g);drawScore(g);
            g.drawLine(0, AlienInvaders.GROUND,
                    AlienInvaders.B_WIDTH, AlienInvaders.GROUND);
            if(drawCollected){
                g.drawImage(tempCollected.getImage(),50,375,this); //self explanatory
            }
            if(isCollected){
                score+=100;
                isCollected=false;drawCollected=true;
                int randomInt = (int)Math.floor(Math.random()*(450-25+1)+25); //if garbage is collected adds to score and spawns new garbage
                garbage1.die();
                Timer timer3;
                timer3 = new Timer(2000, e -> {
                    garbage1.setX(randomInt);
                    garbage1.setVisible();
                    drawGarbage(g);
                    drawCollected=false;
                });
                timer3.start();timer3.setRepeats(false);
            }
            if(alienTime){
                drawAlienTime(g); //draws alien text for alien arrival
            }
        }

        Toolkit.getDefaultToolkit().sync(); //ensures display is up to date
    }

    private void update_gameCycle(){
        if (clock == 120) {
            inGameLoop = false; //if 120 seconds have passed stop clock and stop game
            timer.stop();
        }
        player.doActions();

        if (garbage1.isVisible() && player.isVisible()) { //if they collide kill them
            if (isColliding(player, garbage1)) {
                garbage1.kill(true);
                isCollected = true;
            }
        }


        if (laser.isVisible()) {

            int laserX = laser.getX();
            int laserY = laser.getY();

            asteroids.forEach(asteroid1 -> {
                int asteroidX = asteroid1.getX(); //if laser and asteroid are colliding put in explosion image and then get rid of asteroid and laser
                int asteroidY = asteroid1.getY();
                if (asteroid1.isVisible())
                    if (laser.isVisible()) {
                        if ((asteroidX + AlienInvaders.ASTEROID_W) >= laserX)
                            if ((asteroidX) <= laserX && laserY >= (asteroidY) && laserY <= (asteroidY + AlienInvaders.ASTEROID_H)) {
                                ImageIcon tempBoomImage = new ImageIcon(boomImage);
                                asteroid1.setImage(tempBoomImage.getImage());
                                asteroid1.kill(true);
                                laser.die();
                            }
                    }
            });

            invaders.forEach(invader -> {
                int alienX = invader.getX();
                int alienY = invader.getY();  //if laser and alien are colliding put in explosion image and then get rid of asteroid and laser
                if (invader.isVisible())
                    if (laser.isVisible()) {
                        if ((alienX + AlienInvaders.ALIEN_W) >= laserX)
                            if ((alienX) <= laserX && laserY >= (alienY) && laserY <= (alienY + AlienInvaders.ALIEN_H)) {
                                ImageIcon tempBoomImage = new ImageIcon(boomImage);
                                invader.setImage(tempBoomImage.getImage());
                                invader.kill(true);
                                laser.die();
                            }
                    }
            });

            int y = laser.getY() - 7; //moves laser

            if (0 > y) {
                laser.die(); //if laser hits ceiling die otherwise draw it
            } else laser.setY(y);
        }
        asteroids.forEach(asteroid1 -> {
            if (player.isVisible() && !asteroid1.isKilled() && asteroid1.isVisible()) { //if asteroid and player collide add explosion and end game
                if(isColliding(player,asteroid1)){
                    var tempIcon = new ImageIcon(boomImage);
                        player.setImage(tempIcon.getImage());
                        player.kill(true);
                        asteroid1.kill(true);
                    }
            }
        });
        switch (level) { //changes colours and asteroid velocity each level
            case 2 -> {
                AlienInvaders.asteroidImage = "res/asteroid2.png";
                asteroidVelocity = 4;
                currentColour = Color.green;
            }
            case 3 -> {
                AlienInvaders.asteroidImage = "res/asteroid3.png";
                asteroidVelocity = 5;
                currentColour = Color.cyan;
            }
            case 4 -> {
                AlienInvaders.asteroidImage = "res/asteroid4.png";
                asteroidVelocity = 7;
                currentColour = Color.red;
            }
            case 5 -> timer5.stop();
            default -> AlienInvaders.asteroidImage = "res/asteroid.png";
        }

         //invaders

        for (Invader invader : invaders) { //for each invader make it move as intended (move sideways until hits wall then move down one)

            int x;
            x = invader.getX();

            if (x >= (AlienInvaders.B_WIDTH - AlienInvaders.RIGHT_BORDER))
                if (!(dir == -1)) {

                    dir = -1;

                    for (Invader a2 : invaders) {

                        a2.setY(a2.getY() + AlienInvaders.GO_DOWN);
                    }
                }

            if (!(x > AlienInvaders.LEFT_BORDER || dir == 1)) {

                dir = 1;

                Iterator<Invader> invaderIterator;
                invaderIterator = invaders.iterator();

                while (invaderIterator.hasNext()) {

                    Invader a = invaderIterator.next();
                    a.setY(a.getY() + AlienInvaders.GO_DOWN); //same thing as above just was testing out this method
                }
            }
        }

        invaders.stream().filter(gameObject::isVisible).forEachOrdered(invader -> {
            int y = invader.getY();
            if (AlienInvaders.GROUND - AlienInvaders.ALIEN_H < y) { //if any row of aliens is about to hit the ground end game
                inGameLoop = false;
            }
            invader.act(dir);
        });

         //invader attacks
        Random generator;
        generator = new Random();

        invaders.forEach(invader -> { //spawns alien attacks for each alien
            int shot = generator.nextInt(15);
            Invader.invaderAttack invaderAttack = invader.getInvaderAttack();
            if (shot == AlienInvaders.CHANCE) {
                if (invader.isVisible() && invaderAttack.isKilled()) {

                    invaderAttack.kill(false);
                    invaderAttack.setX(invader.getX());
                    invaderAttack.setY(invader.getY());
                }
            }
            int alienAttackX = invaderAttack.getX();
            int alienAttackY = invaderAttack.getY();
            int playerX;
            playerX = player.getX();
            int playerY;
            playerY = player.getY();
            if (player.isVisible() && !invaderAttack.isKilled()) { //if any of the invader attacks hit the player, add explosion and kill the player hence end game.

                if (alienAttackX >= (playerX))
                    if (alienAttackX <= (playerX + AlienInvaders.PLAYER_W) && alienAttackY >= (playerY) && alienAttackY <= (playerY + AlienInvaders.PLAYER_H)) {
                        var tempIcon = new ImageIcon(boomImage);
                        player.setImage(tempIcon.getImage());
                        player.kill(true);
                        invaderAttack.kill(true);
                    }
            }
            if (!invaderAttack.isKilled()) { //moves the alien attack down

                invaderAttack.setY(invaderAttack.getY() + 1);

                if (invaderAttack.getY() >= AlienInvaders.GROUND - AlienInvaders.BOMB_HEIGHT) {

                    invaderAttack.kill(true);
                }
            }
        });
    }

    private void doGameCycle() { //animation updating handling

        update_gameCycle();
        repaint();
    }

    private class timerGameLoop implements ActionListener { //this sets up our animating

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter { //key binding to up arrow to shoot laser

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP) {

                if (inGameLoop) { //if up is pressed spawn laser and play shoot sound

                    if (!laser.isVisible()) {
                        laser = new Laser(x, y);
                        soundManager.shoot();
                    }
                }
            }
        }

    }
}
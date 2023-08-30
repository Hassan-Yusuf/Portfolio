package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.company.AlienInvaders.mainLoop;

public class mainMenu extends JPanel implements ActionListener{
    private final JPanel title;
    private final JPanel start; //panels for our 2 sections of main menu.
    private final JLabel titleLabel; //label for title.
    private final JButton startButton;
    private final JButton highScoresButton;
    private final JButton quitButton; //buttons
    private final ArrayList<Rectangle> stars;
    public Timer timer = new Timer(20,this);


    public void emptyGrid(JPanel object){
        if(object!=null){
            this.remove(object); //empties our JPanel
        }
    }
    public mainMenu(){ //creates our main menu JPanel
        setSize(AlienInvaders.mainMenuDimension);
        setVisible(true);
        setLayout(null);
        setBackground(Color.black);
        title = new JPanel();
        title.setBounds(100,100,600,150); //this is where our title panel will be located and sets color
        title.setBackground(Color.darkGray);
        titleLabel = new JLabel("Alien Invaders"); //adds name of game in label with font settings
        titleLabel.setFont(new Font("Arial",Font.BOLD,30));
        titleLabel.setForeground(Color.WHITE);
        Timer timer = new Timer(200, e -> {
            if(title.getBackground()==Color.darkGray){
                title.setBackground(Color.white); //this adds an animation to our titlePanel
                titleLabel.setForeground(Color.black);
            }
            else{
                title.setBackground(Color.darkGray);
                titleLabel.setForeground(Color.WHITE);
            }
        });
        timer.start();
        start = new JPanel();
        start.setBounds(300,400,200,100); //where our start panel will be located and sets it to black
        start.setBackground(Color.BLACK);
        startButton = new JButton("Start Game  ");
        startButton.setForeground(Color.BLACK); //settings for start button
        startButton.setBackground(Color.GRAY);
        //used to get rid of button default setting
        Border emptyBorder = BorderFactory.createEmptyBorder();
        startButton.setBorder(emptyBorder);
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.WHITE);
            } //when mouse enters, changes colour

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.GRAY);
            } //otherwise gray
            public void mouseClicked(java.awt.event.MouseEvent evt){
                emptyGrid(title);emptyGrid(start);setVisible(false);
                //System.out.println(Arrays.toString(mainLoop.getComponents()));
                mainLoop.GameLoop();mainLoop.setState(JFrame.ICONIFIED);mainLoop.setState(JFrame.NORMAL);
                soundManager.stop(soundManager.music);
            }
        });
        highScoresButton = new JButton("High Scores");
        highScoresButton.setForeground(Color.BLACK); //adds high score button with these settings
        highScoresButton.setBackground(Color.GRAY);
        highScoresButton.setBorder(emptyBorder);
        highScoresButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                highScoresButton.setBackground(Color.WHITE);
            } //when mouse enters, changes colour

            public void mouseExited(java.awt.event.MouseEvent evt) {
                highScoresButton.setBackground(Color.GRAY);
            }
            //otherwise gray
            public void mouseClicked(java.awt.event.MouseEvent evt){
                emptyGrid(title);emptyGrid(start);setVisible(false); //will get rid of the main menu and go to high score window if it can find file
                try {
                    mainLoop.highScores();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        quitButton = new JButton("Quit Game   ");
        quitButton.setForeground(Color.BLACK);
        quitButton.setBackground(Color.GRAY); //quit game button with settings here
        quitButton.setBorder(emptyBorder);
        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                quitButton.setBackground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                quitButton.setBackground(Color.GRAY);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){
                System.exit(0);
            } //quits game.
        });
        title.add(titleLabel);start.add(startButton);start.add(highScoresButton);start.add(quitButton);
        add(title);add(start);
        Rectangle star;
        stars= new ArrayList<>();
        for(int x=50; x<AlienInvaders.mainMenuDimension.width;x+=100){
            for(int y=50;y<AlienInvaders.mainMenuDimension.height;y+=100){ //this section is an animation for our background of the panel.
                star = new Rectangle(x,y,1,1);
                stars.add(star);
            }
        }
    }
    public void updateStars(){
        stars.forEach(star ->{
            if(star.getY()==600){ //this makes our background animation of stars go down the screen, and if it reaches the end, stars will go back to top.
                star.y=1;
            }
            star.y+=1;
        });
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; //this handles all drawing on the mainMenu panel.
        g2d.setColor(Color.WHITE);
        stars.forEach(g2d::draw);
        timer.start();


    }
    @Override
    public void actionPerformed(ActionEvent e){ //this updates our menu animations
        updateStars();
        repaint();
    }


}

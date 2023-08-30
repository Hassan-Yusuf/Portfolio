package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static com.company.AlienInvaders.mainLoop;

public class gameOver extends JPanel{
    int score; //displayText stores the label used to display game over text. score stores score for initializer.
    File file = new File("src\\com\\company\\score.txt"); //gets the score file.
    void drawText(){ //draws our text
        //uses html to display text in nice format
        //uses html to display text in nice format
        JLabel displayText = new JLabel("<html><center>GAME OVER!</center></html>" + //uses html to display text in nice format
                "please stay safe and stay at home.</html>");
        displayText.setForeground(Color.RED);
        displayText.setFont(new Font("Calibri", Font.ITALIC,50));
        GridBagLayout layout = new GridBagLayout(); //this centers our text to the middle of the screen and adds it to the screen.
        setLayout(layout);
        add(displayText,new GridBagConstraints());

    }
    void saveData() throws IOException{ //this saves our data into a text file.
        FileWriter write = new FileWriter(file,true);
        try {
            String name = JOptionPane.showInputDialog(this, "Your initials please?(2 characters):", //gets input from user from dialog
                    "Save your score!", JOptionPane.INFORMATION_MESSAGE);
            String line = name.toUpperCase() + "\t" + score + "\n";
            if (name.length() == 2) { //makes sure name is of length 2 otherwise doesn't save it
                write.write(line);
                JOptionPane.showMessageDialog(this, "Score saved!", //shows confirmation message
                        "Success!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Score was not saved :(",
                        "Failed to save", JOptionPane.INFORMATION_MESSAGE); //shows failure to save message.
            }
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(this,"You have to enter a name! Failed to save!");
        }
        write.close();
        remove(this);setVisible(false);
        soundManager.stop(soundManager.gameOver);
        soundManager.play(soundManager.music);
        mainLoop.mainMenu();
    }

    public gameOver(int score) { //creates our window with the settings below, and draws our text, + calls saveData()
        setBackground(Color.GREEN);
        setSize(new Dimension(800, 600));
        setVisible(true);
        this.score =score;
        drawText();
        Timer timer = new Timer(500, e-> {
            try {
                saveData();
            } catch (IOException ioException) { //added a timer here because JOptionPane overrides JPanel and this results in a blank frame
                ioException.printStackTrace();  //hence we need a little time for the Panel to set first for a quick fix.
            }
        });
        timer.start();timer.setRepeats(false);
    }
}
package com.company;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.company.AlienInvaders.mainLoop;

public class highScores extends JPanel implements ActionListener {
    File file = new File("res/score.txt"); //our score file.
    Rectangle star;
    public ArrayList<Rectangle> stars;
    public Timer timer = new Timer(20,this);

    public LinkedHashMap<String,Integer> getData() throws FileNotFoundException { //this sorts our scores from highest to lowest and returns the map
        Map<String,Integer> map = new HashMap<>();
        LinkedHashMap<String,Integer> sortedMap = new LinkedHashMap<>();
        Scanner scanner = new Scanner(file);
        int tempScore;String name;
        while(scanner.hasNext()){
            name =scanner.next();
            tempScore=scanner.nextInt();  //stores name and score into a new map
            map.put(name,tempScore);
        }
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //stores map in linkedHashmap (which doesn't auto sort) and sorts map in reverse order.
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue())); //(which is highest to lowest)
        return sortedMap;

    }

    public void getHighScores(LinkedHashMap<String,Integer> map){ //this takes our sorted map and displays top 5
        int count=0;
        String text= "<html><center><h1>Top 5 highest scores<br/><br/><br/>"; //uses html to display title
        for(SortedMap.Entry<String,Integer> entry:map.entrySet()){
            count+=1;
            text+=count+". "+entry.getKey()+"   "+entry.getValue()+"<br/><br/>"; //only adds first 5 scores to text and adds 2 new lines for each
            if(count>=5){text+="</html>";break;}
        }
        //label for displaying text to user.
        JLabel displayText = new JLabel(text);
        displayText.setForeground(Color.RED);
        displayText.setFont(new Font("Calibri", Font.ITALIC,25)); //displays our high scores on the screen
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout); //makes the text centered to middle of screen.
        add(displayText,new GridBagConstraints());
    }

    public void updateStars(){
        stars.forEach(star ->{ //moves stars to the right till it reaches right edge of screen
            if(star.getX()==800){
                star.x=1;
            }
            star.x+=1;
        });
    }
    @Override
    public void actionPerformed(ActionEvent e){
        updateStars();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; //this is our animation section (mostly handles drawing of stars)
        g2d.setColor(Color.GREEN);
        stars.forEach(g2d::draw);
        timer.start();
    }

    public highScores() throws FileNotFoundException { //creates our window with the settings below, and draws our text, + calls getData()
        setBackground(Color.BLACK);
        setSize(new Dimension(800, 600));
        setVisible(true);
        getHighScores(getData());
        stars = new ArrayList<>();
        for(int x=50; x<AlienInvaders.mainMenuDimension.width;x+=100){
            for(int y=50;y<AlienInvaders.mainMenuDimension.height;y+=100){
                star = new Rectangle(x,y,5,1);
                stars.add(star);
            }
        }
        JButton backButton = new JButton("Go back");
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(Color.GRAY);
        //used to get rid of button default setting
        Border emptyBorder = BorderFactory.createEmptyBorder();
        backButton.setBorder(emptyBorder);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.GRAY);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){
                removeAll();setVisible(false); //mouse animations etc. //this also moves us back to mainMenu on click
                mainLoop.mainMenu();
            } //quits game.
        });
        backButton.setBounds(1,1,50,50);
        add(backButton);
    }
}

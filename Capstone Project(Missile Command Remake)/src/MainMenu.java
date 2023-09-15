import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainMenu extends JPanel implements ActionListener {
    public JPanel titleSection,middleSection;
    public JLabel titleText,middleText;
    public JButton start,highScores,quit;
    private final ArrayList<Rectangle> stars;
    private final ArrayList<Rectangle> planes;
    public Timer timer = new Timer(20,this);
    public Timer menuText;

    public void emptyPanel(JPanel object){
        if(object!=null){
            this.remove(object); //empties our JPanel
        }
    }

    public MainMenu(){
        Border emptyBorder = BorderFactory.createEmptyBorder();
        setSize(constants.DIM_WIDTH,constants.DIM_HEIGHT);
        setVisible(true);
        setLayout(null);
        setBackground(Color.black);
        titleSection = new JPanel();
        titleSection.setBackground(Color.BLACK);
        titleSection.setBounds(100,50,600,100);
        titleText = new JLabel("Missile Command!");
        titleText.setFont(new Font("Monospaced",Font.BOLD,60));
        titleText.setForeground(Color.YELLOW);
        titleSection.add(titleText);
        add(titleSection);
        middleSection = new JPanel();
        middleSection.setBackground(Color.BLACK);
        middleSection.setBounds(100,200,600,50);
        middleText = new JLabel("Buttons");
        start = new JButton("Start");
        start.setFont(new Font("Monospaced",Font.BOLD,30));
        highScores = new JButton("Leaderboard");
        highScores.setFont(new Font("Monospaced",Font.BOLD,30));
        quit = new JButton("Quit");
        quit.setFont(new Font("Monospaced",Font.BOLD,30));
        start.setForeground(Color.YELLOW);highScores.setForeground(Color.YELLOW);quit.setForeground(Color.YELLOW);
        middleSection.add(start);middleSection.add(Box.createRigidArea(new Dimension(70, 0)));
        middleSection.add(highScores);middleSection.add(Box.createRigidArea(new Dimension(70, 0)));middleSection.add(quit);
        //middleSection.add(middleText);
        add(middleSection);
        start.setBackground(Color.BLACK);
        start.setBorder(emptyBorder);
        soundManager.initHovers();soundManager.initExplosions();
        //Listeners
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                soundManager.playHover();
                start.setBackground(Color.GREEN);start.setForeground(Color.white);
                //soundManager.playHover();
            } //when mouse enters, changes colour

            public void mouseExited(java.awt.event.MouseEvent evt) {
                start.setBackground(Color.BLACK);start.setForeground(Color.YELLOW);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){
                soundManager.explode();
                emptyPanel(titleSection);emptyPanel(middleSection);setVisible(false);timer.stop();
                MissileCommand.main.Game();MissileCommand.main.setState(JFrame.ICONIFIED);MissileCommand.main.setState(JFrame.NORMAL);
                ;menuText.stop();soundManager.stopMenuMusic();
            }
        });
        highScores.setBackground(Color.BLACK);
        highScores.setBorder(emptyBorder);
        //Listeners
        highScores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                soundManager.playHover();
                highScores.setBackground(Color.GREEN);highScores.setForeground(Color.white);
            } //when mouse enters, changes colour

            public void mouseExited(java.awt.event.MouseEvent evt) {
                highScores.setBackground(Color.BLACK);highScores.setForeground(Color.YELLOW);
            }
            //otherwise BLACK
            public void mouseClicked(java.awt.event.MouseEvent evt){
                soundManager.play(soundManager.click,false);emptyPanel(titleSection);emptyPanel(middleSection);setVisible(false);
                timer.stop();//will get rid of the main menu and go to high score window if it can find file
                try {
                    MissileCommand.main.leaderBoard();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        quit.setBackground(Color.BLACK);
        quit.setBorder(emptyBorder);
        //Listeners
        quit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                soundManager.playHover();
                quit.setBackground(Color.GREEN);quit.setForeground(Color.white);
            } //when mouse enters, changes colour

            public void mouseExited(java.awt.event.MouseEvent evt) {
                quit.setBackground(Color.BLACK);quit.setForeground(Color.YELLOW);
            }
            //otherwise BLACK
//            public void mouseClicked(java.awt.event.MouseEvent evt){
//                setVisible(false); //will get rid of the main menu and go to high score window if it can find file
//            }
            public void mouseClicked(java.awt.event.MouseEvent evt){
                soundManager.play(soundManager.click,false);
                System.exit(0);
            } //quits game.
        });
        Rectangle star;
        stars= new ArrayList<>();
        for(int x=49; x<constants.DIM_WIDTH;x+=50){
            for(int y=49;y<constants.DIM_HEIGHT;y+=50){ //this section is an animation for our background of the panel.
                star = new Rectangle(x,y,1,1);
                stars.add(star);
            }
        }
        Rectangle plane;
        planes= new ArrayList<>();
        for(int x=20; x<800;x+=120){
            for(int y=400;y<600;y+=50){ //this section is an animation for our background of the panel.
                plane = new Rectangle(x,y,10,2);
                planes.add(plane);
            }
        }
        menuText= new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(titleText.getForeground().equals(Color.BLACK)){
                    titleText.setForeground(Color.YELLOW);
                }else{
                    titleText.setForeground(Color.BLACK);
                }
            }
        });menuText.start();



    }
    public void updateStars(){
        stars.forEach(star ->{
            if(star.getY()==600){ //this makes our background animation of stars go down the screen, and if it reaches the end, stars will go back to top.
                star.y=1;
            }
            star.y+=1;
        });

    }
    public void updatePlanes(){
        planes.forEach(plane ->{
            if(plane.getX()==800){ //this makes our background animation of stars go down the screen, and if it reaches the end, stars will go back to top.
                plane.x=1;
            }
            plane.x+=1;
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; //this handles all drawing on the mainMenu panel.
        g2d.setColor(Color.WHITE);
        stars.forEach(g2d::draw);
        g2d.setColor(Color.YELLOW);
        planes.forEach(g2d::draw);
        for (Rectangle plane : planes) {
            g2d.fillRect(plane.x,plane.y,10,2);
        }
        timer.start();


    }
    @Override
    public void actionPerformed(ActionEvent e){ //this updates our menu animations
        updateStars();
        updatePlanes();
        repaint();
    }
}

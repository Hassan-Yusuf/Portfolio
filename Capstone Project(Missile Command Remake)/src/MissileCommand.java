import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class MissileCommand extends JFrame {
    public static MissileCommand main;
    static Timer menuMusic;
    public MissileCommand() {
        setTitle("Missile Command!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);setLocation(500,250);
        setSize(constants.DIM_WIDTH,constants.DIM_HEIGHT);
        mainMenu();
    }
    public void mainMenu(){
        if(soundManager.isPlaying(soundManager.waitingMusic))soundManager.stop(soundManager.waitingMusic);
        menuMusic = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.menuMusicPlayNext();
            }
        });menuMusic.start();
        setContentPane(new MainMenu());
    }
    public void Game(){
        soundManager.stopMenuMusic();
        menuMusic.stop();
        setContentPane(new View());
    }
    public void gameOver(int score) { //gameOver tab and game music handling
        soundManager.stop(soundManager.backgroundNoise);
        setContentPane(new gameOver(score));
        soundManager.play(soundManager.waitingMusic,true);
    }
    public void leaderBoard() throws FileNotFoundException {
        setContentPane(new leaderBoard());
    }
    public static void main(String[] args) {
        main = new MissileCommand();
        main.setVisible(true);
    }

}

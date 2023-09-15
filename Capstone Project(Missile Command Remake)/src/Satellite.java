import javax.swing.*;
import java.awt.*;

public class Satellite extends FlyingEnemies{
    Timer timer;
    String Satellite = "res/Satellite.png";
    ImageIcon SatelliteImage=new ImageIcon(Satellite);
    String Satellite2 = "res/Satellite2.png";
    ImageIcon Satellite2Image=new ImageIcon(Satellite2);
    public Satellite() {
        setImage(SatelliteImage.getImage());
        setupSatellite();
    }
    public void setupSatellite(){
        vectorX=2;
        chooseSide();
        timer = new Timer(500, e-> {
            if(getImage()==SatelliteImage.getImage()){
                setImage(Satellite2Image.getImage());
            }
            else{
                setImage(SatelliteImage.getImage());
            }
        });
        timer.start();
    }

}

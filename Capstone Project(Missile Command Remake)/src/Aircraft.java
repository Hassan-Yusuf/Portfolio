import javax.swing.*;
import java.awt.*;

public class Aircraft extends FlyingEnemies{
    String AircraftLeft = "res/AircraftLeft.png";
    String AircraftRight = "res/AircraftRight.png";
    ImageIcon AircraftLeftImage=new ImageIcon(AircraftLeft);
    ImageIcon AircraftRightImage=new ImageIcon(AircraftRight);


    public Aircraft() {
        setupAircraft();
    }

    public void setupAircraft(){
        vectorX=3;
        chooseSide();
        if(startSide <= 0.5){
            setImage(AircraftRightImage.getImage());
        }
        else{
            setImage(AircraftLeftImage.getImage());
        }
    }
    public void moveAircraft(){
        if(startSide <= 0.5 && getX() > xTarget - 30){
            setImage(AircraftLeftImage.getImage());
        }
        else if(startSide > 0.5 && getX() < xTarget + 30){
            setImage(AircraftRightImage.getImage());
        }
        move();
    }

}


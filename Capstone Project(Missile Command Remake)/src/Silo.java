import javax.swing.*;
import java.awt.*;

public class Silo extends gameObject {
    public Silo(int x, int y) {
        initSilo(x, y);
    }
    public void initSilo(int x, int y){
        String  SiloImage = "res/silo.png";
        var tempImage = new ImageIcon(SiloImage);
        setImage(tempImage.getImage());
        setX(x);
        setY(y);
    }
    public void drawSilo(Graphics g){
        g.drawImage(getImage(), getX(), getY(), null);
    }
}

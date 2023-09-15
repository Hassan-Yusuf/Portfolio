import javax.swing.*;
import java.awt.*;

public class City extends gameObject{
    public boolean isDead = false;

    public City(int x, int y) { initCity(x, y); }

    private void initCity(int x, int y){
        String  CityImage = "res/city.png";
        var tempImage = new ImageIcon(CityImage);
        setImage(tempImage.getImage());
        setX(x);
        setY(y);

    }
    public void drawCity(Graphics g){
        g.drawImage(getImage(), getX(), getY(), null);
    }
}

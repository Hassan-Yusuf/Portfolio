import java.awt.*;

public class gameObject {
    double x;double y;double vectorX;double vectorY;
    private Image image;
    private boolean visible;
    private boolean dying;


    public void die() {

        visible = false;
    }
    public gameObject() {

        visible = true;
    }



    protected void setVisible() {

        this.visible = true;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public boolean isVisible() {

        return visible;
    }


    public void setX(int x) {
        this.x = x; }

    public void setY(int y) {
        this.y = y; }

    public int getY() {
        return (int)y; }

    public int getX() {
        return (int)x; }
    public Image getImage() { return image; }
    public boolean isKilled() {
        return this.dying;
    }
    public void kill(boolean dying) {

        this.dying = dying;
    }


}
import javax.swing.*;
import java.awt.*;

public class FlyingEnemies extends gameObject {
    private final boolean drawEnemyAgents=true;
    protected double startSide;
    protected double xTarget, yTarget, startX, startY;

    Timer timer;

    protected void chooseSide() {
        startSide = Math.random();
        if (startSide <= 0.5) {
            setX((int) (Math.random() * (-150 + 75 + 1) - 75));
            xTarget = 900;
        } else {
            setX((int) (Math.random() * (900 - 800 + 1) + 800));
            xTarget = -150; //more space here as the coordinates are 50 pixels wide to the right
        }
        setY((int) (Math.random() * (250 - 80 + 1) + 80));
        startX = x;
        startY = y;
        yTarget = startY;
        if(drawEnemyAgents){
            spawnEnemyAgents();
        }
    }

    protected void move() {
        if (startSide <= 0.5) {
            if (getX() < xTarget) {
                setX((int) (getX() + vectorX));
            } else {
                if(y<constants.DIM_HEIGHT-300)y=y+50;
                else y=y-50;
                setX((int) xTarget - 10);
                startSide = 1;
                xTarget = -200;
            }
        } else {
            if (getX() > xTarget) {
                setX((int) (getX() - vectorX));
            } else {
                setY(getY() + 50);
                setX((int) xTarget + 10);
                startSide = 0;
                xTarget = 1000;
            }
        }
    }


    public void spawnEnemyAgents(){
        timer = new Timer(3000, e -> {
            if (!isKilled()){
                View.enemyAgents.add(new enemyAgent(getX(), getY(), false));
            }
        });
        timer.start();

    }
    public void draw(Graphics g){
        g.drawImage(getImage(), getX(), getY(), null);
    }

}


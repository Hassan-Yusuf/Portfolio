import javax.swing.*;
import java.awt.*;

public class enemyAgent extends gameObject {
    private int setTarget;
    public boolean split = true;
    public boolean hasSplitted = false;
    private double endX, startX, startY;
    private double speed = 1;
    private int endY = constants.DIM_HEIGHT - 100;
    private double splitDecide = (Math.random());
    private int randomSplit= (int) (Math.random() * (4 - 2 + 1) + 2);
    private boolean explosionSoundPlayed=false;

    public enemyAgent(){
        if(View.level<2){
            split = false;
        }
        if(splitDecide>=0.25&& split)split=false;
        if(View.level>=5){
            speed=2;
        }
        randomXY();
        setupTarget();
    }
    public void setSplitted(boolean hasSplitted){
        this.hasSplitted = hasSplitted;
    }
    public boolean getSplit(){
        return split;
    }

    public void randomXY() {
        x = (int) (Math.random() * (750 - 50 + 1) + 50); //random x coordinate
        y = (int) (Math.random() * (-200 - 0 + 1) + 0); //random y coordinate
        startX = x;
        startY = y;
    }

    public enemyAgent(int x, int y, boolean split) {
        this.x = x;
        this.y = y;
        startX = x;
        startY = y;
        this.split = split;
        setupTarget();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setupTarget() {
        setRandomTarget();
        setTargetCoordinates();
        calculateVector();
        if(split)splitEnemyAgents();
    }

    private void setRandomTarget() {
        setTarget = (int) (Math.random() * (9 - 1 + 1) + 1);
    }

    private void setTargetCoordinates() {
        if (setTarget == 1) {
            endX = 10 + 30;
        } else if (setTarget >= 2 && setTarget <= 4) {
            int increment = setTarget - 2;
            endX = 80 + 100 * increment + 37;
        } else if (setTarget == 5) {
            endX = constants.DIM_WIDTH / 2 - 30 + 30;
        } else if (setTarget >= 6 && setTarget <= 8) {
            int increment = setTarget - 3;
            endX = 80 + 100 * increment + 55 + 37;
        } else if (setTarget == 9) {
            endX = constants.DIM_WIDTH - 84 + 30;
        }
        //to account for the width of the silos
    }

    public void calculateVector() {
        double vectorX = endX - x;
        double vectorY = endY - y;
        double magnitude = Math.sqrt(vectorX * vectorX + vectorY * vectorY);
        vectorX = vectorX / magnitude;
        vectorY = vectorY / magnitude;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }
    public void killAmmo(Bullet[] ammo){
        for (Bullet bullet:ammo) {
            bullet.fired=true;
            bullet.kill(true);
            }
    }



    public void move() {
        if (!((x > (endX - 5) && x < (endX + 5)) && (y > (endY - 5) && y < (endY + 5)))) {
            x = x + vectorX * speed;
            y = y + vectorY * speed;
        } else {
            if(setTarget==1) {
                View.silos[0].kill(true);
                for (Bullet bullet:View.ammoLeft) {
                    bullet.fired=true;
                    bullet.kill(true);
                }
            }
            else if(setTarget==2){
                View.cities[0].kill(true);
                View.cities[0].isDead=true;
            }
            else if(setTarget==3){
                View.cities[1].kill(true);
                View.cities[1].isDead=true;
            }
            else if(setTarget==4){
                View.cities[2].kill(true);
                View.cities[2].isDead=true;
            }
            else if(setTarget==5) {
                View.silos[1].kill(true);
                for (Bullet bullet:View.ammoMiddle){
                    bullet.fired=true;
                    bullet.kill(true);
                }
            }
            else if(setTarget==6){
                View.cities[3].kill(true);
                View.cities[3].isDead=true;
            }
            else if(setTarget==7){
                View.cities[4].kill(true);
                View.cities[4].isDead=true;
            }
            else if(setTarget==8){
                View.cities[5].kill(true);
                View.cities[5].isDead=true;
            }
            else if(setTarget==9) {
                View.silos[2].kill(true);
                for (Bullet bullet:View.ammoRight) {
                    bullet.fired=true;
                    bullet.kill(true);
                }
            }
            View.explosions.add(new Explosion(endX,endY,5,40));
            if(!explosionSoundPlayed) {
                soundManager.play(soundManager.getClip("groundExplosion", -10), false);
                explosionSoundPlayed=true;
            }
            kill(true);
        }
    }

    public int getY() {
        return (int) y;
    }

    public int getX() {
        return (int) x;
    }

    public void drawAgent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((int) startX, (int) startY, (int) x + 2, (int) y + 2);
        g2d.setColor(Color.white);
        g2d.fillRect((int) x, (int) y, 4, 4);

    }

    public void splitEnemyAgents() {
        Timer timer = new Timer((7-randomSplit)*1000, e -> {
            int count = 0;
            for (int i = 0; i < 10; i++) {
                if (!isKilled()) {
                    View.enemyAgents.add(new enemyAgent((int) x, (int) y + 10, false));
                    count++;
                    if (count == randomSplit) {
                        kill(true);
                        break;
                    }
                }
            }

            hasSplitted=true;
        }); timer.start(); timer.setRepeats(false);
    }
}

import javax.sound.sampled.Clip;
import java.awt.*;

public class Bullet extends gameObject{
    public Rectangle attack;//public View view;
    public Boolean fired = false;
    public Boolean usedUp=false;
    private int endX,endY,startX,startY;
    public double speed = 1.5+View.extraVelocity;
    public boolean exploded = false;
    public int explosionNum;
    final Clip bulletFired = soundManager.getClip("bulletFiredSound",-12);
    private boolean explosionSoundPlayed=false;

    public Bullet(){ //potentially add triangle shape to this class so can be deleted when fired
        //vectorX=0;vectorY=0;
        //this.view = view;
    }

    public void fireBullet(double vectorX,double vectorY,int x2,int y2){
        kill(false);
        x=x2;y=y2;startX=(int)x;startY=(int)y;
        this.vectorX=vectorX;
        this.vectorY=vectorY;
        fired = true;
        soundManager.play(bulletFired,false);
    }
//    public Explosion checkAndExplode(){
//        if((x>(endX-20)&&x<(endX+20)) && (y>(endY-20)&&y<(endY+20))){
//            kill(true);
//            return new Explosion(endX,endY,5,200);
//        }
//        else return null;
//    }
    public void setEnd(int endX, int endY){
        this.endX=endX;
        this.endY=endY;
        usedUp=true;
    }
    public void move() {
        if(!((x>(endX-5)&&x<(endX+5)) && (y>(endY-5)&&y<(endY+5)))){
            x = x + vectorX*speed;
            y = y + vectorY*speed;
        }
        else{
            bulletFired.stop();
            kill(true);
            }
    }
    public void drawBullet(Graphics g){
        if(!isKilled()) {
            g.setColor(Color.white);
            g.fillOval((int) x, (int) y, 3, 3);
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.blue);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(startX,startY,(int) x+1,(int) y+1);
            g.fillRect(endX-1,endY-12, 3, 24);
            g.fillRect(endX-12, endY-1, 24, 3);
        }
        else if(!exploded&&isKilled()&&fired){
            exploded=true;
            View.explosions.add(new Explosion(endX,endY,5,40));
            if(!explosionSoundPlayed) {
                soundManager.play(soundManager.getClip("regularExplosion", -10), false);
                explosionSoundPlayed=true;
            }
        }
    }
    public void resetBullet(){
        kill(true);
        vectorX=0;
        vectorY=0;
    }
}

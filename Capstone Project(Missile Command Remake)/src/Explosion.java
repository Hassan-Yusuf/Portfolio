import java.awt.*;
import java.math.*;
import java.util.Vector;

public class Explosion extends gameObject{
    private int r,rmax;
    public Explosion(double x,double y,int r,int radiusMax){
        this.r=r;
        this.rmax=radiusMax;
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(gameObject AABB) { // Circle - AABB collision
        // Get center point of circle

        double centerX = x;
        double centerY = y;

        // Calculate center and half-extents of AABB box

        double width = (AABB instanceof enemyAgent) ? constants.enemyAgent_W: (AABB instanceof Aircraft) ? constants.Aircraft_W:
                (AABB instanceof Satellite) ? constants.Satellite_W: (AABB instanceof City) ? constants.City_W: (AABB instanceof Silo) ? constants.Silo_W:0;
        double height = (AABB instanceof enemyAgent) ? constants.enemyAgent_H: (AABB instanceof Aircraft) ? constants.Aircraft_H:
                (AABB instanceof Satellite) ? constants.Satellite_H: (AABB instanceof City) ? constants.City_H: (AABB instanceof Silo) ? constants.Silo_H:0;
        double halfWidth = width/2;
        double halfHeight = height/2;
        double aabbCenterX = AABB.getX() + halfWidth;
        double aabbCenterY = AABB.getY() + halfHeight; //Gets point closest from the center of the square to the center of the circle.

        // Calculate difference between centers of circle and AABB box
        double diffX = centerX - aabbCenterX;
        double diffY = centerY - aabbCenterY;

        // Clamp the difference to the AABB box
        double clampedX = Math.max(-halfWidth, Math.min(diffX, halfWidth));
        double clampedY = Math.max(-halfHeight, Math.min(diffY, halfHeight));

        // Calculate the closest point on AABB box to center of circle
        double closestX = aabbCenterX + clampedX;
        double closestY = aabbCenterY + clampedY;

        // Calculate distance between the closest point and center of circle
        double distX = closestX - centerX;
        double distY = closestY - centerY;
        double dist = Math.sqrt(distX * distX + distY * distY);

        // Check if the distance is less than or equal to the radius of the circle
        return dist <= r;
    }

    public boolean updateTillMax(){ //I put the update here as its better within class this time as there will be multiple explosions
        if(r != rmax) {
            r++;
            return true;
        } //this function will increase the radius till it reaches the maximum and return true to pass to the bullet
        else {kill(true);return false;} //when it reaches the maximum it returns false to say the bullet is done being fired. so it doesn't waste processing.
    }

    public void drawExplosion(Graphics g){
        g.setColor(new Color(View.color,View.color,View.color));
        g.fillOval((int)x-r,(int)y-r,2*r,2*r);

    }

    public void colliding(gameObject other){

    }

    public int getR(){
        return r;
    }
}

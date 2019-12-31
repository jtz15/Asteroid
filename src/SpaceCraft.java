
import java.awt.Polygon;

public class SpaceCraft extends VectorSprite{
    int lives;
    public SpaceCraft (){
        shape = new Polygon();
        shape.addPoint(0,-25);
        shape.addPoint(15,10);
        shape.addPoint(-15,10);
        drawShape = new Polygon();
        drawShape.addPoint (0,-25);
        drawShape.addPoint (15,10);
        drawShape.addPoint (-15,10);
        xposition = 450;
        yposition = 300;
        ROTATION = 0.1;
        THRUST = 1;
        active = true;
        lives = 3;
        
    }
    
    public void accelerate(){
        xspeed += Math.cos(angle - Math.PI/2) * THRUST;
        yspeed += Math.sin(angle - Math.PI/2) * THRUST;
    }
    
    public void deaccelerate(){
        xspeed -= Math.cos(angle - Math.PI/2) * THRUST;
        yspeed -= Math.sin(angle - Math.PI/2) * THRUST;
    }
    
    public void rotateRight(){
        angle += ROTATION;
    }
    
    public void rotateLeft(){
        angle -= ROTATION;
    }
    
    public void hit(){
        active = false;
        counter = 0;
    }
    
    public void reset(){
        xposition = 450;
        yposition = 300;
        xspeed = 0;
        yspeed = 0;
        angle = 2 * Math.PI;
        active = true;
        lives --;
    }
    
    public void lifeIncrease(){
        lives ++;
    }
    }




import java.awt.Polygon;

public class Bullet extends VectorSprite{
   
    public Bullet(double x, double y, double a){
        shape = new Polygon();
        shape.addPoint(0,0);
        shape.addPoint(0,6);
        shape.addPoint(8,0);
        shape.addPoint (8,6);
        drawShape = new Polygon();
        drawShape.addPoint (0,0);
        drawShape.addPoint (0,6);
        drawShape.addPoint (8,0);
        drawShape.addPoint (8,6);
        xposition = x;
        yposition = y;
        angle = a;
        ROTATION = 0.1;
        THRUST = 5;
        active = true;

        xspeed += Math.cos(a - Math.PI/2) * THRUST;
        yspeed += Math.sin(a - Math.PI/2) * THRUST;
    }
}

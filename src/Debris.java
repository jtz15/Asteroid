
import java.awt.Polygon;

public class Debris extends VectorSprite{
    double a;
    public Debris(double x, double y){
        shape = new Polygon();
        shape.addPoint(1,1);
        shape.addPoint(-1,-1);
        shape.addPoint(-1,1);
        shape.addPoint (1,-1);
        drawShape = new Polygon();
        drawShape.addPoint (1,1);
        drawShape.addPoint (-1,-1);
        drawShape.addPoint (-1,1);
        drawShape.addPoint (1,-1);
        xposition = x;
        yposition = y;
        a = Math.random() * 2 * Math.PI;

        xspeed += Math.cos(a - Math.PI/2) * a;
        yspeed += Math.sin(a - Math.PI/2) * a;
}
}

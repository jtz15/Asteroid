
import java.awt.Polygon;

public final class Asteroids extends VectorSprite{
   double h;
   double a;
   int size;
   

    public Asteroids(){
        size = 3;
        initAsteroids();
    }
    
    public Asteroids(double x, double y, int s){
        size = s;
        initAsteroids();
        xposition = x;
        yposition = y;
        }
    
    public void initAsteroids(){
            shape = new Polygon();
            shape.addPoint(6 * size, 3/5 * size);
            shape.addPoint(1 * size, 7 * size);
            shape.addPoint(-5 * size, 2 * size);
            shape.addPoint(-17/5 * size, -3* size);
            shape.addPoint(4 * size ,-7 * size);
            drawShape=new Polygon();
            drawShape.addPoint(6 * size, 3/5 * size);
            drawShape.addPoint(1 * size, 7 * size);
            drawShape.addPoint(-5 * size, 2 * size);
            drawShape.addPoint(-17/5 * size, -3 * size);
            drawShape.addPoint(4 * size, -7 * size);
            ROTATION = Math.random()/2-0.25;
            THRUST = 0.5;
            active = true;
            h = Math.random() * 1;
            a = Math.random() * 2 * Math.PI;
            xspeed += Math.cos(a - Math.PI/2) * h + Math.random() * 2 / size;
            yspeed += Math.sin(a - Math.PI/2) * h + Math.random() * 2 / size;
            h = Math.random() * 400+100;
            xposition = Math.cos(a-Math.PI/2)*h + 450;
            yposition = Math.sin(a-Math.PI/2)*h + 300;

}
    
   @Override
        public void updatePosition(){
            angle += ROTATION;
            super.updatePosition();
        }
        
        
        
}

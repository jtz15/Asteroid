import java.applet.*;
import java.awt.*;

public class Background extends Applet {
     Image background;

     @Override
     public void init() {
          setSize(900,600);
          background = getImage(getCodeBase(), "outer-space-hd-wallpaper.JPG");
          BackGroundPanel bgp = new BackGroundPanel();
          bgp.setLayout(new FlowLayout());
          bgp.setBackGroundImage(background);
          setLayout(new BorderLayout());
          add(bgp);
     }
}

class BackGroundPanel extends Panel {
     Image background;

     BackGroundPanel() {
          super();
     }

     @Override
     public void paint(Graphics g) {
          g.drawImage(getBackGroundImage(), 0, 0,
              (int)getBounds().getWidth(), (int)getBounds().getHeight(), this);
     }

     public void setBackGroundImage(Image backGround) {
          this.background = backGround;    
     }

     private Image getBackGroundImage() {
          return background;    
     }
}


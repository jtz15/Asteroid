
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;
import java.applet.AudioClip;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Asteroid extends Applet implements KeyListener, ActionListener , MouseListener{
    SpaceCraft ship; 
    
    Background background;
    Timer timer;
    Image offscreen; 
    Graphics offg;
    Boolean upKey;
    Boolean rightKey;
    Boolean leftKey;
    Boolean downKey;
    Boolean spaceKey;
    ArrayList<Asteroids> asteroidList; 
    ArrayList<Bullet> bulletList;
    ArrayList<Debris> explosionList;
    int score;
    Boolean doneReading;
    Boolean clicked;
    AudioClip shipAst;
    AudioClip bullAst;
    AudioClip thruster;
    AudioClip laser;
    AudioClip battle;
    int level;
    int sizeArray;
    boolean enterKey;
    FileWriter hsFile;
    FileReader hrFile;
    PrintWriter pw;
    BufferedReader br;
    int highscore;
    String name;

    @Override
    public void init() {
        this.setSize(900,600);
        this.addKeyListener(this);
        this.addMouseListener(this);
        ship = new SpaceCraft();
        timer = new Timer(20, this);
        offscreen = createImage(this.getWidth(), this.getHeight());
        offg = offscreen.getGraphics();
        upKey = false;
        leftKey = false;
        rightKey = false;
        downKey = false;
        spaceKey = false;
        asteroidList = new ArrayList<>();
        doneReading = false;
        clicked = false;
        level = 1;
        for (int i = 0; i < 3; i++){
            asteroidList.add(new Asteroids());
        }
        bulletList = new ArrayList();
        explosionList = new ArrayList();
        background = new Background();
        score = 0;
        laser = getAudioClip(getCodeBase(), "laser79.wav");
        shipAst = getAudioClip(getCodeBase(), "explode1.wav");
        bullAst = getAudioClip(getCodeBase(), "explode0.wav");
        thruster = getAudioClip(getCodeBase(), "thruster.wav");
        battle = getAudioClip(getCodeBase(), "Star Wars Battle Theme FULL.wav");
        sizeArray = asteroidList.size();
        name = "";
       
        
    }
    
    public String readFile(){
        String str = "";//= br.readLine();
        try{ hrFile = new FileReader("HS.txt");
        br = new BufferedReader(hrFile);
        str = br.readLine();
        while((br.readLine()!= null)){
        str=br.readLine();
        }
        } catch (IOException ex) {System.out.println("ERROR!");}
    return str;
    }
    
    
    @Override
    public void start(){
        timer.start();
        battle.loop();
        
    }

    @Override
    public void stop(){
        timer.stop();
    }

    @Override
     public void actionPerformed(ActionEvent e) {
        
        mouseCheck();
        
        keyCheck();
        
        respawnShip();
        
        ship.updatePosition();
        
        for (int i = 0; i < asteroidList.size (); i++){
            asteroidList.get(i).updatePosition();
            asteroidList.get(i).wrapAround();
            
            }
        for (int i = 0; i < bulletList.size(); i++){
            bulletList.get(i).updatePosition();
            if (bulletList.get(i).counter == 100 || bulletList.get(i).active == false){
                bulletList.remove(i);
            }
                }
        for (int i = 0; i < explosionList.size(); i++){
            explosionList.get(i).updatePosition();
            if (explosionList.get(i).counter == 10)
                explosionList.remove(i);
        }
        checkCollision();
        
        ship.wrapAround();
        
        checkAsteroidDestruction();
    }
    
    @Override
    public void paint(Graphics g) {
        offg.setColor(Color.BLACK);
        offg.fillRect(0, 0, 900, 600);
  
        if (!doneReading){ 
            offg.setColor(Color.RED);
            offg.setFont(offg.getFont().deriveFont(Font.BOLD));
            offg.setFont(offg.getFont().deriveFont(60.0f));
            offg.drawString("ASTEROIDS", 275, 300);
            offg.setColor(Color.WHITE);
            offg.setFont(offg.getFont().deriveFont(35.0f));
            offg.setFont(offg.getFont().deriveFont(Font.PLAIN));
            offg.drawString("Name of Commander: " + name, 350, 400);
            offg.setFont(offg.getFont().deriveFont(25.0f));
            offg.setFont(offg.getFont().deriveFont(Font.ITALIC));
            offg.drawString("Click ENTER to begin..." + name, 350, 440);
        }
        //background.paint(offg);    
        if (doneReading && ship.lives >= 1 && asteroidList.isEmpty() != true ){         
             for (int i = 0; i < explosionList.size(); i++){
                offg.setColor(Color.YELLOW);
                explosionList.get(i).paint(offg);}
            if (ship.active){
                offg.setColor(Color.GREEN);
                ship.paint(offg);
                    for (int i = 0; i < bulletList.size(); i++){
                        offg.setColor(Color.BLUE);
                        bulletList.get(i).paint(offg);}
         }
        }
        
        offg.setColor(Color.RED);
        for (int i = 0; i < asteroidList.size(); i++){
            asteroidList.get(i).paint(offg);
        }

         offg.setColor(Color.WHITE);
         offg.setFont(offg.getFont().deriveFont(25.0f));
         offg.drawString("Lives: " + ship.lives, 10, 500);
         
         offg.setColor(Color.WHITE);
         offg.setFont(offg.getFont().deriveFont(25.0f));
         offg.drawString("Score: " + score, 10, 540);
         
         offg.setColor(Color.WHITE);
         offg.setFont(offg.getFont().deriveFont(25.0f));
         offg.drawString("Level: " + level, 10, 580);
         
         offg.setColor(Color.WHITE);
         offg.setFont(offg.getFont().deriveFont(25.0f));
         offg.drawString("Highscore: " + readFile(), 10, 460);
         
        if (asteroidList.isEmpty() && ship.lives >= 1  ){
            offg.setFont(offg.getFont().deriveFont(Font.BOLD));
            offg.setFont(offg.getFont().deriveFont(25.0f));
            offg.setColor(Color.WHITE);
            offg.drawString("WAVE " + level + " PASSED!", 350, 300);
            offg.setFont(offg.getFont().deriveFont(15.0f));
            offg.drawString("Press enter to begin wave " + (level + 1), 354, 350);
        } 
           
        if (ship.lives == 0){
            battle.stop();
            offg.setFont(offg.getFont().deriveFont(Font.BOLD));
            offg.setFont(offg.getFont().deriveFont(30.0f));
            offg.setColor(Color.WHITE);
            offg.drawString("GAME OVER!", 370, 300);
            checkHighscore();
        }
        g.drawImage(offscreen,0,0,this);
        repaint();
    }
    

    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            clicked = true;
            }
}
    @Override
    public void mousePressed(MouseEvent e) {
     if (e.getButton() == MouseEvent.BUTTON1){
            clicked = true;
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
          if (e.getButton() == MouseEvent.BUTTON1){
            clicked = false;
            }}
    @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {}

    
    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { 
            leftKey = true;
            //ship.angle -= 0.1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightKey = true;
            //ship.angle += 0.1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_UP){
            upKey = true;
            thruster.stop();
            //ship.accelerate();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            downKey = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            spaceKey = true;
            
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            enterKey = true;
        }

        repaint();
}

    @Override
    public void keyTyped(KeyEvent e) {
    }
    

    @Override
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            leftKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP){
            upKey = false;
            thruster.loop();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            downKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            spaceKey = false;
            laser.play();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            enterKey = false;
            
        }

    }
    
     public void keyCheck(){
        if (upKey){
            ship.accelerate();
           }
        if (leftKey ){
            ship.rotateLeft();}
        if(rightKey){
            ship.rotateRight();}
        if (downKey){
            ship.deaccelerate();
        }
        if (spaceKey){
            fireBullet();
        }
        
        if (enterKey){
            if (asteroidList.isEmpty() && ship.lives >= 1  ){
                offg.setColor(Color.BLACK);
                offg.fillRect(0, 0, 900, 600);
                ship.reset();
                updateLevel();}
        
    }
     }
      
     public void mouseCheck(){
         if (clicked){
             doneReading = true;
         }
    }
     
     
    public void checkCollision (){
        for (int i = 0; i < asteroidList.size(); i++){
            double r;
            if (collision(ship,asteroidList.get(i)) == true){
                ship.hit();
                r = Math.random() * 3 + 5;
                shipAst.play();
                for (int j = 0; j < r; j++){
                explosionList.add(new Debris(ship.xposition, ship.yposition));
            }
            }
            for (int j = 0; j < bulletList.size(); j++){
                if (collision(bulletList.get(j), asteroidList.get(i)) == true){
                    bulletList.get(j).active = false;
                    asteroidList.get(i).active = false;
                    r = Math.random() * 3 + 5;
                    bullAst.play();
                    for (int p = 0; p < r; p++){
                    explosionList.add(new Debris(asteroidList.get(i).xposition, asteroidList.get(i).yposition));}
                    score = score + 30 / asteroidList.get(i).size;
                    
            }
            }
            
            
        }
            
    }
    
    public Boolean collision(VectorSprite thing1, VectorSprite thing2){
        int x,y;
        for (int i = 0; i < thing1.drawShape.npoints; i++){
           x = thing1.drawShape.xpoints[i];
           y = thing1.drawShape.ypoints[i];
            if (thing2.drawShape.contains(x, y)){
                return true;
    }        
    }
        for (int i = 0; i < thing2.drawShape.npoints; i++){
           x = thing2.drawShape.xpoints[i];
           y = thing2.drawShape.ypoints[i];
           if (thing1.drawShape.contains(x, y)){
            return true;
    }        
    }
    return false;
}
    
    public void respawnShip(){
        if (ship.active == false && ship.counter > 50 && isRespawnSafe() && ship.lives > 0){
            ship.reset();
        }
    }
    
    public boolean isRespawnSafe(){
        double a,b,c;
        for (int i=0; i < asteroidList.size(); i++){
            a = asteroidList.get(i).xposition - 450;
          
            b = asteroidList.get(i). yposition - 300;
            
            c = Math.sqrt(a*a + b*b);
            
            if (c < 100){
                return false;
            }
            
        }
        return true;
    
    }
public void fireBullet(){
    if (ship.counter > 5 && ship.active){
        bulletList.add(new Bullet(ship.xposition, ship.yposition, ship.angle));
        ship.counter = 0;
        laser.play();
    }
}

public void checkAsteroidDestruction(){
    for (int i = 0; i < asteroidList.size (); i++){
        if (asteroidList.get(i).active == false){
            if (asteroidList.get(i).size > 1){asteroidList.add(new Asteroids(asteroidList.get(i).xposition, asteroidList.get(i).yposition, asteroidList.get(i).size - 1));
                    asteroidList.add(new Asteroids(asteroidList.get(i).xposition, asteroidList.get(i).yposition, asteroidList.get(i).size - 1));
            }
        asteroidList.remove(i);
        }   
}
}

public void updateLevel(){
        if (asteroidList.isEmpty()){
            level ++;
            ship.lifeIncrease();
            sizeArray += 3;
            for(int i = 0; i < sizeArray; i++)
                asteroidList.add(new Asteroids());
        } 
            }

public void checkHighscore(){
        if (score > highscore){
            highscore = score;
             try {
            hsFile = new FileWriter("HS.txt");
            pw = new PrintWriter("HS.txt");
            pw.println(highscore);
            pw.close();
        } catch (IOException ex) {System.out.println("ERROR!");}
}
            
}
}



    

    



package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject{
    BufferedImage pwrImage;
    int x,y,time;

    public PowerUp(int x,int y,BufferedImage img){
        this.x = x;
        this.y = y;
        this.pwrImage = img;
    }
    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.pwrImage,0,0,null);
    }

    @Override
    public void update() {

    }
}

package tankrotationexample.game;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject{
    BufferedImage pwrImage;
    int x,y,time;
    private Rectangle hitBox;
    public PowerUp(int x,int y,BufferedImage img){
        this.x = x;
        this.y = y;
        this.pwrImage = img;
        hitBox = new Rectangle(pwrImage.getWidth(),pwrImage.getHeight());
    }
    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.pwrImage,x,y,null);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }
}

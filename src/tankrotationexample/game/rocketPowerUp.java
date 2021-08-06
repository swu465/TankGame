package tankrotationexample.game;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class rocketPowerUp extends PowerUp{
    private BufferedImage img;
    int x,y,state = 1;
    private Rectangle hitBox;
    public rocketPowerUp(int x, int y, BufferedImage pwrImg){
        this.x = x;
        this.y = y;
        this.img = pwrImg;
        hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
    }
    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,x,y,null);
    }

    @Override
    public void update() {
        if(state == 0){
            hitBox.setBounds(0,0,0,0);
        }
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public int getState() {
        return state;
    }

    public void pickedUp(){
        state = 0;
    }

}

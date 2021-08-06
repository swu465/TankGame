package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class speedPowerUp extends PowerUp{
    private BufferedImage img;
    int x,y,state = 1;
    private Rectangle hitBox;

    public speedPowerUp(int x, int y, BufferedImage speed) {
        this.x = x;
        this.y = y;
        this.img = speed;
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

    @Override
    public void pickedUp() {
        state = 0;
    }
}

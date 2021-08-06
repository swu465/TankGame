package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HPPowerUp extends PowerUp{
    private int x,y,state;
    private BufferedImage heartImg;
    private Rectangle hitBox;

    public HPPowerUp(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.heartImg = img;
        hitBox = new Rectangle(x,y,this.heartImg.getWidth(),this.heartImg.getHeight());
        state = 1;
    }
    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.heartImg,x,y,null);
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

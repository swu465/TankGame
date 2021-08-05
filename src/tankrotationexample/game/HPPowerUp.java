package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HPPowerUp extends GameObject{
    private BufferedImage img;
    int x,y,state = 1;
    private Rectangle hitBox;

    @Override
    public void drawImage(Graphics g) {

    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public int getState() {
        return state;
    }
}

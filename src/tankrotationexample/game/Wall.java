package tankrotationexample.game;

import java.awt.*;

public abstract class Wall extends GameObject{
    public abstract void drawImage(Graphics g);
    public void update(){

    }
    public abstract int getHP();
    public abstract void damaged();
}

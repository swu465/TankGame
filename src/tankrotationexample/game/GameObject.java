package tankrotationexample.game;

import java.awt.*;

public abstract class GameObject {
    public abstract void drawImage(Graphics g);
    public abstract void update();
    public abstract Rectangle getHitBox();
    //maybe add damaged
    //public abstract void damaged();
    public abstract int getState();
}

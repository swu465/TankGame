package tankrotationexample.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Rocket extends GameObject{
    int R = 7;
    int x,y,vx,vy,angle,damage;
    public BufferedImage rocketImage;
    private Rectangle hitBox;
    public Rocket(int x,int y, int angle, BufferedImage rocket){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.rocketImage = rocket;
        damage = 2;
        this.hitBox = new Rectangle(x,y,this.rocketImage.getWidth(),this.rocketImage.getHeight());
    }
    public void moveForwards(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitBox.setLocation(x,y);
    }
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
        rotation.rotate(Math.toRadians(angle),this.rocketImage.getWidth()/2.0,this.rocketImage.getHeight());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.rocketImage,rotation,null);
        //g2d.setColor(Color.CYAN);
        //g2d.drawRect(x,y,this.rocketImage.getWidth(),this.rocketImage.getHeight());
    }

    @Override
    public void update() {
        moveForwards();
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }
    @Override
    public int getState() {
        return 0;
    }

}

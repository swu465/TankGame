package tankrotationexample.game;
import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
	int R = 7;
	int x,y,vx,vy,angle,damage;
	public BufferedImage bulletImage;
	Rectangle hitBox;

	public Bullet(int x, int y, int angle, BufferedImage bullet){
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.bulletImage = bullet;
		this.hitBox = new Rectangle(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
	}
	public void moveForwards(){
		vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
	}
	public void checkBorder(){
		if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
	}
	@Override
	public void update(){
		moveForwards();
	}

	@Override
	public Rectangle getHitBox() {
		return this.hitBox;
	}

	@Override
	public void drawImage(Graphics g){
		AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
		rotation.rotate(Math.toRadians(angle),this.bulletImage.getWidth()/2.0,this.bulletImage.getHeight());
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.bulletImage,rotation,null);
		g2d.setColor(Color.CYAN);
		g2d.drawRect(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
		
	}
}

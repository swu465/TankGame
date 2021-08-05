package tankrotationexample.game;
import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
	int R = 7;
	int x,y,vx,vy,angle,damage;
	public BufferedImage bulletImage;
	private Rectangle hitBox;

	public Bullet(int x, int y, int angle, BufferedImage bullet){
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.bulletImage = bullet;
		damage = 1;
		this.hitBox = new Rectangle(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
	}
	public void moveForwards(){
		vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitBox.setLocation(x,y);
	}
	@Override
	public void update(){
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

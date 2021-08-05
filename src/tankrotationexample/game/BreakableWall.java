package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
	int x,y;
	int state = 2;
	BufferedImage wallImage,dyingWall;
	private Rectangle hitBox;
	
	public BreakableWall(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.wallImage = image;
		hitBox = new Rectangle(x,y,wallImage.getWidth(),wallImage.getHeight());
	}
	@Override
	public void drawImage(Graphics g){
		if(state > 0){
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(this.wallImage,x,y,null);
			g2.setColor(Color.red);
			g2.drawRect(x,y,wallImage.getWidth(),wallImage.getHeight());
		}
	}

	@Override
	public void update() {
		//draw image?
	}

	@Override
	public Rectangle getHitBox() {
		return this.hitBox;
	}
	public int getHP(){
		return state;
	}
	public void damaged(){
		state--;
	}

}

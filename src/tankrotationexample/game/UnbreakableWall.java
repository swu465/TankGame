package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall{
	int x,y;
	int state = 9;
	BufferedImage wallImage;
	private Rectangle hitBox;
	
	public UnbreakableWall(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.wallImage = image;
		this.hitBox = new Rectangle(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
	}
	
	@Override
	public void drawImage(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.wallImage,x,y,null);
		//g2.setColor(Color.blue);
		//g2.drawRect(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
	}
	@Override
	public Rectangle getHitBox() {
		return this.hitBox.getBounds();
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public void update() {
	}

	@Override
	public void damaged() {
	}
}

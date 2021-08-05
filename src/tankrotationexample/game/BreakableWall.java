package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
	int x,y;
	int state = 2;
	BufferedImage wallImage;
	private Rectangle hitBox;
	
	public BreakableWall(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.wallImage = image;
		hitBox = new Rectangle(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
	}
	@Override
	public void drawImage(Graphics g){
		if(state > 0){
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(this.wallImage,x,y,null);
			g2.setColor(Color.blue);
			g2.drawRect(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
		}
	}

	@Override
	public void update() {
		if(state == 0){
			hitBox.setBounds(0,0,0,0);
		}
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
	public void damaged(){
		state--;
	}

}

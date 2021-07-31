package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
	int x,y;
	int state = 2;
	BufferedImage wallImage,dyingWall;
	
	public BreakableWall(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.wallImage = image;
	}
	@Override
	public void drawImage(Graphics g){
		if(state > 0){
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(this.wallImage,x,y,null);
		}
	}

	@Override
	public void update() {
		//draw image?
	}

}

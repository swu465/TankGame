package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall{
	int x,y;
	int state;
	BufferedImage wallImage;
	
	public UnbreakableWall(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.wallImage = image;
	}
	
	@Override
	public void drawImage(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.wallImage,x,y,null);
	}

	@Override
	public void update() {

	}
}

package tankrotationexample.game;

public class BreakableWall extends Wall{
	int x,y;
	int state;
	BufferedImage wallImage;
	
	public BreakableWall(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.wallImage = image;
	}
	@Override
	public void drawWall(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(this.wallImage,x,y,null);
	}
	
}

package tankrotationexample.game;



import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{


    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
	private ArrayList<Bullet> ammoDrum;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;


    private Rectangle hitBox;
    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
	//static long frameCount = 0;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
		this.ammoDrum = new ArrayList<>();

    }
    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }
    void setX(int x){ this.x = x; }

    void setY(int y) { this. y = y;}
    public int getX(){
        return this.x;
    }
    public  int getY(){
        return this.y;
    }
    public int getSplitY(){
        int number;
        if(y <= GameConstants.GAME_SCREEN_HEIGHT/2){
            number = 0;
        }else{
            number = y - (GameConstants.GAME_SCREEN_HEIGHT/2);
        }
        return number;
    }
    public int getSplitX(){
        int num;
        if(x <= GameConstants.GAME_SCREEN_WIDTH/4){
            num = 0;
        }else{
            num = x - (GameConstants.GAME_SCREEN_WIDTH/4);
        }
        return num;
    }
    void toggleShootPressed(){
        this.ShootPressed = true;
    }
    void unToggleShootPressed(){
        this.ShootPressed = false;
    }
    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
@Override
public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed && TRE.frameCount % 30 == 0){
            System.out.println("Pew Pew");
			Bullet b = new Bullet(x,y,(int)angle,GameResource.get("bullet"));
			this.ammoDrum.add(b);
        }
		this.ammoDrum.forEach(bullet->bullet.update());
		//to prevent bugs, do updates first and then do reads
		/*for( int x = 0; x < this.ammo.size(); x++){
			this.ammoDrum.get(x).update();
		}*/
		
        //this.hitBox.setLocation(x,y);
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }



//write the same function, but for keeping the split screen on the screen.
    void checkCollision(){

    }
    private void checkBorder() {
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
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

//to draw tank in middle. for left screen width/4, screen height/2
    //world width + height - cordinnate and then do divisions?
    //x - screenw/4
    //y- - screeny /2
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        //fix this later
		this.ammoDrum.forEach(bullet->bullet.drawImage(g));
		//????
		g2d.setColor(Color.RED);
		//g2d.rotate(Math.toRadians(angle),bounds.x+bounds.width/2,bounds.y+bounds.height/2);
		g2d.drawRect(x,y,this.img.getWidth(),this.img.getHeight());
    }


    public void collisionHappened() {
        this.unToggleDownPressed();
        this.unToggleLeftPressed();
        this.unToggleRightPressed();
        this.unToggleUpPressed();
    }
}

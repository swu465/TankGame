package tankrotationexample.game;



import tankrotationexample.GameConstants;

import javax.security.sasl.RealmChoiceCallback;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{


    private int x,prevX;
    private int y,prevY;
    private int vx;
    private int vy;
    private float angle;
	private ArrayList<GameObject> ammoDrum;
	private int HP = 4,state = 3;
	private boolean shootRockets,goFast;
	private int rocketCount;

    private int R = 2;
    private final float ROTATIONSPEED = 3.0f;


    private Rectangle hitBox;
    private BufferedImage img,healthBar;
    private BufferedImage explosionImg;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.prevX = x;
        this.prevY = y;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
		this.ammoDrum = new ArrayList<>();
		shootRockets = false;
		goFast = false;
		rocketCount = 0;
		this.healthBar = GameResource.get("fullHP");
    }
    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    @Override
    public int getState() {
        return state;
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
        int number,marginY=0,returnY;
        if(y <= GameConstants.GAME_SCREEN_HEIGHT/2){
            number = 0;
        }else{
            number = Math.min(y - (GameConstants.GAME_SCREEN_HEIGHT/2),1050);
        }
        return number;
    }
    public int getSplitX(){
        int num,marginX = 0,returnX;
        if(x <= GameConstants.GAME_SCREEN_WIDTH/4){
            num = 0;
        }else{
            num = Math.min(x - (GameConstants.GAME_SCREEN_WIDTH / 4), 1365);
        }
        return num;
    }
    public int ammoDrumSize(){
        return ammoDrum.size();
    }
    public GameObject ammoDrumGetBullet(int x){
        return ammoDrum.get(x);
    }
    public void ammoDrumRemoveBullet(int index){
        ammoDrum.remove(index);
        System.out.println(ammoDrumSize());
    }

    public void damaged(GameObject item){
        if(item instanceof Bullet){
            HP--;
            //do 1 damage
        }else if(item instanceof Rocket){
            HP--;
            HP--;
            //do 2 damage
        }
        System.out.println("ow");
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
        switch (HP){
            case 0:
                //img = GameResource.get("explosion");
                state--;
                //reset hp to 4
                HP = 4;
                break;
            case 1:
                healthBar = GameResource.get("threeHit");
                break;
            case 2:
                healthBar = GameResource.get("twoHit");
                break;
            case 3:
                healthBar = GameResource.get("oneHit");
                break;
            case 4:
                healthBar = GameResource.get("fullHP");
                break;
        }
        if(goFast && TRE.frameCount == 3500){
            R = 2;
            goFast = false;
        }
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
        if(this.ShootPressed && TRE.frameCount % 30 == 0 && !shootRockets){
            System.out.println("Pew Pew");
			Bullet b = new Bullet(x,y,(int)angle,GameResource.get("bullet"));
			this.ammoDrum.add(b);
        }else if(this.ShootPressed && TRE.frameCount % 30 == 0 && rocketCount > 0){
            Rocket r = new Rocket(x,y,(int)angle,GameResource.get("rocket"));
            this.ammoDrum.add(r);
            rocketCount--;
            if(rocketCount == 0){
                shootRockets = false;
            }
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
        prevX = x;
        prevY = y;
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }

    private void moveForwards() {
        prevX = x;
        prevY = y;
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }



//write the same function, but for keeping the split screen on the screen.
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
        //rotation.rotate(Math.toRadians(angle),this.healthBar.getWidth()/2.0,this.healthBar.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.drawImage(this.healthBar,x,y+50,null);
        g2d.drawString(String.valueOf(state),x,y+60);
        //fix this later?
		this.ammoDrum.forEach(bullet->bullet.drawImage(g));
		//????
		g2d.setColor(Color.RED);
		//hitbox bounds?
		//g2d.rotate(Math.toRadians(angle),bounds.x+bounds.width/2,bounds.y+bounds.height/2);
		g2d.drawRect(x,y,this.img.getWidth(),this.img.getHeight());
    }

    public void collisionHappened() {
        this.x = prevX;
        this.y = prevY;
    }
    public void setPowerUp(PowerUp item){
        if(item instanceof rocketPowerUp){
            shootRockets = true;
            rocketCount = 5;
        }else if(item instanceof speedPowerUp){
           R = 4;
           goFast = true;
        }else if(item instanceof HPPowerUp){
            HP = 4;
        }
    }

    public void reset(){
        hitBox.setBounds(x,y,this.img.getWidth(),this.img.getHeight());
        HP = 4;
        state = 3;
        this.unToggleShootPressed();
        this.unToggleUpPressed();
        this.unToggleRightPressed();
        this.unToggleLeftPressed();
        this.unToggleDownPressed();
    }
}

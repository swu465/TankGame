/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import jdk.jshell.execution.Util;
import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1,t2;
    private Launcher lf;
    private long tick = 0;
    private static ArrayList<GameObject> gameObjects;
    int[] wallHP;
	static long frameCount = 0;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    public static void damageWall(int index) {
        BreakableWall wall= (BreakableWall) gameObjects.get(index);
        wall.damaged();
        gameObjects.add(index,wall);
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
				gameObjects.forEach(gameObjects -> gameObjects.update());
                //this.t1.update(); // update tank
				//this.t2.update();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                //System.out.println(t1);
				frameCount++;
                CollisionDetection.checkBulletsOne(t1,t2,gameObjects);
                CollisionDetection.checkBulletsTwo(t1,t2,gameObjects);
                CollisionDetection.checkPlayers(t1,t2,gameObjects);
               CollisionDetection.checkPlayers(t2,t1,gameObjects);

				/*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                /*if(this.tick > 2000){
                    this.lf.setFrame("end");
                    return;
                }*/
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
		this.t2.setX(500);
		this.t2.setY(500);
		//need to redo hitboxes? or walls
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage godWall = null;
        BufferedImage normalWall = null;
        gameObjects = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("map2.txt"));
            BufferedReader mapReader = new BufferedReader(isr);
            //0 = nothing
            //2 = breakable wall
            // 3 = unbreakable wall
            //4 = powerups
            //9 = unbreakable calls. not used in collisions.
            String row = mapReader.readLine();
            if(row == null){
                throw new IOException("No data in file");
            }
            String[]  mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
            for(int curRow = 0; curRow< numRows; curRow++){
                row = mapReader.readLine();
                //System.out.println("for loop "+row);
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++){
                    //System.out.println(mapInfo[curCol]);
                    switch (mapInfo[curCol]){
                        case "2":
                            BreakableWall brWall = new BreakableWall(curCol*30,curRow*30,GameResource.get("breakableWall"));
                            System.out.println("Breakable wall");
                            this.gameObjects.add(brWall);
                            break;
                        case "3":
                        case "9":
                            UnbreakableWall ubrWall = new UnbreakableWall(curCol*30,curRow*30,GameResource.get("unbreakableWall"));
                            this.gameObjects.add(ubrWall);
                            //System.out.println("unbreakable wall");
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        //ask prof about Tank t1 = ... here vs private member
        t1 = new Tank(300, 300, 0, 0, 0, GameResource.get("tankOne"));
        t2 = new Tank(1000,800,0,0,180,GameResource.get("tankTwo"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
		this.gameObjects.add(t1);
		this.gameObjects.add(t2);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        this.gameObjects.forEach(wall->wall.drawImage(buffer));
        //this.t1.drawImage(buffer);
		//this.t2.drawImage(buffer);
        //drawCamera(world,g2,t1);
        //drawCamera(world,g2,t2);
        int offsetY = GameConstants.WORLD_HEIGHT - (GameConstants.GAME_SCREEN_HEIGHT/2);
        int offsetX = GameConstants.WORLD_WIDTH - (GameConstants.GAME_SCREEN_WIDTH/4);

		BufferedImage leftHalf = world.getSubimage(t1.getSplitX(),
                t1.getSplitY(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);
		//System.out.println("tank 1:\nx: "+t1.getSplitX()+"\ty: "+t1.getSplitY());
		//System.out.println("tank 2:\nx: "+t2.getSplitX()+"\ty: "+t2.getSplitY());
		//x = tank.getX(),y=tank.getY()
        //do not follow this exactly. was from video. will need something like this
		BufferedImage rightHalf = world.getSubimage(t2.getSplitX(),t2.getSplitY(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
		BufferedImage miniMap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_WIDTH);
		g2.drawImage(leftHalf,0,0,null);
		g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2+4,0,null);
		g2.scale(.15,.15);
		//find place to put minimap
		g2.drawImage(miniMap,3250,0,null);
		//g2.scale();
        //g2.drawImage(world,0,0,null);
    }
    public static void removeWall(int index){
        gameObjects.remove(index);
    }
}

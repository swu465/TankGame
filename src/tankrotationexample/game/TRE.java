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
	static long frameCount = 0;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
				gameObjects.forEach(gameObjects -> gameObjects.update());


                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                //System.out.println(t1);
				frameCount++;
                CollisionDetection.checkBulletsOne(t1,t2,gameObjects);
                CollisionDetection.checkBulletsTwo(t1,t2,gameObjects);
                CollisionDetection.checkPlayers(t1,t2,gameObjects);
                //if one tank is out of lives, the game ends.
               if(t1.getState() == 0 || t2.getState() == 0){
                   this.lf.setFrame("end");
                   return;
               }

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
        frameCount = 0;

		this.t1.reset();
		this.t2.reset();
		this.gameInitialize();
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        gameObjects = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("map3.txt"));
            BufferedReader mapReader = new BufferedReader(isr);
            //0 = nothing
            //2 = breakable wall
            // 3 = unbreakable wall
            //4-7 = powerups
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
                            //System.out.println("Breakable wall");
                            TRE.gameObjects.add(brWall);
                            break;
                        case "3":
                        case "9":
                            UnbreakableWall ubrWall = new UnbreakableWall(curCol*30,curRow*30,GameResource.get("unbreakableWall"));
                            TRE.gameObjects.add(ubrWall);
                            //System.out.println("unbreakable wall");
                            break;
                        case "4":
                            rocketPowerUp r = new rocketPowerUp(curCol*30,curRow*30,GameResource.get("rocketPwr"));
                            TRE.gameObjects.add(r);
                            break;
                        case "5":
                            HPPowerUp h = new HPPowerUp(curCol*30,curRow*30,GameResource.get("heal"));
                            TRE.gameObjects.add(h);
                            break;
                        case "6":
                            speedPowerUp s = new speedPowerUp(curCol*30,curRow*30,GameResource.get("speed"));
                            TRE.gameObjects.add(s);
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
         t2 = new Tank(1500,1650,0,0,180,GameResource.get("tankTwo"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
		TRE.gameObjects.add(t1);
		TRE.gameObjects.add(t2);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        //buffer.setColor(Color.BLACK);
        buffer.drawImage(GameResource.get("background"),0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT,null);
        //buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        //buffer.drawImage(GameResource.get("background"),0,0,null);
        TRE.gameObjects.forEach(wall->wall.drawImage(buffer));
        //this.t1.drawImage(buffer);
		//this.t2.drawImage(buffer);

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
		g2.drawImage(miniMap,3300,0,null);
		//g2.scale();
        //g2.drawImage(world,0,0,null);
    }
    public static void removeObj(int index){
        TRE.gameObjects.remove(index);
    }
    public static void damageWall(int index) {
        BreakableWall wall= (BreakableWall) TRE.gameObjects.get(index);
        wall.damaged();
        TRE.gameObjects.add(index,wall);
    }
}

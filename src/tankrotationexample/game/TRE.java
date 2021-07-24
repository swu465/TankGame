/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


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
    ArrayList<Wall> walls;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.t1.update(); // update tank
				this.t2.update();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                System.out.println(t1);
				/*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(this.tick > 2000){
                    this.lf.setFrame("end");
                    return;
                }
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
        walls = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank2.png")));
            godWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("unbreakablewall.png")));
            normalWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("breakablewall.png")));
            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1.png"));
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
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++){
                    switch (mapInfo[curCol]){
                        case "2":
                            BreakableWall brWall = new BreakableWall(curCol*30,curRow*30,normalWall);
                            this.walls.add(brWall);
                            break;
                        case "3":
                        case "9":
                            UnbreakableWall ubrWall = new UnbreakableWall(curCol*30,curRow*30,godWall);
                            this.walls.add(ubrWall);
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(300, 300, 0, 0, 0, t1img);
        t2 = new Tank(200,200,0,0,0,t2img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        this.walls.forEach(wall->wall.drawWall(buffer));
        this.t1.drawImage(buffer);
		this.t2.drawImage(buffer);
		BufferedImage leftHalf = world.getSubimage(0,0,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
		//x = tank.getX(),y=tank.getY()
        //do not follow this exactly. was from video. will need something like this
		BufferedImage rightHalf = world.getSubimage(900,1000,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
		BufferedImage miniMap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_WIDTH);
		g2.drawImage(leftHalf,0,0,null);
		g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2+4,0,null);
		g2.scale(.15,.15);
		//find place to put minimap
		g2.drawImage(miniMap,200,200,null);
        g2.drawImage(world,0,0,null);
    }

}

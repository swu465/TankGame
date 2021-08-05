package tankrotationexample.game;

import javax.sound.midi.SysexMessage;
import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
//maybe just make a method in the TRE. since the arraylist of walls is there. tanks are there.
public class CollisionDetection {
        public CollisionDetection(){

        }
        public static void checkPlayers(Tank t1, Tank t2, ArrayList<GameObject> objList){
                //System.out.println("player checking");
                if(t1.getHitBox().intersects(t2.getHitBox())){
                        System.out.println("tank collision");
                        t1.collisionHappened();
                        t2.collisionHappened();
                }
                for(int x = 0; x < objList.size(); x++) {
                        if (objList.get(x) instanceof Wall) {
                                //System.out.println("wall is found. ArrayList is :"+objList.size());
                                //System.out.println(t1.getHitBox().intersects(objList.get(x).getHitBox()));
                                //System.out.println(objList.get(x).getHitBox().getHeight());
                                if (t1.getHitBox().intersects(objList.get(x).getHitBox()) ||
                                objList.get(x).getHitBox().intersects(t1.getHitBox())) {
                                        System.out.println("in a wall");
                                        t1.collisionHappened();
                                }else if(t2.getHitBox().intersects(objList.get(x).getHitBox())){
                                        t2.collisionHappened();
                                }
                        }
                }

        }
        public static void checkBulletsOne(Tank t1, Tank t2, ArrayList<GameObject> gameObjects){
                GameObject b;
                //gethitbox isn't working. idk why.
                //System.out.println("bullet checking");
                try{
                        for(int x = 0; x < t1.ammoDrumSize(); x++){
                                for(int y = 0; y < gameObjects.size(); y++){
                                        b= t1.ammoDrumGetBullet(x);
                                        if(b.getHitBox().intersects(t2.getHitBox())){
                                                t2.damaged(b);
                                                t1.ammoDrumRemoveBullet(x);
                                        }else if(gameObjects.get(y) instanceof UnbreakableWall godWall&&
                                                ( godWall.getHitBox().intersects(b.getHitBox()) ||
                                                        b.getHitBox().intersects(godWall.getHitBox()) ) ){
                                                t1.ammoDrumRemoveBullet(x);
                                        }else if(gameObjects.get(y) instanceof BreakableWall targetWall &&
                                                ( targetWall.getHitBox().intersects(b.getHitBox()) ||
                                                        b.getHitBox().intersects(targetWall.getHitBox()) ) ){
                                                //System.out.println("i hit a breakble wall");
                                                if(b instanceof Rocket){
                                                        TRE.removeWall(y);
                                                        t1.ammoDrumRemoveBullet(x);
                                                }else if(b instanceof Bullet){
                                                        t1.ammoDrumRemoveBullet(x);
                                                        TRE.damageWall(y);
                                                        if(targetWall.getState() == 0){
                                                                TRE.removeWall(y);
                                                        }
                                                }
                                        }
                                }
                        }
                }catch(Exception e){
                        System.out.println(e);
                }

        }

        public static void checkBulletsTwo(Tank t1, Tank t2,ArrayList<GameObject> gameObjects){
                GameObject b;
                //System.out.println("bullet checking");
                try{
                        for(int x = 0; x < t2.ammoDrumSize(); x++){
                                for(int y = 0; y < gameObjects.size(); y++){
                                        //nested for loop. outer loop for the ammodrum
                                        //inner for loop for the size of the arraylist
                                        //System.out.println(gameObjects.size());
                                        //check for bullet or rocket. then assign
                                        b= t2.ammoDrumGetBullet(x);
                                        if(b.getHitBox().intersects(t1.getHitBox())){
                                                t1.damaged(b);
                                                t2.ammoDrumRemoveBullet(x);
                                        }else if(gameObjects.get(y) instanceof UnbreakableWall godWall&&
                                                ( godWall.getHitBox().intersects(b.getHitBox()) ||
                                                        b.getHitBox().intersects(godWall.getHitBox()) ) ){
                                                t2.ammoDrumRemoveBullet(x);

                                        }else if(gameObjects.get(y) instanceof BreakableWall targetWall &&
                                                ( targetWall.getHitBox().intersects(b.getHitBox()) ||
                                                   b.getHitBox().intersects(targetWall.getHitBox()) ) ){
                                                //System.out.println("i hit a breakble wall");
                                                if(b instanceof Rocket){
                                                        t2.ammoDrumRemoveBullet(x);
                                                        TRE.removeWall(y);
                                                }else if(b instanceof Bullet){
                                                        t2.ammoDrumRemoveBullet(x);
                                                        TRE.damageWall(y);
                                                        if(targetWall.getState() == 0){
                                                                TRE.removeWall(y);
                                                        }
                                                }
                                        }
                                }
                        }
                }catch(Exception e){
                        //System.out.println(e+"out of bullets?");
                }

        }

}

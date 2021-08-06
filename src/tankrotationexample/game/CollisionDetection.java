package tankrotationexample.game;

import javax.sound.midi.SysexMessage;
import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

public class CollisionDetection {
        public static void checkPlayers(Tank t1, Tank t2, ArrayList<GameObject> objList){
                //System.out.println("player checking");
                if(t1.getHitBox().intersects(t2.getHitBox())){
                        //System.out.println("tank collision");
                        t1.collisionHappened();
                        t2.collisionHappened();
                }
                //checks given arraylist for player collision into walls and power ups.
                for(int x = 0; x < objList.size(); x++) {
                        if (objList.get(x) instanceof Wall) {
                                if (t1.getHitBox().intersects(objList.get(x).getHitBox()) ||
                                objList.get(x).getHitBox().intersects(t1.getHitBox())) {
                                        //System.out.println("in a wall");
                                        t1.collisionHappened();
                                }else if(t2.getHitBox().intersects(objList.get(x).getHitBox())){
                                        t2.collisionHappened();
                                }
                        }else if(objList.get(x) instanceof PowerUp item){
                                //removes power up from the game after picked up.
                                if(t1.getHitBox().intersects(item.getHitBox())){
                                        t1.setPowerUp(item);
                                        item.pickedUp();
                                        TRE.removeObj(x);
                                }else if(t2.getHitBox().intersects(item.getHitBox())){
                                        t2.setPowerUp(item);
                                        item.pickedUp();
                                        TRE.removeObj(x);
                                }
                        }
                }
        }
        public static void checkBulletsOne(Tank t1, Tank t2, ArrayList<GameObject> gameObjects){
                GameObject b;
                try{
                        //nested for loop. Outer loop for bullets
                        //inner loop for objs in the Arraylist
                        //try catch for times when only 1 bullet exists and it disppears, creating 0 out of 0 exception
                        for(int x = 0; x < t1.ammoDrumSize(); x++){
                                for(int y = 0; y < gameObjects.size(); y++){
                                        b= t1.ammoDrumGetBullet(x);
                                        if(b.getHitBox().intersects(t2.getHitBox())){
                                                t2.damaged(b);
                                                t1.ammoDrumRemoveBullet(x);
                                        }else if(gameObjects.get(y) instanceof UnbreakableWall godWall&&
                                                ( godWall.getHitBox().intersects(b.getHitBox()) ||
                                                        b.getHitBox().intersects(godWall.getHitBox()) ) ){
                                                //doesn't matter what hits the unbreakable wall. it will disappear
                                                t1.ammoDrumRemoveBullet(x);
                                        }else if(gameObjects.get(y) instanceof BreakableWall targetWall &&
                                                ( targetWall.getHitBox().intersects(b.getHitBox()) ||
                                                        b.getHitBox().intersects(targetWall.getHitBox()) ) ){
                                                if(b instanceof Rocket){
                                                        TRE.removeObj(y);
                                                        t1.ammoDrumRemoveBullet(x);
                                                }else if(b instanceof Bullet){
                                                        t1.ammoDrumRemoveBullet(x);
                                                        TRE.damageWall(y);
                                                        if(targetWall.getState() == 0){
                                                                TRE.removeObj(y);
                                                        }
                                                }
                                        }
                                }
                        }
                }catch(Exception e){
                        //System.out.println(e);
                }

        }

        public static void checkBulletsTwo(Tank t1, Tank t2,ArrayList<GameObject> gameObjects){
                GameObject b;
                try{
                        for(int x = 0; x < t2.ammoDrumSize(); x++){
                                for(int y = 0; y < gameObjects.size(); y++){
                                        //nested for loop. outer loop for the ammodrum
                                        //inner for loop for the size of the arraylist
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
                                                if(b instanceof Rocket){
                                                        t2.ammoDrumRemoveBullet(x);
                                                        TRE.removeObj(y);
                                                }else if(b instanceof Bullet){
                                                        t2.ammoDrumRemoveBullet(x);
                                                        TRE.damageWall(y);
                                                        if(targetWall.getState() == 0){
                                                                TRE.removeObj(y);
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

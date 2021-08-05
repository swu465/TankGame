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
                Bullet b;
                //gethitbox isn't working. idk why.
                //System.out.println("bullet checking");
                for(int x = 0; x < t1.ammoDrumSize(); x++){
                        b= t1.ammoDrumGetBullet(x);
                        if(b.hitBox.intersects(t2.getHitBox())){
                                t2.damaged(b);
                                t1.ammoDrumRemoveBullet(x);
                        }else if(gameObjects.get(x) instanceof Wall targetWall){
                                //System.out.println(targetWall.getHitBox().intersects(b.getHitBox()));
                                //System.out.println(b.hitBox.intersects(targetWall.getHitBox()));
                                //System.out.println(targetWall.getHP());
                                if(targetWall.getHP() == 9){
                                        //System.out.println("godwall");
                                }else if(targetWall.getHP() == 2){
                                        System.out.println("a");
                                        TRE.damageWall(x);
                                        targetWall.damaged();
                                }
                                if(targetWall.getHP() == 0){
                                        TRE.removeWall(x);
                                }
                        }
                }
        }
        public static void checkBulletsTwo(Tank t1, Tank t2,ArrayList<GameObject> gameObjects){
                Bullet b;
                //walls problem. bullets still work with new gethitbox
                //System.out.println("bullet checking");
                try{
                        for(int x = 0; x < t2.ammoDrumSize(); x++){
                                for(int y = 0; y < gameObjects.size(); y++){
                                        //nested for loop. outer loop for the ammodrum
                                        //inner for loop for the size of the arraylist
                                        //System.out.println(gameObjects.size());
                                        b= t2.ammoDrumGetBullet(x);
                                        if(b.getHitBox().intersects(t1.getHitBox())){
                                                t1.damaged(b);
                                                t2.ammoDrumRemoveBullet(x);
                                        }else if(gameObjects.get(y) instanceof Wall targetWall){
                                                if( b.getHitBox().intersects(targetWall.getHitBox()) ||
                                                        targetWall.getHitBox().intersects(b.getHitBox()) &&
                                                                targetWall.getHP() == 9){
                                                        //System.out.println("found wall");
                                                        t2.ammoDrumRemoveBullet(x);
                                                }else if(b.getHitBox().intersects(targetWall.getHitBox()) &&
                                                                targetWall.getHP() == 2){
                                                        TRE.damageWall(y);
                                                        targetWall.damaged();

                                                }
                                        }
                                }

                        }
                }catch(Exception e){
                        System.out.println(e+"out of bullets?");
                }

        }

}

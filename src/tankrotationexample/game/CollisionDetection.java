package tankrotationexample.game;

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
                                }
                        }
                }

        }
        public static void checkBulletsOne(Tank t1, Tank t2, ArrayList<GameObject> gameObjects){
                Bullet b;
                //System.out.println("bullet checking");
                for(int x = 0; x < t1.ammoDrumSize(); x++){
                        b= t1.ammoDrumGetBullet(x);
                        if(b.hitBox.intersects(t2.getHitBox())){
                                t2.damaged();
                                t1.ammoDrumRemoveBullet(x);
                        }else if(b.hitBox.intersects(gameObjects.get(x).getHitBox()) && gameObjects.get(x) instanceof BreakableWall checkWall){
                                if(checkWall.getHP() > 0){
                                        checkWall.damaged();
                                        TRE.damageWall(x);
                                }
                                if(checkWall.getHP() == 0){
                                        TRE.removeWall(x);
                                }
                        }else if(b.hitBox.intersects(gameObjects.get(x).getHitBox()) ||
                                gameObjects.get(x).getHitBox().intersects(b.getHitBox())){//should be unbreakable walll here
                                System.out.println("godwall");
                                t1.ammoDrumRemoveBullet(x);
                        }
                }
        }
        public static void checkBulletsTwo(Tank t1, Tank t2,ArrayList<GameObject> gameObjects){
                Bullet b;
                //System.out.println("bullet checking");
                for(int x = 0; x < t2.ammoDrumSize(); x++){
                        b= t2.ammoDrumGetBullet(x);
                        if(b.hitBox.intersects(t1.getHitBox())){
                                t1.damaged();
                                t2.ammoDrumRemoveBullet(x);
                        }else if(b.hitBox.intersects(gameObjects.get(x).getHitBox()) && gameObjects.get(x) instanceof BreakableWall checkWall){
                                if(checkWall.getHP() > 0){
                                        checkWall.damaged();
                                }
                                if(checkWall.getHP() == 0){
                                        gameObjects.remove(x);
                                }
                        }else if(gameObjects.get(x) instanceof UnbreakableWall godWall &&
                                b.hitBox.intersects(gameObjects.get(x).getHitBox()) ||
                                gameObjects.get(x).getHitBox().intersects(b.getHitBox())){//should be unbreakable walll here
                                System.out.println("godwall");
                                t2.ammoDrumRemoveBullet(x);
                        }
                }
        }

}

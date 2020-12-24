package main.java.nju.linhao.bullet;

import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.enums.Player;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletManager {
    private CopyOnWriteArrayList<HumanBullet> humanBullets;
    private CopyOnWriteArrayList<MonsterBullet> monsterBullets;

    public BulletManager(){
        humanBullets = new CopyOnWriteArrayList<>();
        monsterBullets = new CopyOnWriteArrayList<>();
    }

    public void addBullet(HumanBullet humanBullet){
        humanBullets.add(humanBullet);
    }

    public void addBullet(MonsterBullet monsterBullet){
        monsterBullets.add(monsterBullet);
    }

    public void removeBullet(int id){
        Iterator<HumanBullet> iter = humanBullets.iterator();
        while( iter.hasNext()){
            Bullet bullet = iter.next();
            if(bullet.getId()== id){
                iter.remove();
            }
        }
    }

    public void clearBullet(){
        humanBullets.clear();
        monsterBullets.clear();
    }
}

package main.java.nju.linhao.bullet;

import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletManager {
    private ArrayList<HumanBullet> humanBullets;
    private ArrayList<MonsterBullet> monsterBullets;

    public BulletManager(){
        humanBullets = new ArrayList<>();
        monsterBullets = new ArrayList<>();
    }

    public void addBullet(Bullet bullet){
        if (bullet instanceof HumanBullet){
            humanBullets.add((HumanBullet) bullet);
        } else if(bullet instanceof MonsterBullet){
            monsterBullets.add((MonsterBullet) bullet);
        } else {
            System.err.println("子弹不属于人类或妖怪！");
        }
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

    public ArrayList<HumanBullet> getHumanBullets(){
        return humanBullets;
    }

    public ArrayList<MonsterBullet> getMonsterBullets(){
        return monsterBullets;
    }
}

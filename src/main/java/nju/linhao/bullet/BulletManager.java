package main.java.nju.linhao.bullet;

import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletManager implements Runnable {
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
        Iterator<HumanBullet> humanBulletIterator = humanBullets.iterator();
        while(humanBulletIterator.hasNext()){
            Bullet bullet = humanBulletIterator.next();
            if(bullet.getId()== id){
                humanBulletIterator.remove();
                return;
            }
        }
        Iterator<MonsterBullet> monsterBulletIterator = monsterBullets.iterator();
        while(monsterBulletIterator.hasNext()){
            Bullet bullet = monsterBulletIterator.next();
            if(bullet.getId()== id){
                monsterBulletIterator.remove();
                return;
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

    @Override
    public void run() {

    }
}

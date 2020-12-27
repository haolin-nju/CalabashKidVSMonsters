package main.java.nju.linhao.bullet;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletManager implements Runnable {
    private CopyOnWriteArrayList<HumanBullet> humanBullets;
    private CopyOnWriteArrayList<MonsterBullet> monsterBullets;

    public BulletManager(){
        humanBullets = new CopyOnWriteArrayList<>();
        monsterBullets = new CopyOnWriteArrayList<>();
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

    public void removeBullets(){
        for(HumanBullet humanBullet : humanBullets){
            if(humanBullet.getToDestroy() == true){
                humanBullets.remove(humanBullet);
            }
        }
        for(MonsterBullet monsterBullet : monsterBullets){
            if(monsterBullet.getToDestroy() == true){
                humanBullets.remove(monsterBullet);
            }
        }
        return;
    }

    public void clearBullets(){
        humanBullets.clear();
        monsterBullets.clear();
    }

    public CopyOnWriteArrayList<HumanBullet> getHumanBullets(){
        return humanBullets;
    }

    public CopyOnWriteArrayList<MonsterBullet> getMonsterBullets(){
        return monsterBullets;
    }

    @Override
    public void run() {
        while(true){
            for(HumanBullet humanBullet : humanBullets){
                double[] humanBulletPos = humanBullet.modifyPos();
                Creature creature = Battlefield.getCreatureFromBulletPos(humanBulletPos[0], humanBulletPos[1]);
                if(creature instanceof Monster){
                    creature.injured(humanBullet.getDamage());
                    humanBullet.setToDestroy(true); // 打到敌人，可以消亡
                }
            }
            for(MonsterBullet monsterBullet : monsterBullets){
                double[] monsterBulletPos = monsterBullet.modifyPos();
                Creature creature = Battlefield.getCreatureFromBulletPos(monsterBulletPos[0], monsterBulletPos[1]);
                if(creature instanceof Human){
                    creature.injured(monsterBullet.getDamage());
                    monsterBullet.setToDestroy(true);// 打到敌人，可以消亡
                }
            }
            removeBullets();
            LocalGameController.getInstance().getBattlefieldController().repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

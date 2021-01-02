package main.java.nju.linhao.bullet;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.utils.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class BulletManager implements Runnable {
    protected LinkedList<HumanBullet> humanBullets;
    protected LinkedList<MonsterBullet> monsterBullets;
    private Player localPlayer;

    public BulletManager() {
        humanBullets = new LinkedList<>();
        monsterBullets = new LinkedList<>();

        localPlayer = Player.PLAYER_1;
    }

    public void addBullet(Bullet bullet) {
        if (bullet instanceof HumanBullet) {
            synchronized (humanBullets) {
                humanBullets.add((HumanBullet) bullet);
            }
        } else if (bullet instanceof MonsterBullet) {
            synchronized (monsterBullets){
                monsterBullets.add((MonsterBullet) bullet);
            }
        } else {
            System.err.println("子弹不属于人类或妖怪！");
        }
    }

    public void removeBullets() {
        synchronized (humanBullets){
            Iterator<HumanBullet> humanBulletIterator = humanBullets.iterator();
            while(humanBulletIterator.hasNext()){
                if(humanBulletIterator.next().getToDestroy() == true){
                    humanBulletIterator.remove();
                }
            }
        }
        synchronized (monsterBullets){
            Iterator<MonsterBullet> monsterBulletIterator = monsterBullets.iterator();
            while(monsterBulletIterator.hasNext()){
                if(monsterBulletIterator.next().getToDestroy() == true){
                    monsterBulletIterator.remove();
                }
            }
        }
        return;
    }

    public void removeBullet(Bullet bullet){//只可能为了对方删除对方的bullet
        if(localPlayer == Player.PLAYER_1){
            synchronized (humanBullets){
                Iterator<HumanBullet> humanBulletIterator = humanBullets.iterator();
                while(humanBulletIterator.hasNext()){
                    if(humanBulletIterator.next() == bullet){
                        humanBulletIterator.remove();
                        return;
                    }
                }
            }
        } else if(localPlayer == Player.PLAYER_2){
            synchronized (monsterBullets){
                Iterator<MonsterBullet> monsterBulletIterator = monsterBullets.iterator();
                while(monsterBulletIterator.hasNext()){
                    if(monsterBulletIterator.next() == bullet){
                        monsterBulletIterator.remove();
                        return;
                    }
                }
            }
        }
    }

    public void clearBullets() {
        humanBullets.clear();
        monsterBullets.clear();
    }

    public synchronized LinkedList<HumanBullet> getHumanBullets() {
        return humanBullets;
    }

    public synchronized LinkedList<MonsterBullet> getMonsterBullets() {
        return monsterBullets;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (humanBullets) {
                for (HumanBullet humanBullet : humanBullets) {
                    if (humanBullet.modifyPos()) {
                        continue;
                    }
                    double[] humanBulletPos = humanBullet.getPos();
                    Creature creature;
                    try {
                        creature = Battlefield.getCreatureFromBulletPos(humanBulletPos[0], humanBulletPos[1]);
                        if (creature instanceof Monster
                                && creature.getCreatureStatus() == CreatureStatus.ALIVE) {
                            if (localPlayer == Player.PLAYER_1) {// 本地是人类阵营，需要计算所有打到妖怪的内容
                                creature.injured(humanBullet.getDamage());
                                LocalGameController.getInstance().requestSendCreatureRemainHealth(creature);
                            }
                            humanBullet.setToDestroy(true); // 打到敌人，可以消亡
                            LocalGameController.getInstance().requestSendBulletDestroy(humanBullet);
                        }
                    } catch (OutofRangeException e) {

                    }
                }
            }
            synchronized (monsterBullets) {
                for (MonsterBullet monsterBullet : monsterBullets) {
                    if (monsterBullet.modifyPos()) {
                        continue;
                    }
                    double[] monsterBulletPos = monsterBullet.getPos();
                    Creature creature;
                    try {
                        creature = Battlefield.getCreatureFromBulletPos(monsterBulletPos[0], monsterBulletPos[1]);
                        if (creature instanceof Human
                                && creature.getCreatureStatus() == CreatureStatus.ALIVE) {
                            if (localPlayer == Player.PLAYER_2) {// 本地是妖怪阵营，需要计算所有打到人类的内容
                                creature.injured(monsterBullet.getDamage());
                                LocalGameController.getInstance().requestSendCreatureRemainHealth(creature);
                            }
                            monsterBullet.setToDestroy(true);// 打到敌人，可以消亡
                            LocalGameController.getInstance().requestSendBulletDestroy(monsterBullet);
                        }
                    } catch (OutofRangeException e) {

                    }
                }
            }
            removeBullets();
            LocalGameController.getInstance().getBattlefieldController().repaint();
            try {
                Thread.sleep(Configuration.BULLET_DEFAULT_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLocalPlayer(Player player) {
        localPlayer = player;
    }
}

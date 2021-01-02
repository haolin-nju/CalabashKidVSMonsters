package main.java.nju.linhao.bullet;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.utils.Configuration;
import main.java.nju.linhao.utils.Displayer;

import java.util.Iterator;
import java.util.LinkedList;

public class BulletDisplayer extends BulletManager implements Runnable {
    private LocalGameStatus localGameStatus;

    public void setLocalGameStatus(LocalGameStatus localGameStatus){
        this.localGameStatus = localGameStatus;
    }

    public void removeBulletFromDisplayer(Bullet bullet) {
        synchronized (humanBullets){
            Iterator<HumanBullet> humanBulletIterator = humanBullets.iterator();
            while(humanBulletIterator.hasNext()){
                if(humanBulletIterator.next() == bullet){
                    humanBulletIterator.remove();
                    return;
                }
            }
        }
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
                        creature = Displayer
                                .getInstance()
                                .getBattlefieldController()
                                .getBattlefield()
                                .getCreatureFromBulletPos(humanBulletPos[0], humanBulletPos[1]);
                        if (creature instanceof Monster
                                && creature.getCreatureStatus() == CreatureStatus.ALIVE) {
                            humanBullet.setToDestroy(true); // 打到敌人，可以消亡
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
                        creature = Displayer
                                .getInstance()
                                .getBattlefieldController()
                                .getBattlefield()
                                .getCreatureFromBulletPos(monsterBulletPos[0], monsterBulletPos[1]);
                        if (creature instanceof Human
                                && creature.getCreatureStatus() == CreatureStatus.ALIVE) {
                            monsterBullet.setToDestroy(true);// 打到敌人，可以消亡
                        }
                    } catch (OutofRangeException e) {

                    }
                }
            }
            removeBullets();
            Displayer.getInstance().getBattlefieldController().repaint(localGameStatus);
            try {
                Thread.sleep(Configuration.BULLET_DEFAULT_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package main.java.nju.linhao.bullet;

import main.java.nju.linhao.utils.RadianCalculater;

public class BulletFactory {
    private RadianCalculater radianCalculater = new RadianCalculater();

    private BulletFactory(){}

    public static HumanBullet createHumanBullet(double clickPosX,
                                                double clickPosY,
                                                double posX,
                                                double posY){
        return new HumanBullet(
                RadianCalculater.calculateRadian(clickPosX, clickPosY, posX, posY),
                posX,
                posY);
    }

    public static HumanBullet createHumanBullet(double damage,
                                                double speed,
                                                double clickPosX,
                                                double clickPosY,
                                                double posX,
                                                double posY){
        return new HumanBullet(
                damage,
                speed,
                RadianCalculater.calculateRadian(clickPosX, clickPosY, posX, posY),
                posX,
                posY);
    }

    public static MonsterBullet createMonsterBullet(double clickPosX,
                                                    double clickPosY,
                                                    double posX,
                                                    double posY){
        return new MonsterBullet(
                RadianCalculater.calculateRadian(clickPosX, clickPosY, posX, posY),
                posX,
                posY);
    }

    public static MonsterBullet createMonsterBullet(double damage,
                                                    double speed,
                                                    double clickPosX,
                                                    double clickPosY,
                                                    double posX,
                                                    double posY){
        return new MonsterBullet(
                damage,
                speed,
                RadianCalculater.calculateRadian(clickPosX, clickPosY, posX, posY),
                posX,
                posY);
    }
}

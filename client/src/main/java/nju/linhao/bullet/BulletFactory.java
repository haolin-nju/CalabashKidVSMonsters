package main.java.nju.linhao.bullet;

public class BulletFactory {
    private BulletFactory(){}

    public static HumanBullet createHumanBullet(double angle,
                                                double posX,
                                                double posY){
        return new HumanBullet(
                angle,
                posX,
                posY);
    }

    public static HumanBullet createHumanBullet(double damage,
                                                double speed,
                                                double angle,
                                                double posX,
                                                double posY){
        return new HumanBullet(
                damage,
                speed,
                angle,
                posX,
                posY);
    }

    public static MonsterBullet createMonsterBullet(double angle,
                                                    double posX,
                                                    double posY){
        return new MonsterBullet(
                angle,
                posX,
                posY);
    }

    public static MonsterBullet createMonsterBullet(double damage,
                                                double speed,
                                                double angle,
                                                double posX,
                                                double posY){
        return new MonsterBullet(
                damage,
                speed,
                angle,
                posX,
                posY);
    }
}

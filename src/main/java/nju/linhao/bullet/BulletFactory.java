package main.java.nju.linhao.bullet;

public class BulletFactory {
    private BulletFactory(){}

    public static HumanBullet createHumanBullet(double clickPosX,
                                                double clickPosY,
                                                double posX,
                                                double posY){
        return new HumanBullet(
                calculateRadian(clickPosX, clickPosY, posX, posY),
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
                calculateRadian(clickPosX, clickPosY, posX, posY),
                posX,
                posY);
    }

    public static MonsterBullet createMonsterBullet(double clickPosX,
                                                    double clickPosY,
                                                    double posX,
                                                    double posY){
        return new MonsterBullet(
                calculateRadian(clickPosX, clickPosY, posX, posY),
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
                calculateRadian(clickPosX, clickPosY, posX, posY),
                posX,
                posY);
    }

    private final static double calculateRadian(double clickPosX, double clickPosY, double posX, double posY){
        double dx = clickPosX - posX;
        double dy = clickPosY - posY;
        double radian;
        if (dx > 0) {
            radian = Math.atan(dy / dx);
        } else if (dx < 0) {
            radian = Math.PI - Math.atan(dy / -dx);
        } else {
            radian = dy > 0 ? Math.toRadians(90.0) : Math.toRadians(-90.0);
            if (dy == 0)
                radian = 0;
        }
        return radian;
    }
}

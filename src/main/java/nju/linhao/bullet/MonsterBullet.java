package main.java.nju.linhao.bullet;

public class MonsterBullet extends Bullet {
    public MonsterBullet(double radian, double posX, double posY){
        super(radian, posX, posY);
    }

    public MonsterBullet(double damage,
                       double speed,
                       double radian,
                       double posX,
                       double posY){
        super(damage, speed, radian, posX, posY);
    }
}

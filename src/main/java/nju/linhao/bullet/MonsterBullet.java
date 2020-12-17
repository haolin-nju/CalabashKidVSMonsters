package main.java.nju.linhao.bullet;

public class MonsterBullet extends Bullet {
    public MonsterBullet(double angle, double posX, double posY){
        super(angle, posX, posY);
    }

    public MonsterBullet(double damage,
                       double speed,
                       double angle,
                       double posX,
                       double posY){
        super(damage, speed, angle, posX, posY);
    }
}

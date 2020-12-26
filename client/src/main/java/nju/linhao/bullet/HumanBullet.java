package main.java.nju.linhao.bullet;

public class HumanBullet extends Bullet {
    public HumanBullet(double angle, double posX, double posY){
        super(angle, posX, posY);
    }

    public HumanBullet(double damage,
                       double speed,
                       double angle,
                       double posX,
                       double posY){
        super(damage, speed, angle, posX, posY);
    }
}

package main.java.nju.linhao.bullet;

public class HumanBullet extends Bullet {
    public HumanBullet(double radian, double posX, double posY){
        super(radian, posX, posY);
    }

    public HumanBullet(double damage,
                       double speed,
                       double radian,
                       double posX,
                       double posY){
        super(damage, speed, radian, posX, posY);
    }
}

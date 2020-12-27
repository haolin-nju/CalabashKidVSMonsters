package main.java.nju.linhao.bullet;

import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.utils.Configuration;
import sun.security.krb5.Config;

public class Bullet {
    public Bullet(double angle, double posX, double posY) {
        this(Configuration.DEFAULT_BULLET_DAMAGE,
                Configuration.DEFAULT_BULLET_SPEED,
                angle,
                posX,
                posY);
    }

    public Bullet(double damage,
                  double speed,
                  double angle,
                  double posX,
                  double posY) {
        this.id++;

        this.damage = damage;
        this.speed = speed;
        this.setAngle(angle);
        this.posX = posX;//行像素
        this.posY = posY;//列像素
        this.toDestroy = false; //生成时还不需要消亡
    }

    public int getId() {
        return id;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }

    public double[] getPos() {
        return new double[]{posX, posY};
    }

    public boolean getToDestroy() {
        return toDestroy;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.deno = 1 / Math.sqrt(1 + angle);
        ;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }

    public boolean isOutOfRange() {
        if (posX < 0 || posX >= Configuration.CANVAS_WIDTH || posY < 0 || posY >= Configuration.CANVAS_HEIGHT) {
            return true;
        }
        return false;
    }

    public boolean modifyPos() {
        posX = posX + speed * angle * deno;
        posY = posY + speed * deno;
        if (isOutOfRange()) {
            toDestroy = true; //可以消亡了
            return true;
        }
        return false;
    }

    private static int id = 0; // 全局唯一bullet id
    private double damage;
    private double speed;
    private double angle;
    private double deno;
    private double posX;
    private double posY;

    private boolean toDestroy;// 子弹需要消亡的标记
}

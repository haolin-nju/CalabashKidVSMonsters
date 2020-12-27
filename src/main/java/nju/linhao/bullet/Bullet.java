package main.java.nju.linhao.bullet;

import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.utils.Configuration;
import sun.security.krb5.Config;

public class Bullet implements Runnable {
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

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.deno = 1 / Math.sqrt(1 + angle);;
    }

    public boolean isOutOfRange() {
        if (posX < 0 || posX >= Configuration.CANVAS_HEIGHT || posY < 0 || posY >= Configuration.CANVAS_WIDTH) {
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

    @Override
    public void run() {
        while (true) {
            if (isOutOfRange()) {
                return;
            }
            posX = posX + speed * angle * deno;
            posY = posY + speed * deno;
        }
    }
}

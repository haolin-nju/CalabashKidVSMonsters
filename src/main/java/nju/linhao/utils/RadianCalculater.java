package main.java.nju.linhao.utils;

public class RadianCalculater {
    public static final double calculateRadian(double clickPosX, double clickPosY, double posX, double posY){
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

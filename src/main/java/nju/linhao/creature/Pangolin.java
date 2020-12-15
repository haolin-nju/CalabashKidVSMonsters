package main.java.nju.linhao.creature;

public class Pangolin extends Human {
    public Pangolin(){
        super("穿山甲");
    }

    public Pangolin(double health,
                   double damage,
                   double defense,
                   double speed){
        super("穿山甲", health, damage, defense, speed);
    }
}

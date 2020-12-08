package main.java.nju.linhao.creature;

public class CalabashKid extends Creature {
    public CalabashKid(String name){
        super(name);
    }

    public CalabashKid(String name,
                       double health,
                       double damage,
                       double defense,
                       double speed){
        super(name, health, damage, defense, speed);
    }
}

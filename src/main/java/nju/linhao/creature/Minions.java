package main.java.nju.linhao.creature;

public class Minions extends Creature {
    public Minions(String name){
        super(name);
    }

    public Minions(String name,
                       double health,
                       double damage,
                       double defense,
                       double speed){
        super(name, health, damage, defense, speed);
    }
}

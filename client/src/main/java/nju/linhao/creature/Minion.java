package main.java.nju.linhao.creature;

public class Minion extends Monster {
    public Minion(String name){
        super(name);
    }

    public Minion(String name,
                  double health,
                  double damage,
                  double defense,
                  double speed){
        super(name, health, damage, defense, speed);
    }
}

package main.java.nju.linhao.creature;

public class Human extends Creature{

    public Human(String name) {
        super(name);
    }

    public Human(String name,
                 double health,
                 double damage,
                 double defense,
                 double speed){
        super(name,
                health,
                damage,
                defense,
                speed);
    }
}

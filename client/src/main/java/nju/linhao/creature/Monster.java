package main.java.nju.linhao.creature;

public class Monster extends Creature {

    public Monster(String name) {
        super(name);
    }

    public Monster(String name,
                   double health,
                   double damage,
                   double defense,
                   double speed) {
        super(name,
                health,
                damage,
                defense,
                speed);
    }
}

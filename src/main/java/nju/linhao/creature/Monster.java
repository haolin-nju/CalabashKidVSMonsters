package main.java.nju.linhao.creature;

import main.java.nju.linhao.enums.Player;

public class Monster extends Creature {

    public Monster(String name) {
        super(name, Player.PLAYER_2);
    }

    public Monster(String name,
                   double health,
                   double damage,
                   double defense,
                   double speed) {
        super(name,
                Player.PLAYER_2,
                health,
                damage,
                defense,
                speed);
    }
}

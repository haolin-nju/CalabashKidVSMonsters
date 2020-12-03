package main.java.nju.linhao.creature;

import main.java.nju.linhao.enums.Direction;

public abstract class Creature {
    boolean alive = false;
    int id = 0;
    double life = 0;
    double attack = 0;
    double defense = 0;

    Direction direction = Direction.UP;
    double speed = 0;
    int posX = 0;
    int posY = 0;
}

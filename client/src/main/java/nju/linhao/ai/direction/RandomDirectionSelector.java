package main.java.nju.linhao.ai.direction;

import main.java.nju.linhao.enums.Direction;
import main.java.nju.linhao.enums.Enums;

import java.util.Random;

public class RandomDirectionSelector implements DirectionSelector {
    @Override
    public Direction selectDirection() {
        return Enums.random(Direction.class);
    }
}

package main.java.nju.linhao.enums;

public enum Direction {
    NO_DIRECTION("NO_DIRECTION"),
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private String description;

    Direction(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

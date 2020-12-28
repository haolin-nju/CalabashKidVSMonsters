package main.java.nju.linhao.enums;

public enum Player {
    PLAYER_1("人类阵营"), PLAYER_2("妖怪阵营");

    private String description;

    Player(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

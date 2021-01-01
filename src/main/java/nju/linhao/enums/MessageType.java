package main.java.nju.linhao.enums;

public enum MessageType {
    CLIENT_READY("CLIENT_READY"),

    SERVER_ACK("SERVER_ACK"),

    TEAM_CREATE("TEAM_CREATE"),

    CREATURE_MOVE("CREATURE_MOVE"),
    CREATURE_ATTACK("CREATURE_ATTACK"),
    CREATURE_INJURED("CREATURE_INJURED"),

    BULLET_DESTROY("BULLET_DESTROY"),

    SOMEONE_LOSE("SOMEONE_LOSE");

    private String description;

    MessageType(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

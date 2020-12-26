package main.java.nju.linhao.enums;

public enum MessageType {
    CLIENT_READY("CLIENT_READY"),

    SERVER_ACK("SERVER_ACK"),

    CREATURE_CREATE("CREATURE_CREATE"),
    CREATURE_MOVE("CREATURE_MOVE"),
    CREATURE_DAMAGE("CREATURE_DAMAGE"),

    BULLET_CREATE("BULLET_CREATE");

    private String description;

    MessageType(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

package main.java.nju.linhao.enums;

public enum Formation {
    LONG_SNAKE_FORMATION("长蛇阵"),
    FRONTAL_VECTOR_FORMATION("锋矢阵"),
    SQUARE_FORMATION("方阵");

    private String description;

    Formation(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

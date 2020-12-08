package main.java.nju.linhao.creature;

import main.java.nju.linhao.enums.Direction;
import main.java.nju.linhao.enums.GridEnum;

import main.java.nju.linhao.utils.Configuration;

public abstract class Creature {
    public Creature(String name) {
        this(name,
                Configuration.DEFAULT_HEALTH,
                Configuration.DEFAULT_DAMAGE,
                Configuration.DEFAULT_DEFENSE,
                Configuration.DEFAULT_CREATURE_SPEED);
    }

    public Creature(String name,
                    double health,
                    double damage,
                    double defense,
                    double speed) {
        this.id++;
        this.selectionStatus = SelectionStatus.UNSELECTED;
        this.creatureStatus = CreatureStatus.ALIVE;
        this.direction = Direction.NO_DIRECTION;

        this.name = name;
        this.health = health;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public double getDefense() {
        return defense;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public double[] getPos() {
        return new double[]{posX, posY};
    }

    public void setHealth(double health){
        this.health = health;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setPos(int[] pos){
        this.posX = pos[0];
        this.posY = pos[1];
    }

    public void setDefense(double defense){
        this.defense = defense;
    }

    public void selected(){
        this.selectionStatus = SelectionStatus.SELECTED;
    }

    public void unselected(){
        this.selectionStatus = SelectionStatus.UNSELECTED;
    }

    private enum SelectionStatus {
        SELECTED, UNSELECTED
    }

    private enum CreatureStatus {
        ALIVE, DEAD
    }

    private static int id = 0; // 全局唯一id
    private SelectionStatus selectionStatus;
    private CreatureStatus creatureStatus; // 是否存活
    private Direction direction; // 移动方向

    private String name; // 生物名字
    private double health; // 生命值
    private double damage; // 攻击力
    private double defense; //防御力
    private double speed; // 移动速度
    private int posX; // 当前位置x坐标
    private int posY; // 当前位置y坐标
}

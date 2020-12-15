package main.java.nju.linhao.creature;

import main.java.nju.linhao.enums.Direction;

import main.java.nju.linhao.utils.Configuration;

public abstract class Creature implements Runnable{

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

    public int getCreatureId() {
        return id;
    }

    public String getCreatureName() {
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

    public int[] getPos() {
        return new int[]{posX, posY};
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

    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
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

    @Override
    public void run(){
        while(this.creatureStatus == CreatureStatus.ALIVE && !Thread.interrupted()){
            if(this.selectionStatus == SelectionStatus.SELECTED){
                System.out.println(Thread.currentThread().getId() + " is being selected");
            }
            else if (this.selectionStatus == SelectionStatus.UNSELECTED){
                try {
                    Thread.sleep(Configuration.DEFAULT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                throw new NullPointerException("当前生物的选择状态不正确！");
            }
        }
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

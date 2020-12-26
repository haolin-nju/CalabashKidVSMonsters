package main.java.nju.linhao.creature;

import javafx.scene.image.Image;
import main.java.nju.linhao.ai.attack.RandomTargetSelector;
import main.java.nju.linhao.ai.attack.TargetSelector;
import main.java.nju.linhao.ai.direction.DirectionSelector;
import main.java.nju.linhao.ai.direction.RandomDirectionSelector;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.BulletFactory;
import main.java.nju.linhao.bullet.HumanBullet;
import main.java.nju.linhao.bullet.MonsterBullet;
import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.Direction;

import main.java.nju.linhao.enums.SelectionStatus;
import main.java.nju.linhao.utils.Configuration;
import main.java.nju.linhao.utils.ImageLoader;

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
        this.img = ImageLoader.loadImg(this.name);
        this.health = health;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
    }

    public int getCreatureId() {
        return id;
    }

    public CreatureStatus getCreatureStatus(){
        return this.creatureStatus;
    }

    public String getCreatureName() {
        return name;
    }

    public Image getImg(){
        return img;
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
        if(posX >= 0 && posX < Configuration.DEFAULT_GRID_ROWS){
            this.posX = posX;
        }
        if(posY >= 0 && posY < Configuration.DEFAULT_GRID_COLUMNS){
            this.posY = posY;
        }
    }

    public void modifyPos(int deltaX, int deltaY){
        int tempPosX = this.posX + deltaX;
        int tempPosY = this.posY + deltaY;
        setPos(tempPosX, tempPosY);
    }

    public void setDefense(double defense){
        this.defense = defense;
    }

    public void setSelected(){
        this.selectionStatus = SelectionStatus.SELECTED;
    }

    public void setUnselected(){
        this.selectionStatus = SelectionStatus.UNSELECTED;
    }

    public SelectionStatus getSelectionStatus(){
        return this.selectionStatus;
    }

    public Bullet attack(Creature attackTarget, double clickPosX, double clickPosY){
        // TODO: Communication and attack!!
        if (attackTarget instanceof Monster) {
            return BulletFactory.createHumanBullet((clickPosY - posY) / (clickPosX - posX), posX, posY);
        } else if (attackTarget instanceof Human) {
            return BulletFactory.createMonsterBullet((clickPosY - posY) / (clickPosX - posX), posX, posY);
        } else {
            return null;
        }
    }

    @Override
    public void run(){
        while(this.creatureStatus == CreatureStatus.ALIVE && !Thread.interrupted()){
            if(this.selectionStatus == SelectionStatus.SELECTED){
                System.out.println(Thread.currentThread().getId() + " is being selected");
            }
            else if (this.selectionStatus == SelectionStatus.UNSELECTED){
                // Select move direction
                DirectionSelector directionSelector = new RandomDirectionSelector();
                Direction moveDirection = directionSelector.selectDirection();
                switch(moveDirection){
                    case NO_DIRECTION:
                    default:
                        break;
                    case UP:
                        modifyPos(-1,0);
                        break;
                    case DOWN:
                        modifyPos(1,0);
                        break;
                    case LEFT:
                        modifyPos(0,-1);
                        break;
                    case RIGHT:
                        modifyPos(0,1);
                        break;
                }
                // TODO: Select attack target and attack



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


    private static int id = 0; // 全局唯一id
    private SelectionStatus selectionStatus;
    private CreatureStatus creatureStatus; // 是否存活
    private Direction direction; // 移动方向

    private String name; // 生物名字
    private Image img; // 生物图片
    private double health; // 生命值
    private double damage; // 攻击力
    private double defense; //防御力
    private double speed; // 移动速度
    private int posX; // 当前位置x坐标
    private int posY; // 当前位置y坐标

    @Override
    public String toString(){
        return name;
    }
}

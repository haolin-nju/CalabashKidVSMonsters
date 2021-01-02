package main.java.nju.linhao.creature;

import javafx.scene.image.Image;
import main.java.nju.linhao.ai.attack.RandomTargetSelector;
import main.java.nju.linhao.ai.attack.TargetSelector;
import main.java.nju.linhao.ai.direction.DirectionSelector;
import main.java.nju.linhao.ai.direction.RandomDirectionSelector;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.BulletFactory;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.enums.*;

import main.java.nju.linhao.utils.Configuration;
import main.java.nju.linhao.utils.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Creature implements Runnable, Serializable {

    public Creature(String name, Player belongsTo) {
        this(name,
                belongsTo,
                Configuration.DEFAULT_HEALTH,
                Configuration.DEFAULT_DAMAGE,
                Configuration.DEFAULT_DEFENSE,
                Configuration.DEFAULT_CREATURE_SPEED);
    }

    public Creature(String name,
                    Player belongsTo,
                    double health,
                    double damage,
                    double defense,
                    double speed) {
        this.creatureID = this.globalCreatureID;
        this.globalCreatureID++;
        this.selectionStatus = SelectionStatus.UNSELECTED;
        this.creatureStatus = CreatureStatus.ALIVE;
        this.direction = Direction.NO_DIRECTION;

        this.name = name;
        this.belongsTo = belongsTo;
        this.img = ImageLoader.getInstance().loadCreatureImg(this.name);
        this.health = health;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;

        this.attackFlag = false;
        this.attackTarget = null;
        this.clickPosX = 0;
        this.clickPosY = 0;

        this.healthLock = new ReentrantLock();
    }

    public int getCreatureId() {
        return creatureID;
    }

    public CreatureStatus getCreatureStatus() {
        return this.creatureStatus;
    }

    public String getCreatureName() {
        return name;
    }

    public Image getImg() {
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

    public void setHealth(double health) {
        this.healthLock.lock();
        try {
            this.health = health;
        } finally {
            this.healthLock.unlock();
        }
        if(this.health <= 0){
            this.creatureStatus = CreatureStatus.DEAD;
        }
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPos(int posX, int posY) {
        if (posX >= 0 && posX < Configuration.DEFAULT_GRID_ROWS) {
            this.posX = posX;
        }
        if (posY >= 0 && posY < Configuration.DEFAULT_GRID_COLUMNS) {
            this.posY = posY;
        }
    }

    public void modifyPos(int deltaX, int deltaY) {
        int tempPosX = this.posX + deltaX;
        int tempPosY = this.posY + deltaY;
        setPos(tempPosX, tempPosY);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                modifyPos(-1, 0);
                break;
            case LEFT:
                modifyPos(0, -1);
                break;
            case DOWN:
                modifyPos(1, 0);
                break;
            case RIGHT:
                modifyPos(0, 1);
                break;
        }
        this.direction = direction;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setSelected() {
        this.selectionStatus = SelectionStatus.SELECTED;
    }

    public void setUnselected() {
        this.selectionStatus = SelectionStatus.UNSELECTED;
    }

    public SelectionStatus getSelectionStatus() {
        return this.selectionStatus;
    }

    public Bullet attack(Creature attackTarget, double clickPosX, double clickPosY) {
        // TODO: Communication and attack!!
        double doublePosY = (posX + 0.5) * Configuration.DEFAULT_GRID_HEIGHT;
        double doublePosX = (posY + 0.5) * Configuration.DEFAULT_GRID_WIDTH;
        if (attackTarget instanceof Monster) {
            return BulletFactory.createHumanBullet(clickPosX, clickPosY, doublePosX, doublePosY);
        } else if (attackTarget instanceof Human) {
            return BulletFactory.createMonsterBullet(clickPosX, clickPosY, doublePosX, doublePosY);
        } else {
            return null;
        }
    }

    public void setAttack(Creature attackTarget, double clickPosX, double clickPosY) {
        this.attackTarget = attackTarget;
        this.clickPosX = clickPosX;
        this.clickPosY = clickPosY;
        this.attackFlag = true;
    }

    public void injured(double damage) {
        this.healthLock.lock();
        try {
            this.health -= (damage - defense);
        } finally {
            this.healthLock.unlock();
        }
        if (this.health <= 0) {
            creatureStatus = CreatureStatus.DEAD;
        }
    }

    @Override
    public void run() {
        boolean gameEnded = false;
        DirectionSelector directionSelector = new RandomDirectionSelector();
        ArrayList<? extends Creature> enemies = null;
        Player localPlayer = LocalGameController.getInstance().getLocalPlayer();
        if(localPlayer == Player.PLAYER_1){
            enemies = LocalGameController
                    .getInstance()
                    .getBattlefieldController()
                    .getBattlefield()
                    .getMonsterTeam()
                    .getTeamMembers();
        } else {
            enemies = LocalGameController
                    .getInstance()
                    .getBattlefieldController()
                    .getBattlefield()
                    .getHumanTeam()
                    .getTeamMembers();
        }
        TargetSelector targetSelector = new RandomTargetSelector(enemies);
        while (this.creatureStatus == CreatureStatus.ALIVE && !Thread.interrupted() && !gameEnded) {
            if (this.selectionStatus == SelectionStatus.SELECTED) {
                if (this.attackFlag == true) {
                    Bullet bullet = attack(attackTarget, clickPosX, clickPosY);
                    if (bullet != null) {
                        LocalGameController.getInstance().getBattlefieldController().getBattlefield().addBullet(bullet);
                        LocalGameController.getInstance().requestCreatureAttack(bullet);
                    }
                    this.attackFlag = false;//攻击完就不再攻击
                }
            } else if (this.selectionStatus == SelectionStatus.UNSELECTED) {
                this.attackTarget = targetSelector.selectAttackTarget();
                if(this.attackTarget != null) {
                    int[] attackTargetPos = attackTarget.getPos();
                    this.attackFlag = true;
                    Bullet bullet = attack(attackTarget,
                            attackTargetPos[1] * Configuration.DEFAULT_GRID_WIDTH,
                            attackTargetPos[0] * Configuration.DEFAULT_GRID_HEIGHT);
                    if (bullet != null) {
                        LocalGameController.getInstance().getBattlefieldController().getBattlefield().addBullet(bullet);
                        LocalGameController.getInstance().requestCreatureAttack(bullet);
                    }
                    this.attackFlag = false;
                }
                Direction moveDirection = directionSelector.selectDirection();
                LocalGameController.getInstance().requestCreatureMove(this, moveDirection);
                try {
                    Thread.sleep(Configuration.CREATURE_DEFAULT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            gameEnded = LocalGameController.getInstance().queryIsOtherLost();
        }
        if(this.creatureStatus == CreatureStatus.DEAD
                && LocalGameController.getInstance().localAliveCreaturesDec() <= 0
                && LocalGameController.getInstance().queryIsOtherLost() == false){
            LocalGameController.getInstance().endGame(LocalGameStatus.WE_LOSE);
        } else if (this.creatureStatus == CreatureStatus.ALIVE && isFirstToNotifyWinning == true) { //获胜一方的
            LocalGameController.getInstance().endGame(LocalGameStatus.WE_WIN);
            isFirstToNotifyWinning = false;
        }
    }

    private static final long serialVersionUID = 726374138698742258L;
    private static int globalCreatureID = 0; // 全局唯一id
    private int creatureID = 0;
    private SelectionStatus selectionStatus;
    private CreatureStatus creatureStatus; // 是否存活
    private Direction direction; // 移动方向

    private String name; // 生物名字
    private Player belongsTo; //属于哪个阵营，PLAYER_1是人类，PLAYER_2是妖怪
    private transient Image img; // 生物图片, 不可序列化，不用传输
    private double health; // 生命值
    private double damage; // 攻击力
    private double defense; //防御力
    private double speed; // 移动速度
    private int posX; // 当前位置x坐标
    private int posY; // 当前位置y坐标

    private boolean attackFlag; //是否需要进行攻击
    private Creature attackTarget; //攻击的目标
    private double clickPosX; //攻击点击的位置X
    private double clickPosY; //攻击点击的位置Y

    private ReentrantLock healthLock; // 血量的锁
    private static boolean isFirstToNotifyWinning = true;

    @Override
    public String toString() {
        return name;
    }
}

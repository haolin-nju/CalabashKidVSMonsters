package main.java.nju.linhao.battlefield;

import javafx.application.Platform;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.HumanBullet;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.controller.logic.NetworkController;
import main.java.nju.linhao.controller.window.MainWindowView;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.*;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.team.HumanTeam;

public class BattlefieldController {
    private Battlefield battlefield;
    private MainWindowView battlefieldView;
    private Formation curFormation;
    private int curFormationIdx;
    private Player curPlayer;
    private Creature curSelectedCreature;

    public BattlefieldController(Battlefield battlefield, MainWindowView battlefieldView) {
        this.battlefield = battlefield;
        this.battlefieldView = battlefieldView;
        curFormation = Formation.LONG_SNAKE_FORMATION;
        curFormationIdx = 0;
        curPlayer = Player.PLAYER_1;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public MainWindowView getBattlefieldView() {
        return battlefieldView;
    }

    public int getFormationIdx() {
        return curFormationIdx;
    }

    public Formation getFormation() {
        return curFormation;
    }

    public Creature getCurSelectedCreature() {
        return curSelectedCreature;
    }

    public void setLocalPlayer(Player player) {
        curPlayer = player;
        battlefield.getBulletManager().setLocalPlayer(player);
    }

    public void setFormation(Formation formation) {
        // TODO: Add some design patterns
        if (curPlayer == Player.PLAYER_1) {
            curFormationIdx = battlefield.getHumanTeam().setFormation(formation);
        } else if (curPlayer == Player.PLAYER_2) {
            curFormationIdx = battlefield.getMonsterTeam().setFormation(formation);
        }
        if (curFormationIdx == -1) {
            System.err.println("curFormationIdx = -1! It shouldn't occur!");
        }
        battlefield.updateCreatureGrids();
        curFormation = formation;
    }


    public int getLocalCreaturesAliveCnt(){
        if(curPlayer == Player.PLAYER_1){
            return battlefield.getHumanTeam().getTeamMembers().size();
        } else if(curPlayer == Player.PLAYER_2){
            return battlefield.getMonsterTeam().getTeamMembers().size();
        } else {
            return -1;
        }
    }

    public void setDefaultSelectedCreature() {
        if (curPlayer == Player.PLAYER_1) {
            curSelectedCreature = battlefield.getHumanTeam().getGrandpa();
        } else if (curPlayer == Player.PLAYER_2) {
            curSelectedCreature = battlefield.getMonsterTeam().getSnakeEssence();
        }
    }

    public synchronized void repaint() {
        LocalGameStatus localGameStatus = LocalGameController.getInstance().getCurrentStatus();
        if (localGameStatus == LocalGameStatus.INIT
                || localGameStatus == LocalGameStatus.READY) {
            Platform.runLater(() -> battlefieldView.paintLocalMainCanvas(battlefield, curPlayer));
        } else if (localGameStatus == LocalGameStatus.RUN) {
            Platform.runLater(() -> battlefieldView.paintBothMainCanvas(battlefield, curPlayer));
        } else if(localGameStatus == LocalGameStatus.WE_LOSE
                || localGameStatus == LocalGameStatus.WE_WIN) {
            Platform.runLater(() -> battlefieldView.paintEndMainCanvas(LocalGameController.getInstance().getStatusImg()));
        }
    }

    public void clear() {
        battlefield.clearCreatureGrids();
        Platform.runLater(() -> battlefieldView.clearMainCanvas());
        curFormation = Formation.LONG_SNAKE_FORMATION;
        curFormationIdx = 0;
        curPlayer = Player.PLAYER_1;
    }

    public void requestMouseClick(double clickPosX, double clickPosY, Player localPlayer) throws OutofRangeException {
        Creature selectedCreature = battlefield.getCreatureFromClickPos(clickPosX, clickPosY);
        if (localPlayer == Player.PLAYER_1) {
            if (selectedCreature instanceof Human) {
                curSelectedCreature.setUnselected();
                selectedCreature.setSelected();
                LocalGameController.getInstance().requestLogMessages("当前选择生物：" + selectedCreature.getCreatureName());
                curSelectedCreature = selectedCreature;
            } else if (selectedCreature instanceof Monster
                    && selectedCreature.getCreatureStatus() == CreatureStatus.ALIVE) {
                curSelectedCreature.setAttack(selectedCreature, clickPosX, clickPosY);
                LocalGameController.getInstance().requestLogMessages(curSelectedCreature.getCreatureName() + "攻击："
                        + selectedCreature.getCreatureName());
            }
        } else if (localPlayer == Player.PLAYER_2) {
            if (selectedCreature instanceof Monster) {
                curSelectedCreature.setUnselected();
                selectedCreature.setSelected();
                LocalGameController.getInstance().requestLogMessages("当前选择生物：" + selectedCreature.getCreatureName());
                curSelectedCreature = selectedCreature;
            } else if (selectedCreature instanceof Human
                    && selectedCreature.getCreatureStatus() == CreatureStatus.ALIVE) {
                curSelectedCreature.setAttack(selectedCreature, clickPosX, clickPosY);
//                Bullet bullet = curSelectedCreature.attack(selectedCreature, clickPosX, clickPosY);
//                if(bullet != null){
//                    battlefield.addBullet(bullet);
//                }
                //                LocalGameController.requestNetworkController();
                LocalGameController.getInstance().requestLogMessages(curSelectedCreature.getCreatureName() + "攻击："
                        + selectedCreature.getCreatureName());
            }
        }
        repaint();
    }

    public Creature letCurSelectedCreatureMove(Direction direction) {
        return letCreatureMove(curSelectedCreature, direction);
    }

    public Creature letCreatureMove(Creature creature, Direction direction) {
        int[] creaturePos = creature.getPos();
        synchronized (battlefield) {
            try {
                switch (direction) {
                    case UP:
                        if (battlefield.getCreatureFromPos(creaturePos[0] - 1, creaturePos[1]) == null) {
                            creature.move(Direction.UP);
                        }
                        break;
                    case DOWN:
                        if (battlefield.getCreatureFromPos(creaturePos[0] + 1, creaturePos[1]) == null) {
                            creature.move(Direction.DOWN);
                        }
                        break;
                    case LEFT:
                        if (battlefield.getCreatureFromPos(creaturePos[0], creaturePos[1] - 1) == null) {
                            creature.move(Direction.LEFT);
                        }
                        break;
                    case RIGHT:
                        if (battlefield.getCreatureFromPos(creaturePos[0], creaturePos[1] + 1) == null) {
                            creature.move(Direction.RIGHT);
                        }
                        break;
                }
                battlefield.updateCreatureGrids();
                return curSelectedCreature;
            } catch (OutofRangeException e) {
                LocalGameController.getInstance().requestLogMessages(creature.toString() + "不能再往外面走了！");
                return curSelectedCreature;
            }
        }
    }

    public void interruptThreads() {
        battlefield.endLocalCreatureThreads(curPlayer);
    }
}

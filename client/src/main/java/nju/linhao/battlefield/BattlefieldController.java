package main.java.nju.linhao.battlefield;

import javafx.application.Platform;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.HumanBullet;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.controller.window.MainWindowView;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.CreatureEnum;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;
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

    public void setLocalPlayer(Player player) {
        curPlayer = player;
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

    public void setDefaultSelectedCreature(){
        if (curPlayer == Player.PLAYER_1) {
            curSelectedCreature = battlefield.getHumanTeam().getGrandpa();
        } else if (curPlayer == Player.PLAYER_2) {
            curSelectedCreature = battlefield.getMonsterTeam().getSnakeEssence();
        }
    }

    public synchronized void repaint() {
        LocalGameStatus localGameStatus = LocalGameController.getCurrentStatus();
        if (localGameStatus == LocalGameStatus.INIT) {
            Platform.runLater(() -> battlefieldView.paintLocalMainCanvas(battlefield, curPlayer));
        } else if (localGameStatus == LocalGameStatus.READY
                || localGameStatus == LocalGameStatus.RUN) {
            Platform.runLater(() -> battlefieldView.paintBothMainCanvas(battlefield, curPlayer));
        }
    }

    public void clear() {
        Platform.runLater(() -> battlefieldView.clearMainCanvas());
        curFormation = Formation.LONG_SNAKE_FORMATION;
        curFormationIdx = 0;
        curPlayer = Player.PLAYER_1;
    }

    public void requestMouseClick(double clickPosX, double clickPosY, Player localPlayer) throws OutofRangeException {
        Creature selectedCreature = battlefield.getCreatureFromPos(clickPosX, clickPosY);
        if (localPlayer == Player.PLAYER_1) {
            if (selectedCreature instanceof Human) {
                curSelectedCreature.setUnselected();
                selectedCreature.setSelected();
                LocalGameController.requestLogMessages("当前选择生物：" + selectedCreature);
                curSelectedCreature = selectedCreature;
            } else if (selectedCreature instanceof Monster
                    && curSelectedCreature instanceof Human) {
                Bullet bullet = curSelectedCreature.attack(selectedCreature, clickPosX, clickPosY);
                if(bullet!=null){
                    battlefield.addBullet(bullet);
                }
            }
        } else if(localPlayer == Player.PLAYER_2){
            if(selectedCreature instanceof Monster){
                curSelectedCreature.setUnselected();;
                selectedCreature.setSelected();
                LocalGameController.requestLogMessages("当前选择生物：" + selectedCreature);
                curSelectedCreature = selectedCreature;
            } else if (selectedCreature instanceof Human
                    && curSelectedCreature instanceof Monster) {
                Bullet bullet = curSelectedCreature.attack(selectedCreature, clickPosX, clickPosY);
                if(bullet!=null){
                    battlefield.addBullet(bullet);
                }

            }
        }
        repaint();
    }
}

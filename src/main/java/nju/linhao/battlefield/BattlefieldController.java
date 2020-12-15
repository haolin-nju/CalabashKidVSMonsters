package main.java.nju.linhao.battlefield;

import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.controller.window.BattlefieldView;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.CreatureEnum;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.team.HumanTeam;

public class BattlefieldController implements Runnable {
    private Battlefield battlefield;
    private BattlefieldView battlefieldView;
    private Formation curFormation;
    private int curFormationIdx;
    private Player curPlayer;

    public BattlefieldController(Battlefield battlefield, BattlefieldView battlefieldView) {
        this.battlefield = battlefield;
        this.battlefieldView = battlefieldView;
        curFormation = Formation.LONG_SNAKE_FORMATION;
        curFormationIdx = 0;
        curPlayer = Player.PLAYER_1;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public BattlefieldView getBattlefieldView() {
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

    @Override
    public void run() {
        while (LocalGameController.getCurrentStatus() == LocalGameStatus.RUN && !Thread.interrupted()) {
            battlefieldView.paintMainCanvas(battlefield);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

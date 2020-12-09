package main.java.nju.linhao.battlefield;

import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.controller.window.BattlefieldView;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.GridEnum;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;

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

    public Battlefield getBattlefield(){
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
            switch (formation) {
                case LONG_SNAKE_FORMATION:
                    battlefield.clearGrids();
                    battlefield.setGrid(0, 7, GridEnum.GRANDPA);
                    battlefield.setGrid(0, 8, GridEnum.PANGOLIN);
                    battlefield.setGrids(new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{3, 4, 5, 6, 9, 10, 11}, GridEnum.CALABASH_KID);
                    curFormationIdx = 0;
                    break;
                case FRONTAL_VECTOR_FORMATION:
                    battlefield.clearGrids();
//                    battlefield.setGrid()
                    curFormationIdx = 1;
                    break;
                case SQUARE_FORMATION:
                    curFormationIdx = 2;
                    break;
                default:
                    assert (false);
            }
        } else if (curPlayer == Player.PLAYER_2) {
            switch (formation) {
                case LONG_SNAKE_FORMATION:
                    battlefield.clearGrids();
                    curFormationIdx = 0;
                    break;
                case FRONTAL_VECTOR_FORMATION:
                    battlefield.clearGrids();
                    curFormationIdx = 1;
                    break;
                case SQUARE_FORMATION:
                    battlefield.clearGrids();
                    curFormationIdx = 2;
                    break;
                default:
                    assert (false);
            }
        }
        curFormation = formation;
    }

    @Override
    public void run() {
        while(LocalGameController.getCurrentStatus() == LocalGameStatus.RUN && !Thread.interrupted()){
            battlefieldView.setMainCanvas(battlefield);
            battlefieldView.refreshCanvas();
        }
    }
}

package main.java.nju.linhao.controller.window;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.GridEnum;
import main.java.nju.linhao.enums.Player;

import java.util.ArrayList;

public class BattlefieldController {
    private static Battlefield battlefield;
    private static Formation curFormation;
    private static int curFormationIdx;

    public BattlefieldController() {
        battlefield = new Battlefield(20, 15);
        curFormation = Formation.LONG_SNAKE_FORMATION;
        curFormationIdx = 0;
    }

    public int getFormationIdx(){
        return curFormationIdx;
    }

    public Formation getFormation(){
        return curFormation;
    }

    public void setFormation(Formation formation, Player player) {
        // TODO
        if (player == Player.PLAYER_1) {
            switch (formation) {
                case LONG_SNAKE_FORMATION:
                    battlefield.setGrid(0, 7, GridEnum.GRANDPA);
                    battlefield.setGrid(0, 8, GridEnum.PANGOLIN);
                    battlefield.setGrids(new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{3, 4, 5, 6, 9, 10, 11}, GridEnum.CALABASH_KID);
                    curFormationIdx = 0;
                    break;
                case FRONTAL_VECTOR_FORMATION:
                    curFormationIdx = 1;
                    break;
                default:
                    assert (false);
            }
        } else if (player == Player.PLAYER_2) {
            switch (formation) {
                case LONG_SNAKE_FORMATION:
                    curFormationIdx = 0;
                    break;
                case FRONTAL_VECTOR_FORMATION:
                    curFormationIdx = 1;
                    break;

                default:
                    assert(false);
            }
        }
        curFormation = formation;
    }

    @FXML
    private Canvas mainCanvas;

    @FXML
    void initialize() {
        // init background
        final Image backgroundImg = new Image(getClass().getResourceAsStream("/pictures/background.jpg"));
        GraphicsContext graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.save();
        graphicsContext.drawImage(backgroundImg, 0, 0, 768, 532);
        graphicsContext.restore();

        // init creatures
    }
}

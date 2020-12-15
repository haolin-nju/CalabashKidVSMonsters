package main.java.nju.linhao.controller.window;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.team.HumanTeam;

import java.util.ArrayList;

public class BattlefieldView {
    public synchronized void paintMainCanvas(Battlefield battlefield) {
        ArrayList<Human> humans = battlefield.getHumanTeam().getTeamMembers();
        paintCreatures(humans);
        ArrayList<Monster> monsters = battlefield.getHumanTeam().getTeamMemebers();
        paintCreatures(monsters);
    }

    private void paintCreatures(ArrayList<? extends Creature> creatures) {
        for (Creature creature : creatures) {

        }
    }

    public void refreshCanvas() {

    }

    @FXML
    private Canvas mainCanvas;

    private GraphicsContext gc;

    @FXML
    void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
    }

}

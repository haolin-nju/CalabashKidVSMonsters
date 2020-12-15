package main.java.nju.linhao.controller.window;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.battlefield.BattlefieldController;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.team.HumanTeam;

public class BattlefieldView {
    public synchronized void paintMainCanvas(Battlefield battlefield){
        HumanTeam humanTeam = battlefield.getHumanTeam();

    }

    public void refreshCanvas(){

    }

    @FXML
    private Canvas mainCanvas;

    private GraphicsContext gc;

    @FXML
    void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
    }

}

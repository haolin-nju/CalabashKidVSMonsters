package main.java.nju.linhao.controller.window;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BattlefieldView {
    public void setMainCanvas(){

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

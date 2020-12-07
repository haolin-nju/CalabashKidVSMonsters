package main.java.nju.linhao.battlefield;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Battlefield {
    private static int width;
    private static int height;

    @FXML
    private Canvas mainCanvas;

    public Battlefield(){
        width = 20;
        height = 15;
    }

    @FXML
    void initialize() {
        final Image img = new Image(getClass().getResourceAsStream("/pictures/background.jpg"));
        GraphicsContext graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.save();
        graphicsContext.drawImage(img, 0, 0,480,360);
        graphicsContext.restore();
    }
}
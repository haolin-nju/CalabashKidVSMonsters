package main.java.nju.linhao;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainCanvas"
    private Canvas mainCanvas; // Value injected by FXMLLoader

    @FXML // fx:id="startButton"
    private Button startButton; // Value injected by FXMLLoader

    @FXML // fx:id="loadButton"
    private Button loadButton; // Value injected by FXMLLoader

    @FXML
    void startButtonClicked(MouseEvent event) {
        System.out.println("开始游戏！");
    }

    @FXML
    void loadButtonClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开战斗记录");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.showOpenDialog(null);
    }

    
    // Codes below are for init
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 300;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert mainCanvas != null : "fx:id=\"mainCanvas\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert loadButton != null : "fx:id=\"loadButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        final Image img = new Image(getClass().getResourceAsStream("/pictures/background.jpg"));
        GraphicsContext graphicsContext = mainCanvas.getGraphicsContext2D();
        graphicsContext.save();
        graphicsContext.drawImage(img, 0, 0,480,360);
        graphicsContext.restore();
    }
}

package main.java.nju.linhao.controller.window;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.bullet.Bullet;
import main.java.nju.linhao.bullet.HumanBullet;
import main.java.nju.linhao.bullet.MonsterBullet;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.CreatureStatus;
import main.java.nju.linhao.enums.LocalGameStatus;
import main.java.nju.linhao.enums.Player;
import main.java.nju.linhao.enums.SelectionStatus;
import main.java.nju.linhao.exception.OutofRangeException;
import main.java.nju.linhao.io.Restorer;
import main.java.nju.linhao.utils.Configuration;

import static main.java.nju.linhao.utils.Configuration.DEFAULT_BULLET_RADIUS;

public class MainWindowView {
    private HostServices hostServices;

    public void setHostServices(HostServices mainHostServices) {
        hostServices = mainHostServices;
    }

    public void logMessages(String log) {
        logTextArea.appendText(log + "\n");
    }

    public Canvas getCanvasController() {
        return mainCanvas;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="newGameMenuItem"
    private MenuItem newGameMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="openMenuItem"
    private MenuItem openMenuItem; // Value injected by FXMLLoader

//    @FXML // fx:id="continueMenuItem"
//    private MenuItem continueMenuItem; // Value injected by FXMLLoader
//
//    @FXML // fx:id="pauseMenuItem"
//    private MenuItem pauseMenuItem; // Value injected by FXMLLoader
//
//    @FXML // fx:id="stopMenuItem"
//    private MenuItem stopMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="saveMenuItem"
    private MenuItem saveMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="saveAsMenuItem"
    private MenuItem saveAsMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="quitMenuItem"
    private MenuItem quitMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="instructionsMenuItem"
    private MenuItem instructionsMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="aboutMenuItem"
    private MenuItem aboutMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="logTextArea"
    private TextArea logTextArea;

    @FXML
    private Canvas mainCanvas;

    private GraphicsContext gc;

    @FXML
    void aboutMenuItemOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于CalabashKids VS Monsters");
        alert.setHeaderText("葫芦娃大战妖精");
        alert.setContentText("版本：v1.0\n作者：林昊\n版权：GPL v3.0");
        alert.showAndWait();
    }

    @FXML
    void instructionsMenuItemOnAction(ActionEvent event) {
        String filePath = getClass().getResource("/docs/CalabashKids VS Monsters Guide.pdf").toString();
        System.out.println(filePath);
        hostServices.showDocument(filePath);
        System.out.println("打开游戏指南");
    }

    @FXML
    void newGameMenuItemOnAction(ActionEvent event) {
//        continueMenuItem.setDisable(true);
//        pauseMenuItem.setDisable(false);
//        stopMenuItem.setDisable(false);
        LocalGameController.getInstance().newGame();
    }

    @FXML
    void openMenuItemOnAction(ActionEvent event) {
        Restorer.restore();
    }

//    @FXML
//    void continueMenuItemOnAction(ActionEvent event) {
//        pauseMenuItem.setDisable(false);
//        LocalGameController.getInstance().continueGame();
//        continueMenuItem.setDisable(true);
//    }
//
//    @FXML
//    void pauseMenuItemOnAction(ActionEvent event) {
//        continueMenuItem.setDisable(false);
//        LocalGameController.getInstance().pauseGame();
//        pauseMenuItem.setDisable(true);
//    }

    @FXML
    void quitMenuItemOnAction(ActionEvent event) {
        logMessages("退出游戏！");
    }

    @FXML
    void saveAsMenuItemOnAction(ActionEvent event) {
        logMessages("保存为");
    }

    @FXML
    void saveMenuItemOnAction(ActionEvent event) {
        logMessages("保存");
    }
//
//    @FXML
//    void stopMenuItemOnAction(ActionEvent event) {
//        continueMenuItem.setDisable(true);
//        pauseMenuItem.setDisable(true);
//        stopMenuItem.setDisable(true);
//        LocalGameController.getInstance().endGame(LocalGameStatus.WE_LOSE);
//
//    }


    @FXML
    public void mainCanvasOnMouseClicked(MouseEvent mouseEvent) {
        double clickPosX = mouseEvent.getX();
        double clickPosY = mouseEvent.getY();
        try {
            LocalGameController.getInstance().requestMouseClick(clickPosX, clickPosY);
        } catch (OutofRangeException e) {
            e.printStackTrace();
        }
        // TODO: just repaint the canvas
    }

    public void clearMainCanvas() {
        gc.clearRect(0, 0, Configuration.CANVAS_WIDTH, Configuration.CANVAS_HEIGHT);
    }

    public void paintLocalMainCanvas(Battlefield battlefield, Player curPlayer) {
        gc.clearRect(0, 0, Configuration.CANVAS_WIDTH, Configuration.CANVAS_HEIGHT);
        if (curPlayer == Player.PLAYER_1) {
            ArrayList<Human> humans = battlefield.getHumanTeam().getTeamMembers();
            paintCreatures(humans, true);
        } else if (curPlayer == Player.PLAYER_2) {
            ArrayList<Monster> monsters = battlefield.getMonsterTeam().getTeamMemebers();
            paintCreatures(monsters, true);
        }
    }

    public void paintBothMainCanvas(Battlefield battlefield, Player curPlayer) {
        gc.clearRect(0, 0, Configuration.CANVAS_WIDTH, Configuration.CANVAS_HEIGHT);
        ArrayList<Human> humans = battlefield.getHumanTeam().getTeamMembers();
        paintCreatures(humans, curPlayer == Player.PLAYER_1);
        ArrayList<Monster> monsters = battlefield.getMonsterTeam().getTeamMemebers();
        paintCreatures(monsters, curPlayer == Player.PLAYER_2);
        LinkedList<HumanBullet> humanBullets = battlefield.getBulletManager().getHumanBullets();
        paintBullets(humanBullets, curPlayer == Player.PLAYER_1);
        LinkedList<MonsterBullet> monsterBullets = battlefield.getBulletManager().getMonsterBullets();
        paintBullets(monsterBullets, curPlayer == Player.PLAYER_2);
    }

    public void paintEndMainCanvas(Image statusImg){
        gc.drawImage(statusImg,
                204,
                198,
                360,
                120);
    }

    private void paintCreatures(ArrayList<? extends Creature> creatures, boolean isCurrentPlayer) {
        int[] curCreaturePos;
        gc.save();
        if (isCurrentPlayer) {
            gc.setFill(Color.BLUE);
        } else {
            gc.setFill(Color.RED);
        }
        for (Creature creature : creatures) {
            if (creature.getCreatureStatus() == CreatureStatus.ALIVE) {
                // Draw creatures
                curCreaturePos = creature.getPos();
                double topLeftY = Configuration.DEFAULT_GRID_HEIGHT * curCreaturePos[0];
                double topLeftX = Configuration.DEFAULT_GRID_WIDTH * curCreaturePos[1];
                gc.drawImage(creature.getImg(),
                        topLeftX,
                        topLeftY,
                        Configuration.DEFAULT_GRID_WIDTH,
                        Configuration.DEFAULT_GRID_HEIGHT);
                // Draw Blood Lines
                gc.setStroke(Color.BLACK);
                gc.strokeRect(topLeftX,
                        topLeftY,
                        Configuration.DEFAULT_GRID_WIDTH,
                        3);
                gc.fillRect(topLeftX,
                        topLeftY,
                        Configuration.DEFAULT_GRID_WIDTH
                                * creature.getHealth()
                                / Configuration.DEFAULT_HEALTH,
                        3);
                // Draw Selection status
                if (creature.getSelectionStatus() == SelectionStatus.SELECTED) {
                    gc.setStroke(Color.GOLD);
                    gc.strokeRect(topLeftX,
                            topLeftY,
                            Configuration.DEFAULT_GRID_WIDTH,
                            Configuration.DEFAULT_GRID_HEIGHT);
                }

            }
        }
        gc.restore();
    }

    private void paintBullets(LinkedList<? extends Bullet> bullets, boolean isCurrentPlayer) {
        gc.save();
        if (isCurrentPlayer) {
            gc.setFill(Color.BLUE);
        } else {
            gc.setFill(Color.RED);
        }
        for (Bullet bullet : bullets) {
            double[] bulletPos = bullet.getPos();
            gc.setStroke(Color.BLACK);
            gc.strokeOval(bulletPos[0] - DEFAULT_BULLET_RADIUS,
                    bulletPos[1] - DEFAULT_BULLET_RADIUS,
                    2 * DEFAULT_BULLET_RADIUS,
                    2 * DEFAULT_BULLET_RADIUS);
            gc.fillOval(bulletPos[0] - DEFAULT_BULLET_RADIUS,
                    bulletPos[1] - DEFAULT_BULLET_RADIUS,
                    2 * DEFAULT_BULLET_RADIUS,
                    2 * DEFAULT_BULLET_RADIUS);
        }
        gc.restore();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert newGameMenuItem != null : "fx:id=\"newGameMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert openMenuItem != null : "fx:id=\"openMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
//        assert pauseMenuItem != null : "fx:id=\"pauseMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
//        assert continueMenuItem != null : "fx:id=\"continueMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
//        assert stopMenuItem != null : "fx:id=\"stopMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert saveMenuItem != null : "fx:id=\"saveMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert saveAsMenuItem != null : "fx:id=\"saveAsMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert quitMenuItem != null : "fx:id=\"quitMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert instructionsMenuItem != null : "fx:id=\"instructionsMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aboutMenuItem != null : "fx:id=\"aboutMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert logTextArea != null : "fx:id=\"logTextArea\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert mainCanvas != null : "fx:id=\"mainCanvas\" was not injected: check your FXML file 'MainWindow.fxml'.";

//        continueMenuItem.setDisable(true);
//        pauseMenuItem.setDisable(true);
//        stopMenuItem.setDisable(true);

        // for canvas
        gc = mainCanvas.getGraphicsContext2D();
    }
}

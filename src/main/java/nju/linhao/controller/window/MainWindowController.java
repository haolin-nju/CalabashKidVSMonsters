package main.java.nju.linhao.controller.window;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.io.Restorer;

public class MainWindowController {
    private static HostServices hostServices;

    public void setHostServices(HostServices mainHostServices) {
        hostServices = mainHostServices;
    }

    public void logMessages(String log) {
        logTextArea.appendText(log + "\n");
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="newGameMenuItem"
    private MenuItem newGameMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="openMenuItem"
    private MenuItem openMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="continueMenuItem"
    private MenuItem continueMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="pauseMenuItem"
    private MenuItem pauseMenuItem; // Value injected by FXMLLoader

    @FXML // fx:id="stopMenuItem"
    private MenuItem stopMenuItem; // Value injected by FXMLLoader

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
        continueMenuItem.setDisable(true);
        pauseMenuItem.setDisable(false);
        stopMenuItem.setDisable(false);
        LocalGameController.newGame();
    }

    @FXML
    void openMenuItemOnAction(ActionEvent event) {
        Restorer.restore();
    }

    @FXML
    void continueMenuItemOnAction(ActionEvent event) {
        pauseMenuItem.setDisable(false);
        LocalGameController.continueGame();
        continueMenuItem.setDisable(true);
    }

    @FXML
    void pauseMenuItemOnAction(ActionEvent event) {
        continueMenuItem.setDisable(false);
        LocalGameController.pauseGame();
        pauseMenuItem.setDisable(true);
    }

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

    @FXML
    void stopMenuItemOnAction(ActionEvent event) {
        continueMenuItem.setDisable(true);
        pauseMenuItem.setDisable(true);
        stopMenuItem.setDisable(true);
        LocalGameController.endGame();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert newGameMenuItem != null : "fx:id=\"newGameMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert openMenuItem != null : "fx:id=\"openMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert pauseMenuItem != null : "fx:id=\"pauseMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert continueMenuItem != null : "fx:id=\"continueMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert stopMenuItem != null : "fx:id=\"stopMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert saveMenuItem != null : "fx:id=\"saveMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert saveAsMenuItem != null : "fx:id=\"saveAsMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert quitMenuItem != null : "fx:id=\"quitMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert instructionsMenuItem != null : "fx:id=\"instructionsMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aboutMenuItem != null : "fx:id=\"aboutMenuItem\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert logTextArea != null : "fx:id=\"logTextArea\" was not injected: check your FXML file 'MainWindow.fxml'.";

        continueMenuItem.setDisable(true);
        pauseMenuItem.setDisable(true);
        stopMenuItem.setDisable(true);
    }
}

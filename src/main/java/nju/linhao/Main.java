package main.java.nju.linhao;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import main.java.nju.linhao.battlefield.Battlefield;
import main.java.nju.linhao.enums.GameStatus;
import main.java.nju.linhao.io.Restorer;
import main.java.nju.linhao.controller.WindowController;
import main.java.nju.linhao.controller.GameController;
import main.java.nju.linhao.controller.NetworkController;


import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Class<?> curClass = this.getClass();
        FXMLLoader loader = new FXMLLoader(curClass.getResource("/MainWindow.fxml"));
        Parent parent = loader.load();
        HostServices hostServices = this.getHostServices();

        WindowController.init(hostServices);

        Battlefield battlefield = new Battlefield();

        GameController.init(hostServices);

        Scene scene = new Scene(parent);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.SPACE){
                if(GameController.getCurrentStatus() == GameStatus.END){
                    GameController.resetGame();
                }
                else if(GameController.getCurrentStatus() == GameStatus.READY){
                    GameController.startNewGame();
                }
            }
            if(event.getCode() == KeyCode.L){
                if(GameController.getCurrentStatus() == GameStatus.END
                        || GameController.getCurrentStatus() == GameStatus.READY){
                    Restorer.restore();
                }

            }
        });

        primaryStage.setTitle("CalabashKids VS Monsters");
        primaryStage.getIcons()
                .add(new Image(curClass.getResourceAsStream("/icon/CalabashKidsVSMonstersIcon.png")
                ));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

package main.java.nju.linhao.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.enums.LocalGameStatus;
import sun.security.krb5.Config;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ImageLoader {
    private static HashMap<String, String> creatureImgPath = null;
    private static HashMap<LocalGameStatus, String> gameStatusImgPath = null;

    private static ImageLoader imageLoader = new ImageLoader();

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        return imageLoader;
    }

    public Image loadCreatureImg(String name) {
        if (creatureImgPath == null) {
            creatureImgPath = new HashMap<>();
            creatureImgPath.put(Configuration.DEFAULT_GRANDPA_NAME, "/pictures/creatures/Grandpa.png");
            creatureImgPath.put(Configuration.DEFAULT_PANGOLIN_NAME, "/pictures/creatures/Pangolin.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_1_NAME, "/pictures/creatures/CalabashKid1.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_2_NAME, "/pictures/creatures/CalabashKid2.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_3_NAME, "/pictures/creatures/CalabashKid3.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_4_NAME, "/pictures/creatures/CalabashKid4.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_5_NAME, "/pictures/creatures/CalabashKid5.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_6_NAME, "/pictures/creatures/CalabashKid6.png");
            creatureImgPath.put(Configuration.DEFAULT_CALABASH_KID_7_NAME, "/pictures/creatures/CalabashKid7.png");

            creatureImgPath.put(Configuration.DEFAULT_SNAKE_ESSENCE_NAME, "/pictures/creatures/SnakeEssence.png");
            creatureImgPath.put(Configuration.DEFAULT_SCORPION_ESSENCE_NAME, "/pictures/creatures/ScorpionEssence.png");
            creatureImgPath.put(Configuration.DEFAULT_CENTIPEDE_ESSENCE_NAME, "/pictures/creatures/CentipedeEssence.png");
            for(int i=0;i<Configuration.DEFAULT_MINION_NUMS;++i) {
                creatureImgPath.put(Configuration.DEFAULT_MINION_NAME + i, "/pictures/creatures/Minion.png");
            }
        }
        Image creatureImg = new Image(ImageLoader.class.getResourceAsStream(creatureImgPath.get(name)));
        return creatureImg;
    }

    public Image loadGameStatusImg(LocalGameStatus localGameStatus){
        if (gameStatusImgPath == null){
            gameStatusImgPath = new HashMap<>();
            gameStatusImgPath.put(LocalGameStatus.WE_LOSE, "/pictures/youlose.png");
            gameStatusImgPath.put(LocalGameStatus.WE_WIN, "/pictures/youwin.png");
        }
        Image gameStatusImg = new Image(ImageLoader.class.getResourceAsStream(gameStatusImgPath.get(localGameStatus)));
        return gameStatusImg;
    }
}

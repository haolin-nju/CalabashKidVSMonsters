package main.java.nju.linhao.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sun.security.krb5.Config;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ImageLoader {
    private static HashMap<String, String> imgPath = null;

    public static Image loadImg(String name) {
        if (imgPath == null) {
            imgPath = new HashMap<>();
            imgPath.put(Configuration.DEFAULT_GRANDPA_NAME, "/pictures/creatures/Grandpa.png");
            imgPath.put(Configuration.DEFAULT_PANGOLIN_NAME, "/pictures/creatures/Pangolin.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_1_NAME, "/pictures/creatures/CalabashKid1.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_2_NAME, "/pictures/creatures/CalabashKid2.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_3_NAME, "/pictures/creatures/CalabashKid3.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_4_NAME, "/pictures/creatures/CalabashKid4.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_5_NAME, "/pictures/creatures/CalabashKid5.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_6_NAME, "/pictures/creatures/CalabashKid6.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_7_NAME, "/pictures/creatures/CalabashKid7.png");

            imgPath.put(Configuration.DEFAULT_SNAKE_ESSENCE_NAME, "/pictures/creatures/SnakeEssence.png");
            imgPath.put(Configuration.DEFAULT_SCORPION_ESSENCE_NAME, "/pictures/creatures/ScorpionEssence.png");
            imgPath.put(Configuration.DEFAULT_CENTIPEDE_ESSENCE_NAME, "/pictures/creatures/CentipedeEssence.png");
            imgPath.put(Configuration.DEFAULT_MINION_NAME, "/pictures/creatures/Minion.png");
        }
        Image icon;
        icon = new Image(ImageLoader.class.getResourceAsStream(imgPath.get(name)));

        return icon;
    }
}

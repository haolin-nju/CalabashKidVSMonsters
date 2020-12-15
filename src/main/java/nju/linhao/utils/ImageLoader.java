package main.java.nju.linhao.utils;

import javafx.scene.image.Image;
import sun.security.krb5.Config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ImageLoader {
    private static HashMap<String, String> imgPath = null;

    public static Image loadImg(String name) {
        if(imgPath == null){
            imgPath = new HashMap<>();
            imgPath.put(Configuration.DEFAULT_GRANDPA_NAME,"/creatures/Grandpa.png");
            imgPath.put(Configuration.DEFAULT_PANGOLIN_NAME,"/creatures/Pangolin.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_1_NAME,"/creatures/CalabashKid1.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_2_NAME,"/creatures/CalabashKid2.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_3_NAME,"/creatures/CalabashKid3.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_4_NAME,"/creatures/CalabashKid4.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_5_NAME,"/creatures/CalabashKid5.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_6_NAME,"/creatures/CalabashKid6.png");
            imgPath.put(Configuration.DEFAULT_CALABASH_KID_7_NAME,"/creatures/CalabashKid7.png");

            imgPath.put(Configuration.DEFAULT_SNAKE_ESSENCE_NAME,"/creatures/SnakeEssence.png");
            imgPath.put(Configuration.DEFAULT_SCORPION_ESSENCE_NAME,"/creatures/ScorpionEssence.png");
            imgPath.put(Configuration.DEFAULT_CENTIPEDE_ESSENCE_NAME,"/creatures/CentipedeEssence.png");
            imgPath.put(Configuration.DEFAULT_MINION_NAME, "/creatures/Minion.png");
        }
        return new Image(ImageLoader.class.getResourceAsStream(imgPath.get(name)));
    }
}

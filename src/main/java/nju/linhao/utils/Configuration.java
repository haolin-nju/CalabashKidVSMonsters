package main.java.nju.linhao.utils;

public class Configuration {
    // Codes below are for Canvas width and height
    public static final int CANVAS_WIDTH = 768;
    public static final int CANVAS_HEIGHT = 515;

    // for Battlefield
    public static final int DEFAULT_GRID_COLUMNS = 20;
    public static final int DEFAULT_GRID_ROWS = 15;
    public static final int DEFAULT_MINION_NUMS = 6;
    public static final double DEFAULT_GRID_WIDTH = CANVAS_WIDTH / DEFAULT_GRID_COLUMNS;
    public static final double DEFAULT_GRID_HEIGHT = CANVAS_HEIGHT / DEFAULT_GRID_ROWS;


    // for Creture
    public static final double DEFAULT_HEALTH = 100;
    public static final double DEFAULT_DAMAGE = 20;
    public static final double DEFAULT_DEFENSE = 10;
    public static final double DEFAULT_CREATURE_SPEED = 10;
    public static final long DEFAULT_SLEEP_TIME = 1000;

    // for Human Name
    public static final String DEFAULT_GRANDPA_NAME = "爷爷";
    public static final String DEFAULT_PANGOLIN_NAME = "穿山甲";
    public static final String DEFAULT_CALABASH_KID_1_NAME = "大娃";
    public static final String DEFAULT_CALABASH_KID_2_NAME = "二娃";
    public static final String DEFAULT_CALABASH_KID_3_NAME = "三娃";
    public static final String DEFAULT_CALABASH_KID_4_NAME = "四娃";
    public static final String DEFAULT_CALABASH_KID_5_NAME = "五娃";
    public static final String DEFAULT_CALABASH_KID_6_NAME = "六娃";
    public static final String DEFAULT_CALABASH_KID_7_NAME = "七娃";

    // for Monster Name
    public static final String DEFAULT_SNAKE_ESSENCE_NAME = "蛇精";
    public static final String DEFAULT_SCORPION_ESSENCE_NAME = "蝎子精";
    public static final String DEFAULT_CENTIPEDE_ESSENCE_NAME = "蜈蚣精";
    public static final String DEFAULT_MINION_NAME = "小喽啰";

    // for Bullet
    public static double DEFAULT_BULLET_DAMAGE = 20;
    public static double DEFAULT_BULLET_SPEED = 15;
    public static double DEFAULT_BULLET_RADIUS = 3;

    // for Server Client
    public static int DEFAULT_PORT = 7777;


    // for Synchronization
    public static double SYNC_TIME;
}

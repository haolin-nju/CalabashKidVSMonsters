package main.java.nju.linhao.io;

import javafx.stage.FileChooser;

import java.io.File;

public class Restorer {
    private static Restorer restorer = new Restorer();
    private static final FileChooser fileChooser = new FileChooser();

    private Restorer(){
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("打开战斗记录");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    public static Restorer getInstance(){
        return restorer;
    }

    public File restore() {
        return fileChooser.showOpenDialog(null);
    }


}

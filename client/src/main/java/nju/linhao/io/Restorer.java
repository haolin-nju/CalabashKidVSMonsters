package main.java.nju.linhao.io;

import javafx.stage.FileChooser;

import java.io.File;

public class Restorer {
    private static final FileChooser fileChooser = new FileChooser();

    static {
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("打开战斗记录");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    public static File restore() {
        return fileChooser.showOpenDialog(null);
    }
}

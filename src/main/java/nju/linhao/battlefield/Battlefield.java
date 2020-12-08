package main.java.nju.linhao.battlefield;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.GridEnum;

import java.util.ArrayList;

public class Battlefield {
    private static int columns;
    private static int rows;
    private static GridEnum [][] grids;

    public Battlefield(){
        this(20, 15);
    }

    public Battlefield(int columns, int rows){
        this.columns = columns;
        this.rows = rows;
        grids = new GridEnum[this.rows][this.columns];
    }

    public static void setGrids(int[] column, int[] row, GridEnum gridEnum){
        try{
            synchronized (grids) {
                for (int i = 0; i < column.length; ++i) {
                    grids[row[i]][column[i]] = gridEnum;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setGrid(int column, int row, GridEnum gridEnum){
        try{
            synchronized (grids) {
                grids[row][column] = gridEnum;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
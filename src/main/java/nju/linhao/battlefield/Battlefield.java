package main.java.nju.linhao.battlefield;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.nju.linhao.enums.Formation;
import main.java.nju.linhao.enums.GridEnum;
import main.java.nju.linhao.team.HumanTeam;
import main.java.nju.linhao.team.MonsterTeam;
import main.java.nju.linhao.team.TeamBuilder;

import java.util.ArrayList;

public class Battlefield implements Runnable {
    private static int columns;
    private static int rows;
    private static GridEnum [][] grids;
    private static HumanTeam humanTeam;
    private static MonsterTeam monsterTeam;

    public Battlefield(){
        this(20, 15, 6);
    }

    public Battlefield(int columns, int rows) {
        this(columns, rows, 6);
    }

    public Battlefield(int columns, int rows, int minionNum){
        this.columns = columns;
        this.rows = rows;
        grids = new GridEnum[this.rows][this.columns];
        humanTeam = TeamBuilder.buildHumanTeam();
        monsterTeam = TeamBuilder.buildMonsterTeam(minionNum);
    }

    public void clearGrids(){
        grids = new GridEnum[this.rows][this.columns];
    }

    public void setGrids(int[] column, int[] row, GridEnum gridEnum){
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

    public void setGrid(int column, int row, GridEnum gridEnum){
        try{
            synchronized (grids) {
                grids[row][column] = gridEnum;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
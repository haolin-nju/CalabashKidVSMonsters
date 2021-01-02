package test.main.java.nju.linhao.controller.logic;

import main.java.nju.linhao.controller.logic.LocalGameController;
import main.java.nju.linhao.enums.LocalGameStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocalGameControllerTest {
    private static int setUpTime = 0;

    @Before
    public void setUp() throws Exception {
        System.out.println("Set Up LocalGameController");
        assertTrue(setUpTime <= 1);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Set Up LocalGameController");
    }

    @Test
    public void setCurrentStatus() {//状态机的测试
        boolean[][] statusTransitionGraph = new boolean[7][7];
        statusTransitionGraph[0][0]=true;
        statusTransitionGraph[0][1]=true;
        statusTransitionGraph[1][0]=true;
        statusTransitionGraph[1][2]=true;
        statusTransitionGraph[2][3]=true;
        statusTransitionGraph[3][4]=true;
        statusTransitionGraph[3][5]=true;
        statusTransitionGraph[4][6]=true;
        statusTransitionGraph[5][6]=true;
        statusTransitionGraph[6][0]=true;
//        assertEquals(1,1);
        LocalGameStatus[] localGameStatuses = LocalGameStatus.values();
        int localGameStatusLen = localGameStatuses.length;
        int cnt = 0;
        for(int i = 0; i < localGameStatusLen; ++i) {
            LocalGameController.getInstance().setCurrentStatusWithoutCondition(localGameStatuses[i]);
            for(int j = 0; j < localGameStatusLen; ++j) {
                if(statusTransitionGraph[i][j] == false){
                    LocalGameController.getInstance().setCurrentStatus(localGameStatuses[j]);
                    assertTrue(LocalGameController.getInstance().getCurrentStatus() == localGameStatuses[i]);
                    System.out.println("Test " + localGameStatuses[i] + " to " + localGameStatuses[j] + ": Successful");
                }
            }
        }
        return;
    }
}
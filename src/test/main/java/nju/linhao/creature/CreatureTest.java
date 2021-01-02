package test.main.java.nju.linhao.creature;

import main.java.nju.linhao.creature.Creature;
import main.java.nju.linhao.creature.Human;
import main.java.nju.linhao.creature.Monster;
import main.java.nju.linhao.enums.CreatureStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class CreatureTest {
    private Random random = new Random(47);

    @Test
    public void injured() {
        Creature creature = new Human(true, "测试生物", 250, 10, 1, 10);
        int testThreadCnt = 50;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(testThreadCnt + 1 );
        int randDamage =  random.nextInt(10);
        for(int i = 0; i < testThreadCnt; ++i){
            Thread thread = new Thread(new Task(cyclicBarrier, creature, randDamage));
            thread.start();
        }
        try {
            cyclicBarrier.await();
            cyclicBarrier.await();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Rest Health: " + creature.getHealth());
        assertTrue((creature.getHealth() == 250 - (randDamage - 1) * 50)
                && creature.getCreatureStatus() == CreatureStatus.DEAD);
    }

    private class Task implements Runnable{
        private CyclicBarrier cyclicBarrier;
        private Creature creature;
        private int randomDamage;

        public Task(CyclicBarrier cyclicBarrier, Creature creature, int randomDamage) {
            this.cyclicBarrier = cyclicBarrier;
            this.creature = creature;
            this.randomDamage = randomDamage;
        }

        @Override
        public void run() {
            try{
                cyclicBarrier.await();
                this.creature.injured(this.randomDamage);
                cyclicBarrier.await();
            } catch(Exception e){
                System.out.println("Time Limit Exceeds");
                e.printStackTrace();
            }
        }
    }
}
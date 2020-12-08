package concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/19 11:50 下午
 * info :
 */
public class DinnerRoom {

    /**
     * 该饭店有5位厨子，每位厨子做一份菜需要3秒时间
     * 消费者吃一盘菜需要4秒时间
     * 该饭店总计有8个盘子(洗盘子时间忽略不计)
     * 请用程序模拟30人同时就餐的场景
     */

    static class Table extends LinkedList<Object> {
        int maxSize;
        public Table(int maxSize) {
            this.maxSize = maxSize;
        }

        public synchronized void putFood(Food f) {
            while (this.size() >= this.maxSize) {
                try {
                    System.out.println("wait eat");
                    wait();
                } catch (Exception e) {

                }
            }
            this.addLast(f);
            System.out.println("上菜一份，现在" + this.size());
            notifyAll();
        }

        public synchronized Food eat() {
            Food f;
            while (this.size() <= 0) {
                try {
                    System.out.println("no food here");
                    wait();
                } catch (Exception e) {

                }
            }
            f = (Food) this.getFirst();
            this.remove(f);
            System.out.println("吃了一份 还有" + this.size());
            notifyAll();
            return f;
        }
    }

    static class Cooker implements Runnable {
        private Table t;

        public Cooker(Table t) {
            this.t = t;
        }

        @Override
        public void run() {
            while (true) {
                Food f = new Food();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "做了一份");
                t.putFood(f);
            }
        }
    }

    static class Eater implements Runnable {
        private Table t;

        public Eater(Table t) {
            this.t = t;
        }

        @Override
        public void run() {
            while (true) {
                Food f = t.eat();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    static class Food{}

    public static void main(String[] args) {
        Table t = new Table(8);
        new Thread(new Cooker(t)).start();
        new Thread(new Cooker(t)).start();
        new Thread(new Cooker(t)).start();

        new Thread(new Eater(t)).start();
        new Thread(new Eater(t)).start();
        new Thread(new Eater(t)).start();
        new Thread(new Eater(t)).start();
        new Thread(new Eater(t)).start();
        new Thread(new Eater(t)).start();

    }

}




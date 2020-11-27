package concurrency;

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
     * 阿里笔试题
     * 该饭店有5位厨子，每位厨子做一份菜需要3秒时间
     * 消费者吃一盘菜需要4秒时间
     * 该饭店总计有8个盘子(洗盘子时间忽略不计)
     * 请用程序模拟30人同时就餐的场景
     */



    volatile private Semaphore cookerSemaphore = new Semaphore(5);
    private volatile Semaphore eaterSemaphore = new Semaphore(30);
    private volatile ReentrantLock lock = new ReentrantLock();
    private volatile Condition addFoodCondition = lock.newCondition();
    private volatile Condition eatCondition = lock.newCondition();
    private volatile ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();

    private boolean isEmpty() {
        return queue.isEmpty();
    }

    private boolean isFull() {
        return !queue.isEmpty();
    }

    public void buildFood() {
        try {
            cookerSemaphore.acquire();
            lock.lock();
            while (isFull()) {
                addFoodCondition.await();
            }
            queue.add(1);
            eatCondition.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
            cookerSemaphore.release();
        }
    }

    public void eat() {
        try {
            eaterSemaphore.acquire();
            lock.lock();
            while (isEmpty()) {
                eatCondition.await();
            }
            queue.remove();
            addFoodCondition.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
            eaterSemaphore.release();
        }
    }

    public static void main(String[] args) {

    }
}




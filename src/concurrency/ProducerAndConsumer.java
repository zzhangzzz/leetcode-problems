package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/20 10:34 上午
 * info :
 */
public class ProducerAndConsumer {
    /**
     * synchronzied wait notify 实现生产者消费者
     */
    private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5000);
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5000), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 主线程
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            poolExecutor.execute(new ProductThread(i, queue));//执行生产任务
            poolExecutor.execute(new ConsumerThread(queue));
        }
        poolExecutor.shutdown();
    }


    /**
     * 生产者的线程，它有一个队列来存储生产出来的任务
     */
    static class ProductThread extends Thread {
        private int taskNum;
        private ArrayBlockingQueue queue;

        public ProductThread(int taskNum, ArrayBlockingQueue queue) {
            this.taskNum = taskNum;
            this.queue = queue;
        }

        @Override
        public void run() {
            //模拟生产
            try {
//            Thread.currentThread().sleep(5000);
                System.out.println("starting produce" + taskNum);
                queue.put(taskNum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ConsumerThread extends Thread {
        private ArrayBlockingQueue queue;

        public ConsumerThread(ArrayBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("starting consume");
            int taskNum;
            try {
                if (queue.size() > 0) {
                    taskNum = (int) queue.take();
                    System.out.println("consumed:" + taskNum);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

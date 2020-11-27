package concurrency;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/21 10:10 上午
 * info : 管程实现生产者消费者
 */
public class Test {

    public static void main(String[] args) {
        SyncContainer container = new SyncContainer();
        new Producer(container).start();
        new Consumer(container).start();
    }
}

class Producer extends Thread {
    SyncContainer container;

    public Producer(SyncContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("生产" + i + "个食物");
            container.add(new Food(i));
        }
    }
}

class Consumer extends Thread {
    SyncContainer container;
    public Consumer(SyncContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费" + i + "个食物");
            container.eat();
        }
    }
}

//缓冲区
class SyncContainer {
    // 容器大小
    Food[] foods = new Food[10];

    // 计数器
    int count = 0;

    // 生产
    public synchronized void add(Food food) {
        if (count == foods.length) {
            // 通知消费 生产者等待
            try {
                this.wait();
            } catch (Exception e) {

            }
        }

        foods[count] = food;
        count++;
        this.notifyAll();

        // 通知消费者消费
    }

    // 消费
    public synchronized Food eat() {
        if (count == 0) {
            // 消费者等待， 生产者生产
            try {
                this.wait();
            } catch (Exception e) {

            }
        }

        count--;
        // 充值生产者生产
        this.notifyAll();

        return foods[count];
    }
}

class Food {
    int id;

    public Food(int id) {
        this.id = id;
    }
}

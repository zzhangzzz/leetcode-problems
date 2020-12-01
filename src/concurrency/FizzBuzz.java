package concurrency;

import java.util.function.IntConsumer;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/12/1 11:01 上午
 * info : 交替打印字符串
 */
public class FizzBuzz {
    private int n;
    private int i;

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        // 多个线程竞争 wait唤醒后要再判断一次 不符合继续continue
        synchronized(this) {
            while (i <= n) {
                if (!(i % 5 != 0 && i % 3 ==0)) {
                    wait();
                }
                if (!(i % 5 != 0 && i % 3 == 0)) {
                    continue;
                }
                if (i > n) {
                    break;
                }
                printFizz.run();
                i++;
                notifyAll();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        synchronized (this) {
            while (i <= n) {
                if (!(i % 3 == 0 && i % 5 ==0)) {
                    wait();
                }
                if (!(i % 5 == 0 && i % 3 == 0)) {
                    continue;
                }
                if (i > n) {
                    break;
                }
                printBuzz.run();
                i++;
                notifyAll();
            }
        }

    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        synchronized (this) {
            while (i <= n) {
                if (!(i % 3 == 0 && i % 5 ==0)) {
                    wait();
                }
                if (!(i % 5 == 0 && i % 3 == 0)) {
                    continue;
                }
                if (i > n) {
                    break;
                }
                printFizzBuzz.run();
                i++;
                notifyAll();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        synchronized (this) {
            while (i <= n) {
                if (i % 5 == 0 || i % 3 == 0) {
                    wait();
                }
                if (i % 5 == 0 || i % 3 == 0) {
                    continue;
                }
                printNumber.accept(i);
                i++;
                notifyAll();
            }
        }
    }
}

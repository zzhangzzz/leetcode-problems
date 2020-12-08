package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/12/6 3:58 下午
 * info :
 */
public class ThreadPoolExample {

    public interface ThreadPoolExam {
        void execute(Job job);

        void shutDown();

        void addWorker(int num);

        void removeWorker(int num);

        int getJobSize();
    }



    public class DefaultThreadPool <Job extends Runnable> implements ThreadPoolExam {
        private static final int MAX_WORKER_NUM = 30;
        private static final int DEFAULT_WORKER_NUM = 5;
        private static final int MIN_WORKER_NUM = 1;

        private final LinkedList<Job> jobs = new LinkedList<>();
        private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

        private int workerNum;
        private AtomicInteger threadNum = new AtomicInteger();

        public DefaultThreadPool(int workerNum) {
            if (workerNum > MAX_WORKER_NUM) {
                this.workerNum = MAX_WORKER_NUM;
            } else {
                this.workerNum = workerNum;
            }
            initlizeWorkers(workerNum);
        }

        private void initlizeWorkers(int num) {
            for (int i = 0; i < num; i++) {
                Worker worker = new Worker();
                workers.add(worker);
                Runnable target;
                Thread t = new Thread(worker);
                t.start();
            }
        }

        class Worker implements Runnable {
            private volatile boolean running = true;
            @Override
            public void run() {
                while (running) {
                    Job job = null;
                    synchronized (jobs) {
                        if (jobs.isEmpty()) {
                            try {
                                jobs.wait();
                            } catch (Exception e) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                        job = jobs.removeFirst();
                    }
                    if (job != null) {
                        job.run();
                    }
                }
            }

            public void shutdown() {
                running = false;
            }
        }

        @Override
        public void execute(Job job) {
            if (null == job) {
                throw new NullPointerException();
            }
            synchronized (jobs) {
                jobs.add(job);
                jobs.notifyAll();
            }
        }

        @Override
        public void shutDown() {
            for (Worker worker : workers) {
                worker.shutdown();
            }
        }

        @Override
        public void addWorker(int num) {
            synchronized (jobs) {
                if (num + this.workerNum > MAX_WORKER_NUM) {
                    num = MAX_WORKER_NUM - this.workerNum;
                }
                initlizeWorkers(num);
                this.workerNum += num;
            }
        }

        @Override
        public void removeWorker(int num) {
            synchronized (jobs) {
                if (num >= this.workerNum) {
                    throw new IllegalArgumentException("超过上限");
                }
                for (int i = 0; i < num; i++) {
                    Worker worker = workers.get(i);
                    if (null != worker) {
                        worker.shutdown();
                        workers.remove(i);
                    }
                }
                this.workerNum -= num;
            }
        }

        @Override
        public int getJobSize() {
            return workers.size();
        }
    }
}

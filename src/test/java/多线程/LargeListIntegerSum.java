package 多线程;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 处理数据
 *
 * @auther: czwei
 * @date: 2019/5/22 15:27
 */
public class LargeListIntegerSum {

    //存放整数的和
    private long sum;
    //障栅集合点(同步器)
    private CyclicBarrier barrier;
    //整数集合List
    private List<Integer> list;
    //使用的线程数
    private int threadCounts;

    public LargeListIntegerSum(List<Integer> list, int threadCounts) {
        this.list = list;
        this.threadCounts = threadCounts;
    }

    /**
     * 获取List中所有整数的和
     *
     * @return
     */
    public long getIntegerSum() {
        ExecutorService exec = Executors.newFixedThreadPool(threadCounts);
        //平均分割List
        int len = list.size() / threadCounts;
        //List中的数量没有线程数多（很少存在）
        if (len == 0) {
            //采用一个线程处理List中的一个元素
            threadCounts = list.size();
            //重新平均分割List
            len = list.size() / threadCounts;
        }
        barrier = new CyclicBarrier(threadCounts + 1);
        for (int i = 0; i < threadCounts; i++) {
            //创建线程任务
            //最后一个线程承担剩下的所有元素的计算
            if (i == threadCounts - 1) {
                exec.execute(new SubIntegerSumTask(list.subList(i * len, list.size())));
            } else {
                exec.execute(new SubIntegerSumTask(list.subList(i * len, len * (i + 1) > list.size() ? list.size() : len * (i + 1))));
            }
        }
        try {
            //关键，使该线程在障栅处等待，直到所有的线程都到达障栅处
            barrier.await();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ":Interrupted");
        } catch (BrokenBarrierException e) {
            System.out.println(Thread.currentThread().getName() + ":BrokenBarrier");
        }
        exec.shutdown();
        return sum;
    }

    /**
     * 分割计算List整数和的线程任务
     */
    public class SubIntegerSumTask implements Runnable {
        private List<Integer> subList;

        public SubIntegerSumTask(List<Integer> subList) {
            this.subList = subList;
        }

        public void run() {
            long subSum = 0L;
            for (Integer i : subList) {
                subSum += i;
            }
            //在LargeListIntegerSum对象上同步
            synchronized (LargeListIntegerSum.this) {
                sum += subSum;
            }
            try {
                //关键，使该线程在障栅处等待，直到所有的线程都到达障栅处
                barrier.await();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ":Interrupted");
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + ":BrokenBarrier");
            }
            System.out.println("分配给线程：" + Thread.currentThread().getName() + "那一部分List的整数和为：\tSubSum:" + subSum);
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        int threadCounts = 10;//采用的线程数
        for (int i = 1; i <= 1000000; i++) {
            list.add(i);
        }
        long start = System.currentTimeMillis();
        LargeListIntegerSum countListIntegerSum = new LargeListIntegerSum(list, threadCounts);

        long sum = countListIntegerSum.getIntegerSum();
        System.out.println("List中所有整数的和为:" + sum);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}

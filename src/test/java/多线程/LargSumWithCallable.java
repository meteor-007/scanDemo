package 多线程;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程并发快速处理数据
 *
 * @uther: czwei
 * @date: 2019/5/22 15:24
 */
public class LargSumWithCallable {

    //使用的线程数
    static int threadCounts = 10;
    static long sum = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadCounts);
        List<Callable<Long>> callList = new ArrayList<>(16);
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j <= 1000000; j++) {
            list.add(j);
        }
        //平均分割List
        int len = list.size() / threadCounts;
        //List中的数量没有线程数多（很少存在）
        if (len == 0) {
            //采用一个线程处理List中的一个元素
            threadCounts = list.size();
            //重新平均分割List
            len = list.size() / threadCounts;
        }
        for (int i = 0; i < threadCounts; i++) {
            final List<Integer> subList;
            if (i == threadCounts - 1) {
                subList = list.subList(i * len, list.size());
            } else {
                subList = list.subList(i * len, len * (i + 1) > list.size() ? list.size() : len * (i + 1));
            }
            //采用匿名内部类实现
            callList.add(new Callable<Long>() {
                public Long call() throws Exception {
                    long subSum = 0L;
                    for (Integer i : subList) {
                        subSum += i;
                    }
                    System.out.println("分配给线程：" + Thread.currentThread().getName() + "那一部分List的整数和为：SubSum:" + subSum);
                    return subSum;
                }
            });
        }
        List<Future<Long>> futureList = exec.invokeAll(callList);
        for (Future<Long> future : futureList) {
            sum += future.get();
        }
        exec.shutdown();
        System.out.println(sum);
    }
}

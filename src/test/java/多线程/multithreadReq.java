package 多线程;

/**
 * @auther: czwei
 * @date: 2019/5/23 19:49
 */
public class multithreadReq {

    //线程数量
    private static final int THREADNUM = 5;
    public static void main(String[] args) {
        //线程数量
        int threadmax = THREADNUM;
        for(int i = 0; i < threadmax; i++)
        {
            ThreadMode thread = new ThreadMode();
            thread.getThread().start();
        }
    }

}

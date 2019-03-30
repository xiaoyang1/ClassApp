package cn.quantum.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {
    public static Executor getExecutor(int threadNum, int queueSize){
        String name = "ClusterThreadPool";
        return new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.MINUTES,
                createBlockingQueue(queueSize), new BlockingPolicy());
    }


    private static BlockingQueue<Runnable> createBlockingQueue(int queues){
        return new LinkedBlockingQueue<Runnable>(queues);
//        return new SynchronousQueue<>();
    }

    private static class BlockingPolicy implements RejectedExecutionHandler {


        public BlockingPolicy() {}

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            if(!executor.isShutdown()){
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

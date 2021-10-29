package org.storm.commons.offlinetask.common;

import org.storm.commons.offlinetask.exception.SystemException;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 线程工具类
 */
public class ThreadUtil {

    /**
     * 所有线程池在此定义
     */
    public enum Pool {
        /**
         * 通用线程池
         */
        COMMON(128, 128, Integer.MAX_VALUE, "通用线程池"),

        /**
         * IO密集型线程池，适合涉及IO操作的线程，如RPC调用，数据库调用等
         */
        IO(9, 18, 4096 * 4, "IO密集型线程池"),

        /**
         * 纯内存cpu计算线程池
         */
        CPU(5, 5, Integer.MAX_VALUE, "CPU密集型线程池");

        ExecutorService executor;

        /**
         * @param corePoolSize    核心线程池大小
         * @param maximumPoolSize 最大线程池大小，只有队列的大小达到queueSize的大小时，该参数才起作用
         * @param queueSize       等待线程数
         * @param poolName        线程名称
         */
        Pool(int corePoolSize, int maximumPoolSize, int queueSize, String poolName) {
            executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(queueSize), new CustomThreadNameThreadFactory(poolName));
        }

        public ExecutorService getExecutor() {
            return executor;
        }
    }

    static class CustomThreadNameThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNo = new AtomicInteger(1);
        private final String  nameStart;
        private static final String  nameEnd  = "]";

        public CustomThreadNameThreadFactory(String poolName){
            nameStart = "[" + poolName + "-";
        }

        @Override
        public Thread newThread(Runnable r) {
            String threadName = nameStart + threadNo.getAndIncrement() + nameEnd;
            return new Thread(r, threadName);
        }
    }


    /**
     * 等待执行完成
     * @param future
     */
    public static void waitForDone(Future future) {
        try {
            if (future != null) {
                future.get();
            }
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

}

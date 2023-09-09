package com.lzok.rssread.Util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lzok
 * @description 解析连接后如果超过五秒提醒用户检查连接
 */
public class ConnectionParser {
    private static final int TIMEOUT_SECONDS = 5;

    public interface ConnectionListener {
        void onConnectionSuccess(String url);

        void onConnectionTimeout();
    }

    public static void parseConnection(final String url, final ConnectionListener listener) {
        // 创建一个单线程线程池
        ExecutorService executor = new ThreadPoolExecutor(
                1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()
        );

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 在这里进行连接解析
                // 如果解析成功，返回连接
                return url;
            }
        };

        Future<String> future = executor.submit(callable);

        try {
            String result = future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            // 解析成功
            listener.onConnectionSuccess(result);
        } catch (InterruptedException e) {
            // 线程被中断
            listener.onConnectionTimeout();
        } catch (ExecutionException e) {
            // 任务执行出错
            listener.onConnectionTimeout();
        } catch (TimeoutException e) {
            // 超时
            future.cancel(true); // 取消任务
            listener.onConnectionTimeout();
        } finally {
            executor.shutdownNow(); // 关闭线程池
        }
    }
}

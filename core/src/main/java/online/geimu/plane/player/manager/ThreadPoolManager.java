package online.geimu.plane.player.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author liutao
 * @description :  线程池管理
 *  初步设计是为了管理游戏中大量的线程,因为很多单位都会涉及到自动移动,所以都会有更新位置的线程.这里设想采用集中的线程池进行管理,提供绑定和回收的功能.
 *  防止程序中出现很多线程池等待任务,但是自己却不知道的情况.
 * @create 2017-01-11 9:43
 */
public class ThreadPoolManager {

    public static final ExecutorService CACHE = Executors.newCachedThreadPool();

    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(100);

}

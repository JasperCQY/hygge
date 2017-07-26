package com.szyooge.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 排队任务
 * @author quanyou.chen
 * @date: 2017年6月9日 下午2:18:25
 * @version  v 1.0
 */
public abstract class TaskQueue implements Runnable {
    
    private static ScheduledExecutorService schedule = null;
    
    static {
        schedule = Executors.newScheduledThreadPool(1);
    }
    
    /**
     * 提供子类初始化用
     * @author quanyou.chen
     * @date: 2017年6月12日 上午10:08:13
     */
    public void init(TaskQueue task) {
        
    }
    
    /**
     * 延迟执行任务
     * @author quanyou.chen
     * @date: 2017年6月9日 下午2:36:21
     * @return
     */
    public long delay() {
        return 10;
    }
    
    /**
     * 延迟执行任务单位
     * @author quanyou.chen
     * @date: 2017年6月9日 下午2:36:31
     * @return
     */
    public TimeUnit timeUnit() {
        return TimeUnit.MILLISECONDS;
    }
    
    /**
     * 邮件发送
     * @author quanyou.chen
     * @date: 2017年4月10日 下午3:37:56
     * @Title: execute    
     */
    public void execute(TaskQueue task) {
        // 初始化
        this.init(task);
        schedule.schedule(task, delay(), timeUnit());
    }
}

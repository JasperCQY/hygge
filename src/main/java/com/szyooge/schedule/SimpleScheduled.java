package com.szyooge.schedule;

import java.util.Map;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;

/**
 * IFC任务
 * @ClassName: IFCTask
 * @author quanyou.chen
 * @date: 2017年6月8日 上午10:46:08
 * @version  v 1.0
 */
public abstract class SimpleScheduled {
    
    /**
     * 每分
     */
    protected static final String PER_MINUTE = "0 0/1 * * * ?";
    
    /**
     * 每小时
     */
    protected static final String PER_HOUR = "0 0 0/1 * * ?";
    
    /**
     * 每天23点59分59秒
     */
    protected static final String PER_DAY = "59 59 23 * * ?";
    
    /**
     * 1秒（毫秒数）
     */
    protected static final int SECOND = 1000;
    
    /**
     * 1分（毫秒数）
     */
    protected static final int MINUTE = 60000;
    
    /**
     * 1小时（毫秒数）
     */
    protected static final int HOUR = 3600000;
    
    /**
     * 1天（毫秒数）
     */
    protected static final int DAY = 86400000;
    
    /**
     * 1周（毫秒数）
     */
    protected static final int HEBDOMAD = 604800000;
    
    /**
     * 任务执行时间规则
     */
    protected String expression;
    
    /**
     * 字典
     */
    protected Map<String, String> dict;
    
    private TriggerTask triggerTask;
    
    /**
     * taskRun
     * @author quanyou.chen
     * @date: 2017年6月8日 上午11:14:13
     * @Title: taskRun
     * @return void 返回值
     */
    protected abstract void taskRun();
    
    /**
     * 表达式（任务执行时间表达式）
     * @author quanyou.chen
     * @date: 2017年6月8日 下午3:07:52
     * @Title: queryExpression
     * @return String 返回值
     */
    protected String initExpression(){
        this.expression = "0/1 * * * * ?";
        return this.expression;
    }
    
    /**
     * 表达式（任务执行时间表达式）
     * @author quanyou.chen
     * @date: 2017年6月8日 下午3:07:52
     * @Title: setExpression
     * @return String 返回值
     */
    protected abstract String setExpression();
    
    public TriggerTask triggerTask() {
        if (triggerTask == null) {
            triggerTask = new TriggerTask(task(), trigger());
        }
        return triggerTask;
    }
    
    /**
     * 查询字典
     * @author quanyou.chen
     * @date: 2017年6月8日 下午2:40:57
     * @Title: queryDict
     * @return
     * @throws IFCBaseException Map<String,String> 返回值
     */
    private Map<String, String> initDict()  {
        return dict;
    }
    
    /**
     * 任务
     * @author quanyou.chen
     * @date: 2017年6月8日 上午10:46:19
     * @Title: task
     * @return Runnable 返回值
     */
    private Runnable task() {
        return new TriggerTaskRunnel();
    }
    
    /**
     * 执行方式
     * @author quanyou.chen
     * @date: 2017年6月8日 上午10:46:32
     * @Title: trigger
     * @return Trigger 返回值
     */
    private Trigger trigger() {
        this.initExpression();
        return new SimpleTrigger(expression);
    }
    
    private class TriggerTaskRunnel implements Runnable {
        @Override
        public void run() {
            initDict();
            // 设置任务时间
            setExpression();
            // 设置下一个任务时间
            ((SimpleTrigger)triggerTask.getTrigger()).setExpression(expression);
            taskRun();
        }
    }
}

package com.szyooge.schedule;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

/**
 * 任务的时间设置
 * @ClassName: IFCTrigger
 * @author quanyou.chen
 * @date: 2017年6月8日 上午11:05:57
 * @version  v 1.0
 */
public class SimpleTrigger implements Trigger {
    private String expression;
    
    public SimpleTrigger(String expression) {
        this.expression = expression;
    }
    
    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        // 定时任务触发，可修改定时任务的执行周期
        CronTrigger trigger = new CronTrigger(this.expression);
        Date nextExecDate = trigger.nextExecutionTime(triggerContext);
        return nextExecDate;
    }
}

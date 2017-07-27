package com.szyooge.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.szyooge.config.WeChatConf;
import com.szyooge.schedule.impl.WeChatTokenSche;

@Configuration
@EnableScheduling
public class DynamicScheduledTask implements SchedulingConfigurer {
    
    @Autowired
    private WeChatConf weChatConf;
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 证书
        SimpleScheduled certificateCleanTask =
            new WeChatTokenSche(weChatConf);
        taskRegistrar.addTriggerTask(certificateCleanTask.triggerTask());
    }
    
}

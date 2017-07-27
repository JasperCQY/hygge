package com.szyooge.schedule.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyooge.config.WeChatConf;
import com.szyooge.schedule.SimpleScheduled;
import com.szyooge.util.MDC_LOG;

/**
 * RSA过期清理
 * @ClassName: CertificateCleanTaskImpl
 * @author quanyou.chen
 * @date: 2017年6月8日 上午11:15:58
 * @version  v 1.0
 */
public class WeChatTokenSche extends SimpleScheduled {
    private static final Logger log = LoggerFactory.getLogger(WeChatTokenSche.class);
    
    private WeChatConf weChatConf;
    
    public WeChatTokenSche(WeChatConf weChatConf) {
        super();
        this.weChatConf = weChatConf;
    }
    
    @Override
    protected String setExpression() {
        this.expression = "0 0 0/1 * * ?";
        return this.expression;
    }
    
    @Override
    public void taskRun() {
        // 定时任务的业务逻辑
        MDC_LOG.add(this);
        log.debug(weChatConf.requestAccessToken());
        MDC_LOG.remove();
    }
}

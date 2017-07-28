package com.szyooge.wechat.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.szyooge.util.XmlUtil;
import com.szyooge.wechat.constant.WeChatXmlConst;
import com.szyooge.wechat.msgtype.service.WeChatMsgTypeService;
import com.szyooge.wechat.service.WeChatPushService;

@Service
public class WeChatPushImpl implements WeChatPushService {
    
    private static Logger logger = LoggerFactory.getLogger(WeChatPushImpl.class);
    
    @Autowired
    private ApplicationContext context;
    
    @Override
    public String wxAccess(String wxMsg) {
        Map<String, String> wxMap = XmlUtil.xmlStrToMap(wxMsg);
        String msgType = wxMap.get(WeChatXmlConst.MsgType);
        logger.info("微信消息类型：" + msgType);
        WeChatMsgTypeService weChatMsgTypeService = (WeChatMsgTypeService) context.getBean(msgType);
        return weChatMsgTypeService.msg(wxMap);
    }
}

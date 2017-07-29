package com.szyooge.wechat.msgtype.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.szyooge.tuling.Robot;
import com.szyooge.tuling.constant.RobotConst;
import com.szyooge.util.XmlUtil;
import com.szyooge.wechat.constant.WeChatXmlConst;
import com.szyooge.wechat.msgtype.service.WeChatMsgTypeService;

@Service(WeChatXmlConst.IMsgType.text)
public class WeChatTextMsgImpl implements WeChatMsgTypeService {
    
    private static Logger logger = LoggerFactory.getLogger(WeChatTextMsgImpl.class);

    @Autowired
    private Robot robot;
    
    @Override
    public String msg(Map<String, String> wxMap) {
        String replyXmlStr = null;
        Map<String, String> replyMap = new HashMap<String, String>(wxMap);
        Resource resource = new ClassPathResource("replyXml/text.xml");
        InputStream in = null;
        try {
            in = resource.getInputStream();
            
            replyMap.put(WeChatXmlConst.FromUserName, wxMap.get(WeChatXmlConst.ToUserName));
            replyMap.put(WeChatXmlConst.ToUserName, wxMap.get(WeChatXmlConst.FromUserName));
            replyMap.put(WeChatXmlConst.CreateTime, String.valueOf(new Date().getTime()));
            String context = wxMap.get(WeChatXmlConst.Content);
            String replyMsg = robot.sendMsg(wxMap.get(WeChatXmlConst.FromUserName),context);
            logger.info(replyMsg);
            try {
            	JSONObject robotReply = new JSONObject(replyMsg);
            	if(RobotConst.DataType.TEXT == robotReply.getInt("code")) {
            		context = robotReply.getString("text");
            	}
            	
			} catch (JSONException e) {
				logger.error("图灵机器人回复JSON格式问题", e);
			}
            replyMap.put(WeChatXmlConst.Content, context);
            replyXmlStr = XmlUtil.mapToXmlStr(replyMap, in);
        } catch (IOException e) {
            logger.error("获取资源文件流失败", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭资源文件流失败", e);
                }
            }
        }
        
        return replyXmlStr;
    }
    
}
